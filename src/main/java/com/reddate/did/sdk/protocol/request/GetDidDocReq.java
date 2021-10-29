package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;

/**
 * 
 * Query did document request data structure
 * 
 *
 */
public class GetDidDocReq implements Serializable{
	
	/**
	 * did identify
	 * 
	 */
	private String did;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }
}
