package com.reddate.did.sdk.protocol.request.hub;

import java.io.Serializable;

/**
 * Transfer ownerReq request data structure
 */
public class TransferOwnerReq implements HubBaseReq, Serializable {

	/**
	 * user id
	 */
	private String uid;

	/**
	 * data url
	 */
	private String url;

	/**
	 * hub data new owner's user id
	 * 
	 */
	private String newOwnerUid;
	
	/**
	 * hub data new owner's public key
	 * 
	 */
	private String newOwnerPublicKey;

	/**
	 * New Key information
	 */
	private String newKey;

	/**
	 * sign value
	 */
	private String sign;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNewOwnerUid() {
		return newOwnerUid;
	}

	public void setNewOwnerUid(String newOwnerUid) {
		this.newOwnerUid = newOwnerUid;
	}

	public String getNewKey() {
		return newKey;
	}

	public void setNewKey(String newKey) {
		this.newKey = newKey;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getNewOwnerPublicKey() {
		return newOwnerPublicKey;
	}

	public void setNewOwnerPublicKey(String newOwnerPublicKey) {
		this.newOwnerPublicKey = newOwnerPublicKey;
	}

	@Override
	public String contractToString() {
		StringBuffer buffer = new StringBuffer();
		if (uid != null) {
			buffer.append(uid);
		}

		if (url != null) {
			buffer.append(url);
		}

		if (newOwnerUid != null) {
			buffer.append(newOwnerUid);
		}

		if (newKey != null) {
			buffer.append(newKey);
		}
		
		if(newOwnerPublicKey != null) {
			buffer.append(newOwnerPublicKey);
		}
		
		return buffer.toString();
	}

}
