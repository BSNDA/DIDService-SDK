package com.reddate.did.sdk.param.req;

import java.io.Serializable;

/**
 * 
 * reset authority public key and private key data structure
 * 
 *
 */
public class ResetDidAuthKey implements Serializable{

	
	/**
	 * 
	 * private key string value
	 * 
	 */
	private String privateKey;
	
	/**
	 * 
	 * public key string value
	 * 
	 */
	private String publicKey;
	
	/**
	 * 
	 * authority key type
	 *
	 */
	private String type;

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
