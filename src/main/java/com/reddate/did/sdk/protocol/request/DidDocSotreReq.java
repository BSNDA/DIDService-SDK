package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;

import com.reddate.did.sdk.protocol.common.DidDocument;

/**
 * 
 * Did document store data structure
 * 
 *
 */
public class DidDocSotreReq implements Serializable{

	/**
	 * 
	 * did document information
	 */
	private DidDocument didDoc;

	public DidDocument getDidDoc() {
		return didDoc;
	}

	public void setDidDoc(DidDocument didDoc) {
		this.didDoc = didDoc;
	}
	
}
