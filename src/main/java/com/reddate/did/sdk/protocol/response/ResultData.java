package com.reddate.did.sdk.protocol.response;

import java.io.Serializable;

/**
 * BSN did service request result wrapper, param T is the result business data
 * 
 * @param <T>
 */
public class ResultData<T> implements Serializable {

	/**
	 * The response code , the code 200 is success code, other code is error code
	 */
	private Integer code;

	/**
	 * the error message information, when request process failed this filed will be
	 * fill.
	 */
	private String msg;

	/**
	 * The result business data
	 */
	private T data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 
	 * success result wrapper method
	 * 
	 * @param <T>
	 * @param data The business data
	 * @return
	 */
	public static <T> ResultData<T> success(T data) {
		ResultData<T> result = new ResultData<>();
		result.setCode(0);
		result.setMsg("success");
		result.setData(data);
		return result;
	}

	/**
	 * 
	 * Error result wrapper method
	 * 
	 * @param <T>
	 * @param code    The error code in response
	 * @param message The detail error message in response
	 * @param classes The business data class type
	 * @return The response wrapper
	 */

	public static <T> ResultData<T> error(Integer code, String message, Class<T> classes) {
		ResultData<T> result = new ResultData<>();
		result.setCode(code);
		result.setMsg(message);
		return result;
	}

	/**
	 * Check if response code is success response code.
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return code == 0;
	}
}
