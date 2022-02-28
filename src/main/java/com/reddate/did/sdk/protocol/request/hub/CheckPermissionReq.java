package com.reddate.did.sdk.protocol.request.hub;

import java.io.Serializable;

/**
 * Check permission request data structure
 */
public class CheckPermissionReq implements HubBaseReq, Serializable {

	/**
	 * user id
	 */
	private String uid;

	/**
	 * owner user id
	 */
	private String ownerUid;

	/**
	 * grant user id
	 */
	private String grantUid;

	/**
	 * resource url
	 */
	private String url;

	/**
	 * grant information
	 */
	private String grant;

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

	public String getGrantUid() {
		return grantUid;
	}

	public void setGrantUid(String grantUid) {
		this.grantUid = grantUid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGrant() {
		return grant;
	}

	public void setGrant(String grant) {
		this.grant = grant;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getOwnerUid() {
		return ownerUid;
	}

	public void setOwnerUid(String ownerUid) {
		this.ownerUid = ownerUid;
	}

	@Override
	public String contractToString() {
		StringBuffer buffer = new StringBuffer();
		if (uid != null) {
			buffer.append(uid);
		}
		if (grantUid != null) {
			buffer.append(grantUid);
		}
		if (url != null) {
			buffer.append(url);
		}
		if (grant != null) {
			buffer.append(grant);
		}
		if (ownerUid != null) {
			buffer.append(ownerUid);
		}
		return buffer.toString();
	}
}
