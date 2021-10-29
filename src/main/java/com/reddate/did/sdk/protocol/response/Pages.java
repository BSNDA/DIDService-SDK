package com.reddate.did.sdk.protocol.response;

import java.util.List;

/**
 * common page wrapper data structure
 * 
 *
 * @param <T>
 */
public class Pages<T> {

	/**
	 * current page nubmer
	 * 
	 */
    private Integer page;
    
    /**
     * 
     * page size
     */
    private Integer size;
    
    /**
     * 
     * total record number
     */
    private Integer totalNum;
    
    /**
     * 
     * total page nubmer
     */
    private Integer totalPage;
    
    /**
     * 
     * page data list
     * 
     */
    private List<T> result;

    public Integer getPage() { return page; }

    public void setPage(Integer page) { this.page = page; }

    public Integer getSize() { return size; }

    public void setSize(Integer size) { this.size = size; }

    public Integer getTotalNum() { return totalNum; }

    public void setTotalNum(Integer totalNum) { this.totalNum = totalNum; }

    public Integer getTotalPage() { return totalPage; }

    public void setTotalPage(Integer totalPage) { this.totalPage = totalPage; }

    public List<T> getResult() { return result; }

    public void setResult(List<T> result) { this.result = result; }

}
