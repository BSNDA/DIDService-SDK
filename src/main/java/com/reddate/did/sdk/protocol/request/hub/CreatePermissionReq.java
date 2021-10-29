package com.reddate.did.sdk.protocol.request.hub;

import java.io.Serializable;

/**
 * 
 * Create permission request data structure
 * 
 * 
 *
 */
public class CreatePermissionReq implements HubBaseReq,Serializable{

	/**
	 * user id
	 * 
	 */
	private String uid;
	
	/**
	 * resource URL
	 * 
	 */
	private String url;

	/**
	 * grant information
	 * 
	 */
	private String grant;

	/**
	 * grant user id
	 * 
	 */
	private String grantUid;
	
	/**
	 * grant user's public key
	 * 
	 */
	private String grantPublicKey;
	
	/**
	 * grant encrypt Key
	 * 
	 */
	private String grantEncryptKey;
	
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

	public String getGrantUid() {
		return grantUid;
	}

	public void setGrantUid(String grantUid) {
		this.grantUid = grantUid;
	}

	public String getGrantPublicKey() {
		return grantPublicKey;
	}

	public void setGrantPublicKey(String grantPublicKey) {
		this.grantPublicKey = grantPublicKey;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getGrantEncryptKey() {
		return grantEncryptKey;
	}

	public void setGrantEncryptKey(String grantEncryptKey) {
		this.grantEncryptKey = grantEncryptKey;
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
		
		if(grant != null) {
			buffer.append(grant);
		}
		
		if(grantUid != null) {
			buffer.append(grantUid);
		}
		
		if(grantPublicKey != null) {
			buffer.append(grantPublicKey);
		}

		if(grantEncryptKey != null) {
			buffer.append(grantEncryptKey);
		}
		
		return buffer.toString();
	}
	
	
	
	
}
