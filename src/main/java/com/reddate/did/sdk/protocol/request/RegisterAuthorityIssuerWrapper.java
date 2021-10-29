package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;


/**
 * 
 * Register authority issuer wrapper data structure
 * 
 *
 */
public class RegisterAuthorityIssuerWrapper implements Serializable {

	/**
	 * did identify
	 */
    private String did;

    /**
     * authority issuer name
     * 
     */
    private String name;
   
    /**
     * authority issuer public key sign value
     * 
     * 
     */
    private String publicKeySign;

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublicKeySign() {
		return publicKeySign;
	}

	public void setPublicKeySign(String publicKeySign) {
		this.publicKeySign = publicKeySign;
	}
	
    
    
    
    
}
