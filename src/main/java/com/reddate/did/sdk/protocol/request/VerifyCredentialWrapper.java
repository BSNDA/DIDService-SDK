package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;

import com.reddate.did.sdk.protocol.common.PublicKey;


/**
 * 
 * Verify credential wrapper data structure
 * 
 *
 */
public class VerifyCredentialWrapper implements Serializable {

	/**
	 * credential wrapper information
	 * 
	 */
    private CredentialWrapper credentialWrapper;

    /**
     * 
     * public key information
     */
    private PublicKey publicKey;

    public CredentialWrapper getCredentialWrapper() {
        return credentialWrapper;
    }

    public void setCredentialWrapper(CredentialWrapper credentialWrapper) {
        this.credentialWrapper = credentialWrapper;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
