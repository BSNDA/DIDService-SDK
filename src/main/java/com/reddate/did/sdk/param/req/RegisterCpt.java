package com.reddate.did.sdk.param.req;

import java.io.Serializable;
import java.util.Map;

import com.reddate.did.sdk.protocol.request.JsonSchema;

/**
 * Register CPT template data structure
 */
public class RegisterCpt implements Serializable {

	/**
	 * did identifier
	 */
	private String did;

	/**
	 * private key
	 */
	private String privateKey;

	/**
	 * CPT template schema Map data
	 */
	private Map<String, JsonSchema> cptJsonSchema;

	/**
	 * CPT template title
	 */
	private String title;

	/**
	 * CPT template long description
	 */
	private String description;

	/**
	 * CPT template type, default is Proof
	 */
	private String type;

	/**
	 * CPT template Id number, when create CPT template will auto generated a Id
	 */
	private Long cptId;

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

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

}
