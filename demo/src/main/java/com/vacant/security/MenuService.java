package com.vacant.security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service
public class MenuService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	public void save(VacantMenu menu) {
		String id = menu.getId();
		String parentId = menu.getParentId();
		int xssx = menu.getXssx();
		String name = menu.getName();
		String path = menu.getPath();
		String rel = menu.getRel();
		String gybz = menu.getGybz();

		if (StringUtils.isEmpty(id)) {
			id = UUID.randomUUID().toString();
			String sql = "insert into vacant_menu values(?,?,?,?,?,?,?)";
			jdbcTemplate.update(sql, id, parentId, xssx, name, path, rel, gybz);
			menu.setId(id);
		} else {
			String sql = "update vacant_menu set parent_id=?,xssx=?,name=?,path=?,";
			sql += "rel=?,gybz=? where id=?";
			jdbcTemplate.update(sql, id, parentId, xssx, name, path, rel, gybz);
		}
	}
}
