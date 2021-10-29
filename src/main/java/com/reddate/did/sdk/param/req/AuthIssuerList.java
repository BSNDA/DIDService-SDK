package com.reddate.did.sdk.param.req;

/**
 * 
 * Authority issuer list description data structure
 * 
 * 
 *
 */
public class AuthIssuerList {

	/**
	 * 
	 * current page number
	 * 
	 */
	private Integer page;
	
	/**
	 * page size
	 * 
	 * 
	 */
	private Integer size;
	
	/**
	 * authority issuer did
	 * 
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
