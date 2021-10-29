package com.reddate.did.sdk.protocol.common;

import java.io.Serializable;

/**
 * 
 *  Did information data structure
 * 
 * 
 *
 */
public class DidInfo implements Serializable {

    /**
     * did Specification
     */
    private String context;

    /**
     * Public key list
     */
    private PublicKey publicKey ;

    /**
     * The master public key list of did, storing the id in the public key
     */
    private String[] authentication;

    /**
     *  List of did's alternate public keys,
     */
    private String[] recovery;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String[] authentication) {
        this.authentication = authentication;
    }

    public String[] getRecovery() {
        return recovery;
    }

    public void setRecovery(String[] recovery) {
        this.recovery = recovery;
    }
}
