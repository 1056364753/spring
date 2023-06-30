package com.lb.entity;

/**
 * @author nt
 *	说明：后端传给前端返回的字段
 */
public class Page {
	
	
	/**
	 *  根据搜索结果返回的总数
	 */
	private Integer total;
	
	/**
	 * 	根据搜索结果返回的数据集合
	 */
	private Object data;

	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
	
	
}
