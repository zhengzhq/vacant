package com.vacant.demo.paging;

/**
 * 条件和值
 * @author zzq
 *
 */
public class ConditionAndParams {

	// 查询条件字符串
	private String condition;
	
	// 查询参数
	private Object[] params;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
}
