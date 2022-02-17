package com.reddate.did.sdk.param.req;

import java.io.Serializable;

/**
 * Query resource operation history request data structure
 */
public class QueryResourceHistory implements Serializable{

	/**
	 * did identifier
	 */
	private String did;

	/**
	 * resource url
	 */
	private String url;
	
	/**
	 * resource operation
	 */
	private Operation operation;

	/**
	 * private key
	 */
	private String privateKey;

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

}
