package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;

/**
 * Revoke credential request data structure
 */
public class RevokCredentialReq implements Serializable {

	/**
	 * credential id
	 */
	private String credId;

	/**
	 * CPT template id
	 */
	private Long cptId;

	/**
	 * did identify
	 */
	private String did;

	/**
	 * revoke date
	 */
	private String revokeDate;

	/**
	 * public key sign value
	 */
	private String publicKeySign;

	/**
	 * revoke sign value
	 */
	private String revokeSign;

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

	public String getPublicKeySign() {
		return publicKeySign;
	}

	public void setPublicKeySign(String publicKeySign) {
		this.publicKeySign = publicKeySign;
	}

	public String getRevokeDate() {
		return revokeDate;
	}

	public void setRevokeDate(String revokeDate) {
		this.revokeDate = revokeDate;
	}

	public String getRevokeSign() {
		return revokeSign;
	}

	public void setRevokeSign(String revokeSign) {
		this.revokeSign = revokeSign;
	}

}
