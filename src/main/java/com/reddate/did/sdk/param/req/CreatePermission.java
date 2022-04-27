package com.reddate.did.sdk.param.req;

import java.io.Serializable;

/**
 * Create permission request data structure
 */
public class CreatePermission implements Serializable {

	/**
	 * resource owner user id
	 */
	private String uid;

	/**
	 * resource url
	 */
	private String url;

	/**
	 * grant information
	 */
	private Operation grant;

	/**
	 * grant user id
	 */
	private String grantUid;

	/**
	 * grant user public key
	 */
	private String grantPublicKey;

	/**
	 * resource owner private key
	 */
	private String privateKey;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public String getGrantUid() {
		return grantUid;
	}

	public void setGrantUid(String grantUid) {
		this.grantUid = grantUid;
	}

	public String getGrantPublicKey() {
		return grantPublicKey;
	}

	public void setGrantPublicKey(String grantPublicKey) {
		this.grantPublicKey = grantPublicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	
}
