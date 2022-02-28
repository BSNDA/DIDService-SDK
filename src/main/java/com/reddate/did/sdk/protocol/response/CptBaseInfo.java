package com.reddate.did.sdk.protocol.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * CPT base information data structure
 */
public class CptBaseInfo implements Serializable {

	/**
	 * Voucher template number
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long cptId;

	/**
	 * Voucher template version
	 */
	private Integer cptVersion;

	public Long getCptId() {
		return cptId;
	}

	public void setCptId(Long cptId) {
		this.cptId = cptId;
	}

	public Integer getCptVersion() {
		return cptVersion;
	}

	public void setCptVersion(Integer cptVersion) {
		this.cptVersion = cptVersion;
	}
}
