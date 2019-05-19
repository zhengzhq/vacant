package com.vacant.demo.paging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.vacant.Utils;

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
	
	private Logger logger = LoggerFactory.getLogger(PageService.class);

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
		WhereAndParams cp = parseForm(searchForm);
		sql += cp.getWhere();
		return null;
	}
	
	public Book turnTo(String sql, SearchForm searchForm, String sql2, String order) {
		int pageNum = searchForm.getPageNum();
		int numPerPage = searchForm.getNumPerPage();
		WhereAndParams cp = parseForm(searchForm);
		sql += cp.getWhere();
		sql += " " + order;
		sql += " limit ";
		sql += (pageNum-1) * numPerPage;
		sql += ",";
		sql += numPerPage;
		Object[] params = cp.getParams();
		Utils.log(logger, sql2, params);
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);
		Book book = new Book();
		book.setPage(rows);
		sql2 += cp.getWhere();
		Map<String, Object> stats = jdbcTemplate.queryForMap(sql2, cp.getParams());
		book.setStats(stats);
		return book;
	}

	/**
	 * “翻到”指定的页码
	 * 
	 * @param sql
	 * @param searchForm
	 * @return
	 */
	public Book turnTo(String sql, SearchForm searchForm, String sql2) {
		return turnTo(sql, searchForm, sql2, "");
	}

	/**
	 * 解析searchForm
	 * 
	 * @param searchForm
	 * @return
	 */
	public WhereAndParams parseForm(SearchForm searchForm) {
		WhereAndParams cp = new WhereAndParams();
		String where = " where 1=1";
		Map<String, String> conditions = searchForm.getConditions();
		List<String> list = new ArrayList<String>();
		for (Entry<String, String> e : conditions.entrySet()) {
			String value = e.getValue();
			if (StringUtils.isEmptyOrWhitespace(value)) {
				continue;
			}
			String and = " and ";
			String key = e.getKey().toUpperCase();
			if (key.endsWith(OP_LK)) {
				and += key.replace(OP_LK, "");
				and += " like ?";
				list.add(value + "%");
			} else if (key.endsWith(OP_BLK)) {
				and += key.replace(OP_BLK, "");
				and += " like ?";
				list.add("%" + value + "%");
			}  else if (key.endsWith(OP_EQ)) {
				and += key.replace(OP_EQ, "");
				and += " = ?";
				list.add(value);
			} else if (key.endsWith(OP_GT)) {
				and += key.replace(OP_GT, "");
				and += " > ?";
				list.add(value);
			} else if (key.endsWith(OP_GE)) {
				and += key.replace(OP_GE, "");
				and += " >= ?";
				list.add(value);
			} else if (key.endsWith(OP_LT)) {
				and += key.replace(OP_LT, "");
				and += " < ?";
				list.add(value);
			} else if (key.endsWith(OP_LE)) {
				and += key.replace(OP_LK, "");
				and += " <= ?";
				list.add(e.getValue());
			} else if (key.endsWith(OP_IN)) {
				and += key.replace(OP_IN, "");
				and += " in(";
				String[] vs = value.split(",");
				for (int i = 0; i < vs.length; i++) {
					if (i > 0) {
						and += ",";
					}
					and += "?";
					list.add(vs[i]);
				}
				and += ")";
			} else {
				continue;
			}
			where += and;
		}
		cp.setWhere(where);
		cp.setParams(list.toArray());
		return cp;
	}
}
