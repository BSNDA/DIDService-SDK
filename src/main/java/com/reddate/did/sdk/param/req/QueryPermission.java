package com.reddate.did.sdk.param.req;

import java.io.Serializable;

/**
 * Query permission request data structure
 */
public class QueryPermission implements Serializable{

	/**
	 * did identifier
	 */
	private String uid;

	/**
	 * grant user did
	 */
	private String grantUid;
	
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

	public String getGrantUid() {
		return grantUid;
	}

	public void setGrantUid(String grantUid) {
		this.grantUid = grantUid;
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
