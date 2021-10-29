package com.reddate.did.sdk.protocol.common;

/**
 * 
 * Did base document data structure
 * 
 * 
 * 
 *
 */
public class BaseDidDocument {

    /**
     * Format standard
     */
    private String context;

    /**
     * Authenticated public key
     */
    private PublicKey authentication;

    /**
     * Standby public key
     */
    private PublicKey recovery;

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        return context;
    }

    public void setAuthentication(PublicKey authentication) {
        this.authentication = authentication;
    }

    public PublicKey getAuthentication() {
        return authentication;
    }

    public void setRecovery(PublicKey recovery) {
        this.recovery = recovery;
    }

    public PublicKey getRecovery() {
        return recovery;
    }


}
