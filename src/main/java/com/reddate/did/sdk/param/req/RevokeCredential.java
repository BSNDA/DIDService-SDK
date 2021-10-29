package com.reddate.did.sdk.param.req;

import java.io.Serializable;


/**
 * 
 * Revoke credential request data strucutre
 * 
 *
 */
public class RevokeCredential implements Serializable {

	/**
	 * 
	 * credential id
	 * 
	 */
	private String credId;
	
	/**
	 * 
	 * CPT template id
	 * 
	 */
	private Long cptId;
	
	/**
	 * 
	 * credential issuer did
	 * 
	 */
	private String did;
	
	/**
	 * 
	 * credential issuer private key
	 * 
	 */
	private String privateKey;

	public String getCredId() {
		return credId;
	}

	public void setCredId(String credId) {
		this.credId = credId;
	}

	public Long getCptId() {
		return cptId;
	}

	public void setCptId(Long cptId) {
		this.cptId = cptId;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	
	
}
