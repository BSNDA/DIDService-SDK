package com.reddate.did.sdk.param.req;

import java.io.Serializable;


/**
 * Transfer data owner request data structure
 */
public class TransferOwner implements Serializable{

	/**
	 * user id
	 */
	private String uid;
	
	/**
	 * resource url
	 */
	private String url;
	
	/**
	 * hub data new owner's user id
	 * 
	 */
	private String newOwnerUid;
	
	/**
	 * hub data new owner's public key
	 * 
	 */
	private String newOwnerPublicKey;
	
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNewOwnerUid() {
		return newOwnerUid;
	}

	public void setNewOwnerUid(String newOwnerUid) {
		this.newOwnerUid = newOwnerUid;
	}

	public String getNewOwnerPublicKey() {
		return newOwnerPublicKey;
	}

	public void setNewOwnerPublicKey(String newOwnerPublicKey) {
		this.newOwnerPublicKey = newOwnerPublicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	
}
