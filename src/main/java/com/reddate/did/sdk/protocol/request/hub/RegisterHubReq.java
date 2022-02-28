package com.reddate.did.sdk.protocol.request.hub;

import java.io.Serializable;

/**
 * Registe hubdata structure
 */
public class RegisterHubReq implements Serializable {

	/**
	 * did idnetify
	 */
	private String did;

	/**
	 * public value String
	 */
	private String publicKey;

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

}
