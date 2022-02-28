package com.reddate.did.sdk.protocol.response;

import java.io.Serializable;

import com.reddate.did.sdk.protocol.request.CptInfo;

/**
 * CPT template informations
 */
public class CptInfos implements Serializable {

	/**
	 * CPT tempalte page information
	 */
	private Pages<CptInfo> cptInfoPages;

	public Pages<CptInfo> getCptInfoPages() {
		return cptInfoPages;
	}

	public void setCptInfoPages(Pages<CptInfo> cptInfoPages) {
		this.cptInfoPages = cptInfoPages;
	}
}
