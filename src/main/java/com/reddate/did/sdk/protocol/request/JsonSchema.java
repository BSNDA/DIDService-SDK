package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;

/**
 * Json Schema data structure
 */
public class JsonSchema implements Serializable {

	/**
	 * Field Type
	 */
	private String type;

	/**
	 * Field description
	 */
	private String description;

	/**
	 * Must fill in
	 */
	private Boolean required;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}
}
