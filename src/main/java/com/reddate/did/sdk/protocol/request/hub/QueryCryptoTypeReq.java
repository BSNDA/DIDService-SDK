package com.reddate.did.sdk.protocol.request.hub;

import java.io.Serializable;

public class QueryCryptoTypeReq implements HubBaseReq, Serializable {

	private String uid;

	private String publicKey;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	@Override
	public String contractToString() {
		StringBuffer buffer = new StringBuffer();
		if (uid != null) {
			buffer.append(uid);
		}

		if (publicKey != null) {
			buffer.append(publicKey);
		}

		return buffer.toString();
	}

}
