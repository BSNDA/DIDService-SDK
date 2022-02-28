package com.reddate.did.sdk.param.req;

import java.io.Serializable;

/**
 * Query permission to yourself request data structure
 */
public class QueryGrantedPermission implements Serializable{

	/**
	 * did identifier
	 */
	private String did;

	/**
	 * resource owner user did
	 */
	private String ownerDid;
	
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

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}
	
	public String getOwnerDid() {
		return ownerDid;
	}

	public void setOwnerDid(String ownerDid) {
		this.ownerDid = ownerDid;
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

	public Operation getGrant() {
		return grant;
	}

	public void setGrant(Operation grant) {
		this.grant = grant;
	}

}
