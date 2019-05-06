package com.vacant;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vacant.user.VacantUser;

public abstract class BaseCtrl {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	protected abstract String v();

	protected String v(String page) {
		return v() + "_" + page;
	}

	protected void czjl(HttpServletRequest req, String name) {
		String path = req.getServletPath();
		String params = "";
		Map<String, String[]> paramMap = req.getParameterMap();
		for (Map.Entry<String, String[]> param : paramMap.entrySet()) {
			if(param.getKey().contentEquals("_csrf")) {
				continue;
			}
			if(params.length()>0) {
				params += ",";
			}
			params += param.getKey();
			params += ":[";
			String[] values = param.getValue();
			for (int i =0;i<values.length;i++) {
				String value = values[i];
				if(i>0) {
					params += ",";
				}
				params += value;				
			}
			params += "]";
		}
		VacantUser user = (VacantUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String czrName = user.getName();
		String czrId= user.getId();
		String czsj = Utils.dateTime();
		String ip = req.getRemoteAddr();

		String sql = "insert into vacant_czjl ";
		sql += "(id, name, path, params, czr_id, czr_name, czsj, ip) ";
		sql += "values (uuid(),?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, name, path, params, czrId, czrName, czsj, ip);
	}
}
