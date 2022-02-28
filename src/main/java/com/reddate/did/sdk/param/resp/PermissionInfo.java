package com.reddate.did.sdk.param.resp;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.reddate.did.sdk.param.req.UsedFlag;

/** 
 * Identify hub permission information description data structure
 */
public class PermissionInfo implements Serializable{

	/**
	 * resource url
	 */
	  private String url;

	  /**
	   * grant information
	   */
	  private String grant;

	  /**
	   * grant user id
	   */
	  private String grantUid;

	  /**
	   * permission status:
	   * 0 - already logic delete permission record 
	   * 1 - login active permission record
	   */
	  private Integer status;

	  /**
	   * permission record create time
	   */
	  private LocalDateTime createTime;

	  /**
	   * permission used time
	   */
	  private LocalDateTime readTime;

	  /**
	   * permission used flag:
	   * YES - permission has been used
	   * NO - permission do not used
	   */
	  private UsedFlag flag;

	  /**
	   * resource owner user id
	   */
	  private String uid;

	  /**
	   * the resource encryption key of grant user
	   */
	  private String key;

	  /**
	   * the resource encryption key for resource owner user
	   */
	  private String ownerKey;

	  public String getKey() {
	    return key;
	  }

	  public void setKey(String key) {
	    this.key = key;
	  }
	  
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

	  public UsedFlag getFlag() {
		return flag;
	  }

	  public void setFlag(UsedFlag flag) {
		this.flag = flag;
	  }

	  public LocalDateTime getCreateTime() {
	    return createTime;
	  }

	  public void setCreateTime(LocalDateTime createTime) {
	    this.createTime = createTime;
	  }

	  public LocalDateTime getReadTime() {
	    return readTime;
	  }

	  public void setReadTime(LocalDateTime readTime) {
	    this.readTime = readTime;
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

	  public Integer getStatus() {
	    return status;
	  }

	  public void setStatus(Integer status) {
	    this.status = status;
	  }

	  public String getOwnerKey() {
	    return ownerKey;
	  }

	  public void setOwnerKey(String ownerKey) {
	    this.ownerKey = ownerKey;
	  }
	
	
}
