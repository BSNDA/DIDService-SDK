package com.reddate.did.sdk.param.resp;

import java.io.Serializable;

import com.reddate.did.sdk.protocol.common.Proof;
import com.reddate.did.sdk.protocol.common.PublicKey;

/**
 * Did document structure description data structure
 * 
 * 
 */
public class DocumentInfo implements Serializable{

    /**
     *  Did of the document owner
     */
    private String did;

    /**
     *  The version of the document
     */
    private String version;

    /**
     *  Created time of the document
     */
    private String created;

    /**
     *  Update time of the document
     */
    private String updated;

    /**
     *  Authenticated public key
     */
    private PublicKey authentication;

    /**
     *  Standby public key
     */
    private PublicKey recovery;

    /**
     *  Signature data structure
     */
    private Proof proof;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) { this.version = version; }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public void setAuthentication(PublicKey authentication) { this.authentication = authentication; }

    public PublicKey getAuthentication() { return authentication; }

    public void setRecovery(PublicKey recovery) { this.recovery = recovery; }

    public PublicKey getRecovery() { return recovery; }

    public Proof getProof() {
        return proof;
    }

    public void setProof(Proof proof) { this.proof = proof; }
}
