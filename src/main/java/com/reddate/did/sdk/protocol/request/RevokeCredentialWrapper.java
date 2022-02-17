package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;

/**
 * Revoke credential wrapper data structure
 */
public class RevokeCredentialWrapper implements Serializable {

	/**
	 * Document number that needs to be revoked
	 */
	private String credId;

	/**
	 * Voucher template number
	 */
	private Long cptId;

	/**
	 * Did of the issuing party
	 */
	private String did;

	/**
	 * revoke date
	 */
	private String rovekeDate;

	/**
	 * proof
	 */
	private Proof proof;

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

	public Proof getProof() {
		return proof;
	}

	public void setProof(Proof proof) {
		this.proof = proof;
	}

	public String getRovekeDate() {
		return rovekeDate;
	}

	public void setRovekeDate(String rovekeDate) {
		this.rovekeDate = rovekeDate;
	}

}
