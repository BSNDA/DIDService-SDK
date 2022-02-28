package com.reddate.did.sdk.protocol.request.hub;

import java.io.Serializable;

import com.reddate.did.sdk.param.req.Operation;

/**
 * query resource history request data structure
 * 
 *
 */
public class QueryResourceHistoryReq implements HubBaseReq,Serializable{

	/**
	 * user id
	 * 
	 */
	private String uid;

	/**
	 * resource url
	 * 
	 */
	private String url;
	
	/**
	 * used flag
	 * 
	 */
	private String operation;

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

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
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

		if(operation != null) {
			buffer.append(operation);
		}
		return buffer.toString();
	}
	
	
}
