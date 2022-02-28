package com.reddate.did.sdk.protocol.response.hub;

import java.io.Serializable;

/**
 * Check permission response data structure
 */
public class CheckPermissionResp implements Serializable {

	/**
	 * is success flag
	 */
	private boolean succes = false;

	/**
	 * error message
	 */
	private String message;

	/**
	 * encryption key
	 */
	private String key;

	/**
	 * resource url
	 */
	private String url;

	public boolean isSucces() {
		return succes;
	}

	public void setSucces(boolean succes) {
		this.succes = succes;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
