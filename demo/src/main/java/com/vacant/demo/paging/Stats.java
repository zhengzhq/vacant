package com.vacant.demo.paging;

import java.util.Map;

public class Stats {

	// 总数
	private int totalCount;
	
	// 其他统计项目
	private Map<String, Object> items;

	/**
	 * 总数
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 总数
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 其他统计项目
	 * @return the items
	 */
	public Map<String, Object> getItems() {
		return items;
	}

	/**
	 * 其他统计项目
	 * @param items the items to set
	 */
	public void setItems(Map<String, Object> items) {
		this.items = items;
	}
}
