package com.lb.common;

/**
 * @author nt
 *	前端传给后端所需的字段
 */
public class PageQurey {
	
	/**
	 * 一页显示多少条
	 */
	private Integer pageSize;
	
	/**
	 * 当前页
	 */
	private Integer currPage;

	/**
	 *  搜索名称
	 */
	private String onSearch;
	

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrPage() {
		return currPage;
	}

	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}

	public String getOnSearch() {
		return onSearch;
	}

	public void setOnSearch(String onSearch) {
		this.onSearch = onSearch;
	}
	
	
}
