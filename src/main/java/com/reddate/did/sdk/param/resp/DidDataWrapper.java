package com.reddate.did.sdk.param.resp;

import java.io.Serializable;

import com.reddate.did.sdk.protocol.common.KeyPair;

/**
 * Generated did document result wrapper data structure
 * 
 * 
 *
 */
public class DidDataWrapper implements Serializable {

	/**
	 * Did Identifier
	 * 
	 */
	private String did;
	
	/**
	 * Main authenticate key pair
	 * 
	 */
	private KeyPair authKeyInfo;
	
	/**
	 * Recovery authenticate key pair
	 * 
	 */
	private KeyPair recyKeyInfo;
	
	/**
	 * Did document defined
	 * 
	 */
	private DocumentInfo document;
	
	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public KeyPair getAuthKeyInfo() {
		return authKeyInfo;
	}

	public void setAuthKeyInfo(KeyPair authKeyInfo) {
		this.authKeyInfo = authKeyInfo;
	}

	public KeyPair getRecyKeyInfo() {
		return recyKeyInfo;
	}

	public void setRecyKeyInfo(KeyPair recyKeyInfo) {
		this.recyKeyInfo = recyKeyInfo;
	}

	public DocumentInfo getDocument() {
		return document;
	}

	public void setDocument(DocumentInfo document) {
		this.document = document;
	}

}
