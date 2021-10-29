package com.reddate.did.sdk.protocol.request.hub;

import java.io.Serializable;

/**
 * query permission request data structure
 * 
 *
 */
public class QueryPermissionReq implements HubBaseReq,Serializable{

	/**
	 * user id
	 * 
	 */
	private String uid;

	/**
	 * grant user id
	 * 
	 */
	private String grantUid;
	
	/**
	 * used flag
	 * 
	 */
	private String flag;

	/**
	 * sign value
	 * 
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

	@Override
	public String contractToString() {
		StringBuffer buffer = new StringBuffer();
		if(uid != null) {
			buffer.append(uid);
		}
		
		if(grantUid != null) {
			buffer.append(grantUid);
		}

		if(flag != null) {
			buffer.append(flag);
		}
		return buffer.toString();
	}
	
	
}
