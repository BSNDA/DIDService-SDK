package com.reddate.did.sdk.param.req;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Map;

/**
 * Create credential request data structure
 */
public class CreateCredential implements Serializable {

	/**
	 * Voucher template number
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
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
	
	/**
	 * Brief description of the voucher
	 */
	private String shortDesc;

	/**
	 * Detailed description of the voucher
	 */
	private String longDesc;

	/**
	 * private key string value
	 */
	private String privateKey;

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

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public String getLongDesc() {
		return longDesc;
	}

	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}

}
