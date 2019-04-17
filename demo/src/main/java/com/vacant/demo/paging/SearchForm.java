package com.vacant.demo.paging;

import java.util.Map;

public class SearchForm {

	private int pageNum; // 页码
	private int numPerPage;
	private Map<String, String> conditions; // 条件
	
	public Map<String, String> getConditions() {
		return conditions;
	}
	public void setConditions(Map<String, String> conditions) {
		this.conditions = conditions;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
}
