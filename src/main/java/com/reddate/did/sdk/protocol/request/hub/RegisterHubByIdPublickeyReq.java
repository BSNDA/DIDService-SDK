package com.reddate.did.sdk.protocol.request.hub;

import java.io.Serializable;

/**
 * Registe hubdata by publickey structure
 */
public class RegisterHubByIdPublickeyReq implements Serializable {

	/**
	 * user Id
	 */
	private String id;
	
	/**
	 * public value String
	 */
	private String publicKey;
	
	/**
	 * encryption Algorithm
	 */
	private String cryptoType;

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCryptoType() {
		return cryptoType;
	}

	public void setCryptoType(String cryptoType) {
		this.cryptoType = cryptoType;
	}

}
