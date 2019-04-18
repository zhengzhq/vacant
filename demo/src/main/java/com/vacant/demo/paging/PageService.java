package com.vacant.demo.paging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
public class PageService {
	
	private static final int NUM_PER_PAGE = 10;
	
	private static final String OP_EQ = "_EQ";
	private static final String OP_LK = "_LK";
	private static final String OP_GE = "_LK";
	private static final String OP_GT = "_LK";
	private static final String OP_LE = "_LK";
	private static final String OP_LT = "_LK";
	private static final String OP_IN = "_IN";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 获取统计信息
	 * @param sql
	 * @param params
	 * @return
	 */
	public Stats stats(String sql, SearchForm searchForm) {
		ConditionAndParams cp = parseForm(searchForm);
		sql += cp.getCondition();
		return null;
	}

	/**
	 * “翻到”指定的页码
	 * @param sql
	 * @param searchForm
	 * @return
	 */
	public Page turnTo(String sql, SearchForm searchForm) {
		return null;
	}
	
	/**
	 * 解析searchForm
	 * @param searchForm
	 * @return
	 */
	public ConditionAndParams parseForm(SearchForm searchForm) {
		ConditionAndParams cp = new ConditionAndParams();
		String condition = " where 1=1";
		Map<String, String> conditions = searchForm.getConditions();
		List<String> list = new ArrayList<String>();
		for (Entry<String, String> e : conditions.entrySet()) {
			String key = e.getKey();
			if(StringUtils.isEmptyOrWhitespace(key)
					) {
				continue;
			}
			String value = e.getValue();
			condition += " and";
			condition += key;
			if(key.endsWith(OP_LK)) {
				condition += " like ?";
				list.add(value + "%");
			} else if(key.endsWith(OP_EQ)) {
				condition += " = ?";
				list.add(value);
			} else if(key.endsWith(OP_GT)) {
				condition += " > ?";
				list.add(value);
			} else if(key.endsWith(OP_GE)) {
				condition += " >= ?";
				list.add(value);
			} else if(key.endsWith(OP_LT)) {
				condition += " < ?";
				list.add(value);
			} else if(key.endsWith(OP_LE)) {
				condition += " <= ?";
				list.add(e.getValue());
			} else if(key.endsWith(OP_IN)) {
				condition += " in(";
				String[] vs = value.split(",");
				for (int i=0;i<vs.length;i++) {
					if(i>0) {
						condition += ",";
					}
					condition += "?";
					list.add(vs[i]);
				}
				condition += ")";
			}
		}
		cp.setCondition(condition);
		return cp;
	}
}
