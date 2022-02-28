package com.reddate.did.sdk.protocol.response.hub;

import java.io.Serializable;

/**
 * Delete permission response data structure
 */
public class DeletePermissionResp implements Serializable {

	/**
	 * is success flag
	 */
	private boolean succes;

	/**
	 * error message
	 */
	private String message;

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

}
