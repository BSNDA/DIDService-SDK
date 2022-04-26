// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.sdk.param.ipfs;

import java.io.Serializable;

/**
 * Save resource information response data structure
 */
public class SaveResource implements Serializable {

	/**
	 * The key value of the file in ipfs network
	 */
	private String hash;

	/**
	 * The encryption key for the file
	 */
	private String key;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
