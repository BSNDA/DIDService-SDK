package com.reddate.did.sdk.protocol.request.hub;

import java.io.Serializable;

/**
 * Save resource request data structure
 */
public class SaveResourceReq implements HubBaseReq, Serializable {

	/**
	 * user id
	 */
	private String uid;

	/**
	 * resource content
	 */
	private String content;

	/**
	 * resource url
	 */
	private String url;

	/**
	 * owner user id
	 */
	private String ownerUid;

	/**
	 * grant information
	 */
	private String grant;

	/**
	 * Key information
	 */
	private String key;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOwnerUid() {
		return ownerUid;
	}

	public void setOwnerUid(String ownerUid) {
		this.ownerUid = ownerUid;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String contractToString() {
		StringBuffer buffer = new StringBuffer();
		if (uid != null) {
			buffer.append(uid);
		}
		if (content != null) {
			buffer.append(content);
		}
		if (url != null) {
			buffer.append(url);
		}
		if (ownerUid != null) {
			buffer.append(ownerUid);
		}

		if (grant != null) {
			buffer.append(grant);
		}
		if (key != null) {
			buffer.append(key);
		}
		return buffer.toString();
	}

}
