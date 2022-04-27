// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.sdk.param.ipfs;

import java.io.Serializable;

/**
 * The resource information response data structure
 */
public class ResourceInfo implements Serializable {

	/**
	 * The content of the file
	 */
	private String content;

	/**
	 * The encryption key for the file
	 */
	private String key;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
