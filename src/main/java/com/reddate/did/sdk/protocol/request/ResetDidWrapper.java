package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;

import com.reddate.did.sdk.protocol.common.DidDocument;

/**
 * 
 * Reset did public key request data structure
 * 
 *
 */
public class ResetDidWrapper implements Serializable{

	/**
	 * did document information
	 * 
	 */
	private DidDocument didDoc;
	
	/**
	 * authority public key sign value
	 * 
	 */
	private String authPubKeySign;

	public DidDocument getDidDoc() {
		return didDoc;
	}

	public void setDidDoc(DidDocument didDoc) {
		this.didDoc = didDoc;
	}

	public String getAuthPubKeySign() {
		return authPubKeySign;
	}

	public void setAuthPubKeySign(String authPubKeySign) {
		this.authPubKeySign = authPubKeySign;
	}
	
}
