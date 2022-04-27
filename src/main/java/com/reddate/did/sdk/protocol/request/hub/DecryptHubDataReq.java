package com.reddate.did.sdk.protocol.request.hub;

import java.io.Serializable;

/**
 * Decrypt Datahub Data request data structure
 */
public class DecryptHubDataReq implements Serializable{

	/**
	 * the ciphertext content
	 */
	private String content;
	
	/**
	 * the ciphertext secret key
	 */
	private String encptyKey;
	
	/**
	 * the private key
	 */
	private String privateKey;
	
	/**
	 * crypto type: ECDSA / SM
	 */
	private String cryptoType;
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getEncptyKey() {
		return encptyKey;
	}
	
	public void setEncptyKey(String encptyKey) {
		this.encptyKey = encptyKey;
	}
	
	public String getPrivateKey() {
		return privateKey;
	}
	
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	
	public String getCryptoType() {
		return cryptoType;
	}
	
	public void setCryptoType(String cryptoType) {
		this.cryptoType = cryptoType;
	}
	
}
