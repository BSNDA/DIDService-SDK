package com.reddate.did.sdk.protocol.response.hub;

import java.io.Serializable;

/**
 * 
 * Register Hub response data structure
 * 
 *
 */
public class RegisterHubResp implements Serializable {

	/**
	 * is process success flag
	 * 
	 */
	  private boolean success = false;
	  
	  /**
	   * use id
	   * 
	   */
	  private String uid;

	  /**
	   * error message
	   * 
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
