package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reddate.did.sdk.protocol.response.BaseCredential;


/**
 * 
 * Credential wrapper data structure
 * 
 *
 */
public class CredentialWrapper extends BaseCredential implements Serializable {


    /**
     * VERSION NUMBER
     */
    private String context;

    /**
     * Certificate Number
     */
    private String id;

    /**
     * DOCUMENT TYPE：Proof
     */
    private String type;

    /**
     * cpt SERIAL NUMBER
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long cptId;

    /**
     * Voucher issuer did
     */
    private String issuerDid;

    /**
     * Of the user requesting the credential did
     */
    private String userDid;

    /**
     * Expiration log
     */
    private String expirationDate;

    /**
     * Creation time
     */
    private String created;

    /**
     * Brief description of the voucher
     */
    private String shortDesc;

    /**
     * Detailed description of the voucher
     */
    private String longDesc;

    /**
     * Declared data
     */
    private Map<String, Object> claim;

    /**
     * Signature data structure
     */
    private Map<String, Object> proof;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCptId() {
        return cptId;
    }

    public void setCptId(Long cptId) {
        this.cptId = cptId;
    }

    public String getIssuerDid() {
        return issuerDid;
    }

    public void setIssuerDid(String issuerDid) {
        this.issuerDid = issuerDid;
    }

    public String getUserDid() {
        return userDid;
    }

    public void setUserDid(String userDid) {
        this.userDid = userDid;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String getCreated() {
        return created;
    }

    @Override
    public void setCreated(String created) {
        this.created = created;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public Map<String, Object> getClaim() {
        return this.claim;
    }

    public void setClaim(Map<String, Object> claim) {
        this.claim = claim;
    }

    public Map<String, Object> getProof() {
        return proof;
    }

    public void setProof(Map<String, Object> proof) {
        this.proof = proof;
    }
}
