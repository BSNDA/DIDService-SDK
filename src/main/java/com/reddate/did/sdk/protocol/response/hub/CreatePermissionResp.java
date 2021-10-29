package com.reddate.did.sdk.protocol.response.hub;

import java.io.Serializable;


/**
 * 
 * Create permission response data structure
 * 
 *
 */
public class CreatePermissionResp implements Serializable{

	/**
	 * resource url
	 * 
	 */
	  private String url;

	  /**
	   * 
	   * resource encryption key
	   */
	  private String key;

	  public String getUrl() {
	    return url;
	  }

	  public void setUrl(String url) {
	    this.url = url;
	  }

	  public String getKey() {
	    return key;
	  }

	  public void setKey(String key) {
	    this.key = key;
	  }
	
	
}
