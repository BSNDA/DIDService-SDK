package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;

import com.reddate.did.sdk.protocol.common.DidDocument;

/**
 * Store did document wrapper data structure
 */
public class StoreDidDocumentWrapper implements Serializable {

	/**
	 * did document information
	 */
	private DidDocument didDocument;

	public DidDocument getDidDocument() {
		return didDocument;
	}

	public void setDidDocument(DidDocument didDocument) {
		this.didDocument = didDocument;
	}
}
