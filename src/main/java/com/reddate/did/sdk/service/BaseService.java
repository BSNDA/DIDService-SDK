package com.reddate.did.sdk.service;

import java.util.List;
import java.util.Map;

import com.reddate.did.sdk.protocol.response.Pages;

/**
 * 
 * the did service implement base class,
 *  some common function write in this class
 * 
 * 
 * @author danny
 *
 */
public class BaseService {

	private String url;
	
	private String token;
	
	private String projectId;
	
	public BaseService(String url, String projectId,String token) {
		super();
		this.url = url;
		this.token = token;
		this.projectId = projectId;
	}

	public String getToken() {
		return token;
	}

	public String getProjectId() {
		return projectId;
	}

	public String getUrl() {
		return url;
	}
	
	
	/**
	 * 
	 * Parse a map type page info to Pages type
	 * 
	 * @param <T> the business data class type
	 * @param pageMap  Page information Map
	 * @param elementType  the business data class type
	 * @return Return the Pages information
	 */
	public <T> Pages<T> parseToPage(Map pageMap,Class elementType){
		Pages<T> page = new Pages<>();
		page.setPage((Integer)pageMap.get("page"));
		page.setSize((Integer)pageMap.get("size"));
		page.setTotalNum((Integer)pageMap.get("totalNum"));
		page.setTotalPage((Integer)pageMap.get("totalPage"));
		page.setResult((List<T>)pageMap.get("result"));
		return page;
	}
	
}
