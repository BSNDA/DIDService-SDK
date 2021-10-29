package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;

import com.reddate.did.sdk.protocol.common.DidDocument;
import com.reddate.did.sdk.protocol.common.PublicKey;


/**
 * 
 * Verify did document wrapper
 * 
 *
 */
public class VerifyDIdDocumentWrapper implements Serializable {

	/**
	 * did document information
	 * 
	 * 
	 */
    private DidDocument didDocument;

    /**
     * 
     * public key information
     * 
     */
    private PublicKey publicKey;

    public DidDocument getDidDocument() {
        return didDocument;
    }

    public void setDidDocument(DidDocument didDocument) {
        this.didDocument = didDocument;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
