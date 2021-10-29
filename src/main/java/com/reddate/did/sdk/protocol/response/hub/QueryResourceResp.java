package com.reddate.did.sdk.protocol.response.hub;

import java.io.Serializable;


/**
 * 
 * Query resource response data structure
 * 
 *
 */
public class QueryResourceResp implements Serializable {

	/**
	 * resource content
	 * 
	 */
	  private String content;

	  /**
	   * 
	   * resource encpty key
	   * 
	   */
	  private String key;

	  public String getContent() {
	    return content;
	  }

	  public void setContent(String content) {
	    this.content = content;
	  }

	  public String getKey() {
	    return key;
	  }

	  public void setKey(String key) {
	    this.key = key;
	  }
	
}
