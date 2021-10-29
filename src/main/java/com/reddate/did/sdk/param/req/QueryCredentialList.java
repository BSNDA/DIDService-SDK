package com.reddate.did.sdk.param.req;

import java.io.Serializable;

/**
 * 
 * Query credential list request data structure
 * 
 *
 */
public class QueryCredentialList implements Serializable{

	/**
	 * 
	 * current page number
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
	 * 
	 * did identifier 
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
