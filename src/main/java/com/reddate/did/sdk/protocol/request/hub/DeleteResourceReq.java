package com.reddate.did.sdk.protocol.request.hub;

import java.io.Serializable;

/**
 * 
 * delete resource request data structure
 * 
 *
 */
public class DeleteResourceReq implements HubBaseReq,Serializable{

	/**
	 * use id
	 * 
	 */
	private String uid;
	
	/**
	 * resource url
	 * 
	 */
	private String url;
	
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
		
		return buffer.toString();
	}
	
	
	
	
	
	
}
