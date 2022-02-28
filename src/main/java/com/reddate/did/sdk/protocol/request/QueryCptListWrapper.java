package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;

/**
 * Query CPT List wrapper data structure
 */
public class QueryCptListWrapper implements Serializable {

	/**
	 * current page number
	 */
	private Integer page;

	/**
	 * page size
	 */
	private Integer size;

	/**
	 * did identify
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
