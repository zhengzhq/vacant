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

	private static final String OP_EQ = "_EQ";
	private static final String OP_LK = "_LK";
	private static final String OP_BLK = "_BLK";
	private static final String OP_GE = "_GE";
	private static final String OP_GT = "_GT";
	private static final String OP_LE = "_LE";
	private static final String OP_LT = "_LT";
	private static final String OP_IN = "_IN";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 获取统计信息
	 * 
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
	 * 
	 * @param sql
	 * @param searchForm
	 * @return
	 */
	public Book turnTo(String sql, SearchForm searchForm, String sql2) {
		int pageNum = searchForm.getPageNum();
		int numPerPage = searchForm.getNumPerPage();
		ConditionAndParams cp = parseForm(searchForm);
		sql += cp.getCondition();
		sql += " limit ";
		sql += (pageNum-1) * numPerPage;
		sql += ",";
		sql += numPerPage;
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, cp.getParams());
		Book book = new Book();
		book.setPage(rows);
		sql2 += cp.getCondition();
		Map<String, Object> stats = jdbcTemplate.queryForMap(sql2, cp.getParams());
		book.setStats(stats);
		return book;
	}

	/**
	 * 解析searchForm
	 * 
	 * @param searchForm
	 * @return
	 */
	public ConditionAndParams parseForm(SearchForm searchForm) {
		ConditionAndParams cp = new ConditionAndParams();
		String condition = " where 1=1";
		Map<String, String> conditions = searchForm.getConditions();
		List<String> list = new ArrayList<String>();
		for (Entry<String, String> e : conditions.entrySet()) {
			String value = e.getValue();
			if (StringUtils.isEmptyOrWhitespace(value)) {
				continue;
			}
			condition += " and ";
			String key = e.getKey().toUpperCase();
			if (key.endsWith(OP_LK)) {
				condition += key.replace(OP_LK, "");
				condition += " like ?";
				list.add(value + "%");
			} else if (key.endsWith(OP_BLK)) {
				condition += key.replace(OP_BLK, "");
				condition += " like ?";
				list.add("%" + value + "%");
			}  else if (key.endsWith(OP_EQ)) {
				condition += key.replace(OP_EQ, "");
				condition += " = ?";
				list.add(value);
			} else if (key.endsWith(OP_GT)) {
				condition += key.replace(OP_GT, "");
				condition += " > ?";
				list.add(value);
			} else if (key.endsWith(OP_GE)) {
				condition += key.replace(OP_GE, "");
				condition += " >= ?";
				list.add(value);
			} else if (key.endsWith(OP_LT)) {
				condition += key.replace(OP_LT, "");
				condition += " < ?";
				list.add(value);
			} else if (key.endsWith(OP_LE)) {
				condition += key.replace(OP_LK, "");
				condition += " <= ?";
				list.add(e.getValue());
			} else if (key.endsWith(OP_IN)) {
				condition += key.replace(OP_IN, "");
				condition += " in(";
				String[] vs = value.split(",");
				for (int i = 0; i < vs.length; i++) {
					if (i > 0) {
						condition += ",";
					}
					condition += "?";
					list.add(vs[i]);
				}
				condition += ")";
			}
		}
		cp.setCondition(condition);
		cp.setParams(list.toArray());
		return cp;
	}
}
