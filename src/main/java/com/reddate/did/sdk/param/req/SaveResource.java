package com.reddate.did.sdk.param.req;

import java.io.Serializable;

/**
 * Save resource request data structure
 */
public class SaveResource implements Serializable {

	/**
	 * user id
	 */
	private String uid;

	/**
	 * resource content
	 */
	private String content;

	/**
	 * resource url
	 */
	private String url;

	/**
	 * resource owner user id
	 */
	private String ownerUid;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOwnerUid() {
		return ownerUid;
	}

	public void setOwnerUid(String ownerUid) {
		this.ownerUid = ownerUid;
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
