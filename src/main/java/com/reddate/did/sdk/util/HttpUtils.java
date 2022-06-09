package com.reddate.did.sdk.util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.reddate.did.sdk.constant.ErrorMessage;
import com.reddate.did.sdk.exception.DidException;
import com.reddate.did.sdk.protocol.request.RequestParam;
import com.reddate.did.sdk.protocol.response.ResultData;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Http/Https utils class, some http(s) protocal common tools method implement
 * in this class
 */
public class HttpUtils<T> {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	static MediaType JSON = MediaType.parse("application/json;charset=utf-8");

	/**
	 * call the OKHttp post method to send some request data to server ,and process
	 * server return data to the target type
	 */
	public static <T, E> ResultData<T> postCall(String url, String token, RequestParam<E> requestParam,
			Class<T> returnType) {
		RequestBody requestBody = RequestBody.create(JSONObject.toJSONString(requestParam), JSON);
		Request request = new Request.Builder().url(url).post(requestBody).addHeader("token", token)
				.addHeader("projectId", requestParam.getProjectId()).header("Connection", "close").build();

		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS)
				.readTimeout(60, TimeUnit.SECONDS).build();

		String resposneDataStr = null;
		Response response = null;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("The request URL is: {} ,The request Data is: {}", url,
						JSONObject.toJSONString(requestParam));
			}
			//System.out.println("The request URL is: "+url+" ,The request Data is: "+JSONObject.toJSONString(requestParam));
			response = client.newCall(request).execute();
			int httpResonseCode = response.code();
			if (httpResonseCode != 200) {
				response.close();
				throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), ErrorMessage.UNKNOWN_ERROR.getMessage()+"service http code is "+httpResonseCode);
			}
			resposneDataStr = response.body().string();
			response.close();
			if (logger.isDebugEnabled()) {
				logger.debug("The response data is: {}", resposneDataStr);
			}
			//System.out.println("The response data is: "+resposneDataStr);
		} catch (IOException e) {
			e.printStackTrace();
			response.close();
			throw new DidException(ErrorMessage.UNKNOWN_ERROR.getCode(), ErrorMessage.UNKNOWN_ERROR.getMessage()+e.getMessage());
		}

		JSONObject resultJson = JSONObject.parseObject(resposneDataStr);
		ResultData<T> result = new ResultData<>();
		Integer resultCode = resultJson.getInteger("code");
		result.setCode(resultCode);
		if (resultCode != 0) {
			throw new DidException(resultCode, resultJson.getString("msg"));
		}
		
		Object obj = resultJson.get("data");
		if (obj != null) {
			if (obj instanceof JSONObject) {
				JSONObject resultJsonData = resultJson.getJSONObject("data");
				if (resultJsonData != null) {
					T data = JSONObject.toJavaObject(resultJsonData, returnType);
					result.setData(data);
				}
			} else {
				T data = (T) obj;
				result.setData(data);
			}
		}

		return result;
	}

}
