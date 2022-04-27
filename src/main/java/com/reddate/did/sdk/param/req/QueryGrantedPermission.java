package com.reddate.did.sdk.param.req;

import java.io.Serializable;

/**
 * Query permission to yourself request data structure
 */
public class QueryGrantedPermission implements Serializable{

	/**
	 * user id
	 */
	private String uid;

	/**
	 * resource owner user did
	 */
	private String ownerUid;
	
	/**
	 * grant permission
	 */
	private Operation grant;
	
	/**
	 * used flag
	 */
	private UsedFlag flag;

	/**
	 * private key
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

	public Operation getGrant() {
		return grant;
	}

	public void setGrant(Operation grant) {
		this.grant = grant;
	}

	public UsedFlag getFlag() {
		return flag;
	}

	public void setFlag(UsedFlag flag) {
		this.flag = flag;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

}
