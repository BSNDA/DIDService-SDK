package com.reddate.did.sdk.param.resp;

import java.io.Serializable;
/**
 * Register hub user result description data structure 
 * 
 * 
 * 
 *
 */
public class RegisterHubResult implements Serializable {

	/**
	 * is success flag
	 * 
	 */
	  private boolean success = false;
	  
	  /**
	   * 
	   * register user id
	   * 
	   */
	  private String did;

	  /**
	   * error message when register failed
	   * 
	   */
	  private String message;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
