package com.reddate.did.sdk.param.resp;

import java.io.Serializable;

/**
 * Save resource result description data structure
 */
public class SaveResourceResult implements Serializable {

	/**
	 * the resource url
	 */
	private String url;

	/**
	 * the resource encryption key
	 */
	private String encryptKey;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

}
