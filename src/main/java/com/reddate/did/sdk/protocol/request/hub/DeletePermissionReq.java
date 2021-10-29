package com.reddate.did.sdk.protocol.request.hub;

import java.io.Serializable;

/**
 * delete permission request data structure
 * 
 *
 */
public class DeletePermissionReq implements HubBaseReq,Serializable{

	/**
	 * user id
	 * 
	 */
	private String uid;
	
	/**
	 * resource url
	 */
	private String url;
	
	/**
	 * grant user id
	 */
	private String grantUid; 
	
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGrantUid() {
		return grantUid;
	}

	public void setGrantUid(String grantUid) {
		this.grantUid = grantUid;
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

	@Override
	public String contractToString() {
		StringBuffer buffer = new StringBuffer();
		
		if(uid != null) {
			buffer.append(uid);
		}
		if(url != null) {
			buffer.append(url);
		}
		if(grantUid != null) {
			buffer.append(grantUid);
		}
		if(grant != null) {
			buffer.append(grant);
		}
		return buffer.toString();
	}
	
	
	
}
