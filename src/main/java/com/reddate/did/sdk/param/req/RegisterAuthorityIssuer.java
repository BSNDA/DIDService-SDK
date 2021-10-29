package com.reddate.did.sdk.param.req;


/**
 * 
 * Register authority issuer description data structure
 * 
 *
 */
public class RegisterAuthorityIssuer extends AuthorityIssuer{
    
	/**
	 * 
	 * private key
	 * 
	 */
	private String privateKey;

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	
}
