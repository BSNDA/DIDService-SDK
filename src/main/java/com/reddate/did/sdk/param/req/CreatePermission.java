package com.reddate.did.sdk.param.req;

import java.io.Serializable;

/**
 * Create permission request data structure
 */
public class CreatePermission implements Serializable {

	/**
	 * resource owner did
	 */
	private String did;

	/**
	 * resource url
	 */
	private String url;

	/**
	 * grant information
	 */
	private Operation grant;

	/**
	 * grant user did
	 */
	private String grantDid;

	/**
	 * grant user public key
	 */
	private String grantPublicKey;

	/**
	 * resource owner private key
	 */
	private String privateKey;

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Operation getGrant() {
		return grant;
	}

	public void setGrant(Operation grant) {
		this.grant = grant;
	}

	public String getGrantDid() {
		return grantDid;
	}

	public void setGrantDid(String grantDid) {
		this.grantDid = grantDid;
	}

	public String getGrantPublicKey() {
		return grantPublicKey;
	}

	public void setGrantPublicKey(String grantPublicKey) {
		this.grantPublicKey = grantPublicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

}
