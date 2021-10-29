package com.reddate.did.sdk.protocol.response;

import java.io.Serializable;

import com.reddate.did.sdk.protocol.common.DidDocument;
import com.reddate.did.sdk.protocol.common.KeyPair;

/**
 * Create did and document data structure
 * 
 * 
 *
 */
public class CreateDidData implements Serializable{

    /**
     *  Did created
     */
    private String did;

    /**
     *  Master public private key information
     */
    private KeyPair authKeyInfo;

    /**
     *  Spare public private key information
     */
    private KeyPair recyKeyInfo;

    /**
     *  Not on the chain, the did document information will be returned
     */
    private DidDocument didDocument;

    public String getDid() { return did; }

    public void setDid(String did) { this.did = did; }

    public KeyPair getAuthKeyInfo() { return authKeyInfo; }

    public void setAuthKeyInfo(KeyPair authKeyInfo) { this.authKeyInfo = authKeyInfo; }

    public KeyPair getRecyKeyInfo() { return recyKeyInfo; }

    public void setRecyKeyInfo(KeyPair recyKeyInfo) { this.recyKeyInfo = recyKeyInfo; }

    public DidDocument getDidDocument() { return didDocument; }

    public void setDidDocument(DidDocument didDocument) { this.didDocument = didDocument; }
}
