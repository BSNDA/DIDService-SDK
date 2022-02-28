package com.reddate.did.sdk.param.req;

import java.io.Serializable;

/**
 * Save resource request data structure
 */
public class SaveResource implements Serializable {

	/**
	 * did identifier
	 */
	private String did;

	/**
	 * resource content
	 */
	private String content;

	/**
	 * resource url
	 */
	private String url;

	/**
	 * resource owner did
	 */
	private String ownerDid;

	/**
	 * grant information
	 */
	private Operation grant;

	/**
	 * user private key
	 */
	private String privateKey;

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
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

	public String getOwnerDid() {
		return ownerDid;
	}

	public void setOwnerDid(String ownerDid) {
		this.ownerDid = ownerDid;
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
