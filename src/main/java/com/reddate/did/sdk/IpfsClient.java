// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.sdk;

import com.reddate.did.sdk.constant.ErrorMessage;
import com.reddate.did.sdk.exception.DidException;
import com.reddate.did.sdk.param.CryptoType;
import com.reddate.did.sdk.param.ipfs.ResourceInfo;
import com.reddate.did.sdk.param.ipfs.SaveResource;
import com.reddate.did.sdk.service.ipfs.IpfsResourceService;
import com.reddate.did.sdk.util.AesUtils;
import com.reddate.did.sdk.util.Secp256Util;

public class IpfsClient {

	private IpfsResourceService ipfsResourceService = new IpfsResourceService();

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
	public SaveResource saveResource(String uploadKey, String content, boolean encrypt, CryptoType cryptoType,
			String privateKey) {
		try {
			return ipfsResourceService.uploadResource(uploadKey, content, encrypt, cryptoType, privateKey);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(),
					ErrorMessage.UNKNOWN_ERROR.getMessage() + e.getMessage());
		}
	}

	/**
	 * Get files saved in ipfs network of BSN 
	 * 
	 * @param downloadKey download key value for BSN ifps service
	 * @param hash  The key value of the file in ipfs network
	 * @return return the file content and encryption key
	 */
	public ResourceInfo getResource(String downloadKey, String hash) {
		try {
			return ipfsResourceService.downloadResource(downloadKey, hash);
		} catch (DidException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(),
					ErrorMessage.UNKNOWN_ERROR.getMessage() + e.getMessage());
		}
	}

	public static String decrypt(CryptoType cryptoType, String content, String encptyKey, String privateKey) {
		String key = null;
		try {
			key = Secp256Util.decrypt(cryptoType, encptyKey, privateKey);
		} catch (Exception e) {
			throw new DidException(ErrorMessage.DECRYPT_KEY_FAILED.getCode(),
					ErrorMessage.DECRYPT_KEY_FAILED.getMessage() + ": " + e.getMessage());
		}
		try {
			return AesUtils.decrypt(content, key);
		} catch (Exception e) {
			throw new DidException(ErrorMessage.DECRYPT_CONTENT_FAILED.getCode(),
					ErrorMessage.DECRYPT_CONTENT_FAILED.getMessage() + ": " + e.getMessage());
		}
	}
}
