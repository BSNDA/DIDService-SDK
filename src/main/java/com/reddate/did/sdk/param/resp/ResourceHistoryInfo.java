package com.reddate.did.sdk.param.resp;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.reddate.did.sdk.param.req.UsedFlag;

/**
 * Query resource operation hisotry information description data structure
 */
public class ResourceHistoryInfo implements Serializable {

	/**
	 * the operation user id
	 */
	private String operationUid;

	/**
	 * the owner user id
	 */
	private String ownerUid;

	/**
	 * the operations
	 */
	private String operation;

	/**
	 * the resource content
	 */
	private String content;

	/**
	 * the resource url
	 */
	private String url;

	/**
	 * the resource key
	 */
	private String key;

	/**
	 * the resource operation time
	 */
	private LocalDateTime operationTime;

	public String getOperationUid() {
		return operationUid;
	}

	public void setOperationUid(String operationUid) {
		this.operationUid = operationUid;
	}

	public String getOwnerUid() {
		return ownerUid;
	}

	public void setOwnerUid(String ownerUid) {
		this.ownerUid = ownerUid;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public LocalDateTime getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(LocalDateTime operationTime) {
		this.operationTime = operationTime;
	}

}
