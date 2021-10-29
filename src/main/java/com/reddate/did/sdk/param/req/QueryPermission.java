package com.reddate.did.sdk.param.req;

import java.io.Serializable;

/**
 * 
 * Query permission request data structure
 * 
 * 
 *
 */
public class QueryPermission implements Serializable{

	/**
	 * did identifier
	 */
	private String did;

	/**
	 * grant user did
	 * 
	 */
	private String grantDid;
	
	/**
	 * 
	 * used flag
	 * 
	 */
	private UsedFlag flag;

	/**
	 * 
	 * private key
	 * 
	 */
	private String privateKey;

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getGrantDid() {
		return grantDid;
	}

	public void setGrantDid(String grantDid) {
		this.grantDid = grantDid;
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
