package com.vacant.role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service
public class RoleService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<VacantRole> all() {
		return jdbcTemplate.query("select * from vacant_role", new Object[] {}, new RoleMapper());
	}

	public VacantRole findByPk(String id) {
		List<VacantRole> list = jdbcTemplate.query("select * from vacant_role where id=?", new String[] { id },
				new RoleMapper());
		return list.get(0);
	}

	@Transactional
	public void save(VacantRole role) {
		String id = role.getId();
		String name = role.getName();

		String sql = null;
		if (StringUtils.isEmpty(id)) {
			id = UUID.randomUUID().toString();
			sql = "insert into vacant_role (name, id) values(?,?)";
			role.setId(id);
		} else {
			sql = "update vacant_menu set name=? where id=?";
		}
		jdbcTemplate.update(sql, name, id);
	}

	private class RoleMapper implements RowMapper<VacantRole> {
		@Override
		public VacantRole mapRow(ResultSet rs, int rowNum) throws SQLException {
			VacantRole role = new VacantRole();
			role.setId(rs.getString("id"));
			role.setName(rs.getString("name"));
			return role;
		}
	}

	public boolean hasUser(String id) {
		String sql = "select 1 from vacant_user_role where role_id=? limit 1";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, id);
		return list.size() > 0;
	}

	@Transactional
	public void delete(String id) {
		jdbcTemplate.update("delete from vacant_role where id=?", id);
	}

}
