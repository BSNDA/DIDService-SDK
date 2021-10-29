package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;

/**
 * 
 * Did document request data structure
 * 
 *
 */
public class DidDocumentReq implements Serializable {

	/**
	 * did identify
	 * 
	 * 
	 */
	private String did;

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}
	
	
	
}
