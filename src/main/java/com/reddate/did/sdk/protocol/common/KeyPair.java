package com.reddate.did.sdk.protocol.common;

import java.io.Serializable;

/**
 * 
 * The key pair data structure
 * 
 * 
 *
 */
public class KeyPair implements Serializable{

    /**
     *  Primary private key
     */
    private String privateKey;

    /**
     *  Primary public key
     */
    private String publicKey;

    /**
     *  encryption algorithm
     */
    private String type;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
