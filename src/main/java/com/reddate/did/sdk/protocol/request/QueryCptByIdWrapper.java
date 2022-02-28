package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;

/**
 * query CPT template wrapper data structure
 */
public class QueryCptByIdWrapper implements Serializable {

	/**
	 * CPT tempalte Id
	 */
	private Long cptId;

	public Long getCptId() {
		return cptId;
	}

	public void setCptId(Long cptId) {
		this.cptId = cptId;
	}

}
