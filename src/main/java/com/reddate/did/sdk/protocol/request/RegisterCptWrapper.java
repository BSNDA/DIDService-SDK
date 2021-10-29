package com.reddate.did.sdk.protocol.request;


import java.io.Serializable;
import java.util.Map;

/**
 * 
 * Register CPT wrapper data structure
 * 
 * 
 *
 */
public class RegisterCptWrapper implements Serializable {

    /**
     * Did of the issuer of the voucher template
     */
    private String did;

    /**
     * Map type Json Schema information
     */
    private Map<String, JsonSchema> cptJsonSchema;

    /**
     * Voucher template title
     */
    private String title;

    /**
     * Detailed description of the voucher template
     */
    private String description;

    /**
     * Document Type：Proof
     */
    private String type;

    private Proof proof;
    
    private Long cptId;

    /**
     *  Creation time
     */
    private String create;

    /**
     *  Update time
     */
    private String update;

    public Long getCptId() {
        return cptId;
    }

    public void setCptId(Long cptId) {
        this.cptId = cptId;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public Map<String, JsonSchema> getCptJsonSchema() {
        return cptJsonSchema;
    }

    public void setCptJsonSchema(Map<String, JsonSchema> cptJsonSchema) {
        this.cptJsonSchema = cptJsonSchema;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


	public Proof getProof() {
		return proof;
	}

	public void setProof(Proof proof) {
		this.proof = proof;
	}

	public String getCreate() {
		return create;
	}

	public void setCreate(String create) {
		this.create = create;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}
	
}
