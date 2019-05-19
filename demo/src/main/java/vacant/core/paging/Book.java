package com.vacant.demo.paging;

import java.util.List;
import java.util.Map;

public class Book {

	private List<Map<String, Object>> page; // 行集
	private Map<String, Object> stats; // 统计数据
	public List<Map<String, Object>> getPage() {
		return page;
	}
	public void setPage(List<Map<String, Object>> page) {
		this.page = page;
	}
	public Map<String, Object> getStats() {
		return stats;
	}
	public void setStats(Map<String, Object> stats) {
		this.stats = stats;
	}
}
