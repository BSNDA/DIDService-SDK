// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.sdk.service.ipfs;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.reddate.did.sdk.constant.ErrorMessage;
import com.reddate.did.sdk.exception.DidException;
import com.reddate.did.sdk.param.CryptoType;
import com.reddate.did.sdk.param.ipfs.ResourceInfo;
import com.reddate.did.sdk.param.ipfs.SaveResource;
import com.reddate.did.sdk.util.AesUtils;
import com.reddate.did.sdk.util.Secp256Util;

public class IpfsResourceService {

	private static final Logger logger = LoggerFactory.getLogger(IpfsResourceService.class);

	private static final String UPLOAD_FILE_URL = "https://ipfscc.bsngate.com:18602/ipfs/peer1/{Key}/api/v0/block/put?format=v0&mhtype=sha2-256&mhlen=-1&pin=true";
	private static final String DOWNLOAD_FILE_URL = "https://ipfscc.bsngate.com:18602/ipfs/peer1/{Key}/api/v0/block/get?arg=";
	
	//  test environment
//	private static final String UPLOAD_FILE_URL = "https://ipfstest.bsngate.com:17603/ipfs/{Key}/api/v0/block/put?format=v0&mhtype=sha2-256&mhlen=-1&pin=true";
//	private static final String DOWNLOAD_FILE_URL = "https://ipfstest.bsngate.com:17603/ipfs/{Key}/api/v0/block/get?arg=";
	
	/**
	 * The  data is sent to the ipfs network of BSN. 
	 * The information stored in ipfs can be ciphertext or plaintext. 
	 * 
	 * @param uploadKey upload key value for BSN ifps service
	 * @param content  data content string
	 * @param encrypt  encrypt the file content stored in then ipfs network if true
	 * @param cryptoType crypto type, required where parameter encrypt's value is true
	 * @param privateKey  decimal private key, required where parameter encrypt's value is true
	 * @return return the key value of the file in ipfs network
	 */
	public SaveResource uploadResource(String uploadKey, String content, boolean encrypt, CryptoType cryptoType,
			String privateKey) {
		if (uploadKey == null || uploadKey.trim().isEmpty()) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "uploadKey is empty");
		}

		if (content == null || content.trim().isEmpty()) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "content is empty");
		}

		if (encrypt) {
			if (cryptoType == null) {
				throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "cryptoType is empty");
			}

			if (privateKey == null || privateKey.trim().isEmpty()) {
				throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "privateKey is empty");
			}
		}

		String uploadUrl = UPLOAD_FILE_URL.replace("{Key}", uploadKey);

		Map<String, Object> dataMap = new HashMap<>();
		String enKey = null;
		if (encrypt) {
			String key = AesUtils.generalKey();
			String enContent;
			try {
				enContent = AesUtils.encrypt(content, key);
			} catch (Exception e) {
				e.printStackTrace();
				throw new DidException(ErrorMessage.ENCRYPT_FAILED.getCode(), ErrorMessage.ENCRYPT_FAILED.getMessage());
			}
			String publicKey = Secp256Util.getPublicKey(cryptoType, privateKey);
			enKey = Secp256Util.encrypt(cryptoType, key, publicKey);
			dataMap.put("doc", enContent);
			dataMap.put("key", enKey);
			// dataMap.put("owner", publicKey);
		} else {
			dataMap.put("doc", content);
			dataMap.put("key", "");
			// dataMap.put("owner", "");
		}
		
		RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("arg", System.currentTimeMillis() + ".txt", okhttp3.RequestBody.create(JSONObject.toJSONString(dataMap), MultipartBody.FORM))
				.build();
		Request request = new Request.Builder().url(uploadUrl).post(requestBody)
				.addHeader("Content-Type", "multipart/form-data").build();
		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS)
				.readTimeout(60, TimeUnit.SECONDS).build();
		String resposneDataStr = null;
		Response response = null;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("The request URL is: {} ", uploadUrl);
			}
			System.out.println("The request URL is: " + uploadUrl);
			response = client.newCall(request).execute();
			int httpResonseCode = response.code();
			if (httpResonseCode != 200) {
				response.close();
				throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(),
						ErrorMessage.UNKNOWN_ERROR.getMessage() + "service http code is " + httpResonseCode);
			}
			resposneDataStr = response.body().string();
			response.close();
			if (logger.isDebugEnabled()) {
				logger.debug("The response data is: {}", resposneDataStr);
			}
			System.out.println("The response data is: " + resposneDataStr);
		} catch (IOException e) {
			e.printStackTrace();
			response.close();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(),
					ErrorMessage.UNKNOWN_ERROR.getMessage() + e.getMessage());
		}

		Map<String, Object> resultDataMap = JSON.parseObject(resposneDataStr, Map.class);
		SaveResource saveResourceIpfsResp = new SaveResource();
		saveResourceIpfsResp.setKey(enKey);
		saveResourceIpfsResp.setHash((String) resultDataMap.get("Key"));

		return saveResourceIpfsResp;
	}

	/**
	 * Get files saved in ipfs network of BSN 
	 * 
	 * @param downloadKey download key value for BSN ifps service
	 * @param hash  The key value of the file in ipfs network
	 * @return return the file content and encryption key
	 */
	public ResourceInfo downloadResource(String downloadKey, String hash) {
		if (downloadKey == null || downloadKey.trim().isEmpty()) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "downloadKey is empty");
		}

		if (hash == null || hash.trim().isEmpty()) {
			throw new DidException(ErrorMessage.PARAMETER_IS_EMPTY.getCode(), "hash is empty");
		}

		String downloadURL = DOWNLOAD_FILE_URL.replace("{Key}", downloadKey) + hash;

		FormBody requestBody = new FormBody.Builder().add("arg", "hash").build();
		Request request = new Request.Builder().url(downloadURL).post(requestBody).build();
		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS)
				.readTimeout(60, TimeUnit.SECONDS).build();
		String resposneDataStr = null;
		Response response = null;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("The request URL is: {}", downloadURL);
			}
			System.out.println("The request URL is: " + downloadURL);
			response = client.newCall(request).execute();
			int httpResonseCode = response.code();
			if (httpResonseCode != 200) {
				response.close();
				throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(),
						ErrorMessage.UNKNOWN_ERROR.getMessage() + "service http code is " + httpResonseCode);
			}
			resposneDataStr = response.body().string();
			response.close();
			if (logger.isDebugEnabled()) {
				logger.debug("The response data is: {}", resposneDataStr);
			}
			System.out.println("The response data is: " + resposneDataStr);
		} catch (IOException e) {
			e.printStackTrace();
			response.close();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(),
					ErrorMessage.UNKNOWN_ERROR.getMessage() + e.getMessage());
		}

		Map<String, Object> dataMap = JSON.parseObject(resposneDataStr, Map.class);
		ResourceInfo resourceInfo = new ResourceInfo();
		resourceInfo.setContent((String) dataMap.get("doc"));
		resourceInfo.setKey((String) dataMap.get("key"));

		return resourceInfo;
	}
}
