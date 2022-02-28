package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;
import java.util.Map;

/**
 * CreateCredential Request data structure
 */
public class CreateCredentialReq implements Serializable {

	/**
	 * Voucher template number
	 */
	private Long cptId;

	/**
	 * Did of the issuer of the certificate template
	 */
	private String issuerDid;

	/**
	 * Did of the user who applied for the credential
	 */
	private String userDid;

	/**
	 * Voucher expiration date
	 */
	private String expirationDate;

	/**
	 * The claim data, the claim data need to correspond to the format of the cpt
	 * template
	 */
	private Map<String, Object> claim;

	/**
	 * Voucher type: Proof
	 */
	private String type;

	public Long getCptId() {
		return cptId;
	}

	public void setCptId(Long cptId) {
		this.cptId = cptId;
	}

	public String getIssuerDid() {
		return issuerDid;
	}

	public void setIssuerDid(String issuerDid) {
		this.issuerDid = issuerDid;
	}

	public String getUserDid() {
		return userDid;
	}

	public void setUserDid(String userDid) {
		this.userDid = userDid;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Map<String, Object> getClaim() {
		return claim;
	}

	public void setClaim(Map<String, Object> claim) {
		this.claim = claim;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
