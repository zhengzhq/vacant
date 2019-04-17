package com.vacant.demo.paging;

import java.util.List;
import java.util.Map;

public class Page {

	private List<Map<String, Object>> rows; // 行集
	private Stats stats; // 统计数据
	public List<Map<String, Object>> getRows() {
		return rows;
	}
	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}
	public Stats getStats() {
		return stats;
	}
	public void setStats(Stats stats) {
		this.stats = stats;
	}
}
