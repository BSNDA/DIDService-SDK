package com.reddate.did.sdk.protocol.request.hub;

import java.io.Serializable;

/**
 * query permission request data structure
 */
public class QueryGrantedPermissionReq implements HubBaseReq, Serializable {

	/**
	 * user id
	 */
	private String uid;

	/**
	 * resource owner user id
	 */
	private String ownerUid;

	/**
	 * grant permission : WRITE,UPDATE,READ
	 */
	private String grant;

	/**
	 * used flag
	 */
	private String flag;

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

	public String getOwnerUid() {
		return ownerUid;
	}

	public void setOwnerUid(String ownerUid) {
		this.ownerUid = ownerUid;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getGrant() {
		return grant;
	}

	public void setGrant(String grant) {
		this.grant = grant;
	}

	@Override
	public String contractToString() {
		StringBuffer buffer = new StringBuffer();
		if (uid != null) {
			buffer.append(uid);
		}

		if (ownerUid != null) {
			buffer.append(ownerUid);
		}

		if (flag != null) {
			buffer.append(flag);
		}
		return buffer.toString();
	}

}
