package com.reddate.did.sdk.protocol.common;

import java.io.Serializable;

/**
 * 
 * The proof  data structure
 * 
 * 
 *
 *
 */
public class Proof implements Serializable {

    /**
     *  Encryption algorithm of signature data
     */
    private String type;

    /**
     *  The did of the user who created the signature data
     */
    private String creator;

    /**
     *  Signature value
     */
    private String signatureValue;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreator() { return creator; }

    public void setCreator(String creator) { this.creator = creator; }

    public String getSignatureValue() {
        return signatureValue;
    }

    public void setSignatureValue(String signatureValue) {
        this.signatureValue = signatureValue;
    }
}
