package com.reddate.did.sdk.param.req;

import java.io.Serializable;


/**
 * 
 * Check permission request data structure
 * 
 *
 */
public class CheckPermission implements Serializable{

	/**
	 * 
	 * did identifier
	 * 
	 */
	private String did;
	
	/**
	 * 
	 * resource owner did
	 * 
	 */
	private String ownerDid;
	
	/**
	 * grant user did
	 * 
	 */
	private String grantDid;
	
	/**
	 * resource url
	 * 
	 * 
	 */
	private String url;
	
	/**
	 * grant information
	 * 
	 * 
	 */
	private Operation grant;

	/**
	 * 
	 * user private key
	 * 
	 */
	private String privateKey;
	
	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getGrantDid() {
		return grantDid;
	}

	public void setGrantDid(String grantDid) {
		this.grantDid = grantDid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getOwnerDid() {
		return ownerDid;
	}

	public void setOwnerDid(String ownerDid) {
		this.ownerDid = ownerDid;
	}
	
}
