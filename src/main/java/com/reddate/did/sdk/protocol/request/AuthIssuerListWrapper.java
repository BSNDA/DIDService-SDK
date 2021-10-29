package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;

/**
 * 
 * Auth issuer list wrapper data structure
 * 
 * 
 *
 */
public class AuthIssuerListWrapper implements Serializable {

	/**
	 * current page nubmer
	 * 
	 * 
	 */
	private Integer page;
	
	/**
	 * 
	 * page size
	 * 
	 */
	private Integer size;
	
	/**
	 * user id
	 * 
	 */
	private String did;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}
	
	
}
