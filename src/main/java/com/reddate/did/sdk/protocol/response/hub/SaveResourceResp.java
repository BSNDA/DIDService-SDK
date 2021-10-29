package com.reddate.did.sdk.protocol.response.hub;

import java.io.Serializable;


/**
 * 
 * Save resource response data structure
 * 
 *
 */
public class SaveResourceResp implements Serializable {

	/**
	 * resource url
	 * 
	 */
	  private String url;

	  /**
	   * encrypt key information
	   * 
	   */
	  private String encryptKey;
	  
	  /**
	   * userid
	   * 
	   */
	  private String uid;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	  
}
