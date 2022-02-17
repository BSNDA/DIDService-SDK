package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;
import java.util.Map;

import com.reddate.did.sdk.protocol.response.CptBaseInfo;

/**
 * CPT information data structure
 */
public class CptInfo extends CptBaseInfo implements Serializable {

	/**
	 * Field list of voucher template
	 */
	private Map<String, JsonSchema> cptJsonSchema;

	/**
	 * A short description of the voucher template
	 */
	private String title;

	/**
	 * Detailed description of the voucher template
	 */
	private String description;

	/**
	 * Did of the user who created the voucher template
	 */
	private String publisherDid;

	/**
	 * Signature data structure
	 */
	private Proof proof;

	/**
	 * Creation time
	 */
	private String create;

	/**
	 * Update time
	 */
	private String update;

	public String getPublisherDid() {
		return publisherDid;
	}

	public void setPublisherDid(String publisherDid) {
		this.publisherDid = publisherDid;
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

	public Map<String, JsonSchema> getCptJsonSchema() {
		return cptJsonSchema;
	}

	public void setCptJsonSchema(Map<String, JsonSchema> cptJsonSchema) {
		this.cptJsonSchema = cptJsonSchema;
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
