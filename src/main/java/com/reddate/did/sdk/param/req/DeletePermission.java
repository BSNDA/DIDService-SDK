package com.reddate.did.sdk.param.req;

import java.io.Serializable;

/**
 * 
 * Delete permission request data structure
 * 
 * 
 *
 */
public class DeletePermission  implements Serializable{

	/**
	 * 
	 * identify hub user did
	 * 
	 */
	private String did;
	
	/**
	 * 
	 * resource url
	 * 
	 */
	private String url;
	
	/**
	 * grant identify hub user did
	 * 
	 * 
	 */
	private String grantDid; 
	
	/**
	 * 
	 * grant information
	 * 
	 */
	private Operation grant;
	
	/**
	 * 
	 * identify hub user private key
	 * 
	 */
	private String privateKey;

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGrantDid() {
		return grantDid;
	}

	public void setGrantDid(String grantDid) {
		this.grantDid = grantDid;
	}

	public Operation getGrant() {
		return grant;
	}

	public void setGrant(Operation grant) {
		this.grant = grant;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	
	
	
	
}
