package com.reddate.did.sdk.protocol.common;

import java.io.Serializable;

/**
 * 
 * The public key data structure
 * 
 * 
 *
 */
public class PublicKey implements Serializable {

    /**
     * encryption algorithm
     */
    private String type;

    /**
     * Public key value
     */
    private String publicKey;

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getPublicKey() { return publicKey; }

    public void setPublicKey(String publicKey) { this.publicKey = publicKey; }
}
