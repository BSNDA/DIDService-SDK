package com.reddate.did.sdk.param.req;

import java.io.Serializable;

/**
 * Check permission request data structure
 */
public class CheckPermission implements Serializable {

	/**
	 * user id
	 */
	private String uid;

	/**
	 * resource owner user id
	 */
	private String ownerUid;

	/**
	 * grant user did
	 */
	private String grantUid;

	/**
	 * resource url
	 */
	private String url;

	/**
	 * grant information
	 */
	private Operation grant;

	/**
	 * user private key
	 */
	private String privateKey;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOwnerUid() {
		return ownerUid;
	}

	public void setOwnerUid(String ownerUid) {
		this.ownerUid = ownerUid;
	}

	public String getGrantUid() {
		return grantUid;
	}

	public void setGrantUid(String grantUid) {
		this.grantUid = grantUid;
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

}
