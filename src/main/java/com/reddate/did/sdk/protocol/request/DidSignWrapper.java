package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;

/**
 * 
 * verify did identify sign data structure
 * 
 *
 */
public class DidSignWrapper implements Serializable{

	/**
	 * did identifier
	 * 
	 */
    private String did;
    
	/**
	 * did identifier sign value
	 * 
	 * 
	 */
    private String didSign;

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getDidSign() {
		return didSign;
	}

	public void setDidSign(String didSign) {
		this.didSign = didSign;
	}
	
	
}
