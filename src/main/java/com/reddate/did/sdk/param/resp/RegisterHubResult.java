package com.reddate.did.sdk.param.resp;

import java.io.Serializable;

/**
 * Register hub user result description data structure
 */
public class RegisterHubResult implements Serializable {

	/**
	 * is success flag
	 */
	private boolean success = false;

	/**
	 * register user id
	 */
	private String uid;

	/**
	 * error message when register failed
	 */
	private String message;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
