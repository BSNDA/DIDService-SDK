package com.reddate.did.sdk.protocol.request;

import java.io.Serializable;

/**
 * 
 * Request Param data Structure
 *
 *
 * @param <T>
 */
public class RequestParam<T> implements Serializable {
	
	/**
	 * 
	 * project id
	 * 
	 */
	private String projectId;
	
	/**
	 * 
	 * did identify
	 */
	private String did;
	
	/**
	 * business data
	 * 
	 */
	private T data;
	
	/**
	 * 
	 * sign value
	 */
	private String sign;
	
	public RequestParam() {
	}

	public RequestParam(String projectId,String did) {
		this.projectId = projectId;
		this.did = did;
	}
	
	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public T getData() {
		return data;
	}


	public void setData(T data) {
		this.data = data;
	}


	public String getSign() {
		return sign;
	}


	public void setSign(String sign) {
		this.sign = sign;
	}


	public String getDid() {
		return did;
	}


	public void setDid(String did) {
		this.did = did;
	}
	
	
	
	
	
}
