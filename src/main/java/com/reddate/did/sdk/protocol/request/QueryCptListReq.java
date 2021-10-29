package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;

/**
 * 
 *Query CPT list data structure
 * 
 *
 */
public class QueryCptListReq implements Serializable {

	/**
	 * page szie
	 * 
	 */
    private Integer size;

    /**
     * current page nubmer
     * 
     */
    private Integer page;

    /**
     * did identify
     * 
     */
    private String did;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }
}
