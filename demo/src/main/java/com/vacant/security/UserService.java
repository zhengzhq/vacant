package com.vacant.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<RolePath> allRolePath() {
		return jdbcTemplate.query("select path, group_concat(role_id) from vacant_role_path",
				new RowMapper<RolePath>() {
					@Override
					public RolePath mapRow(ResultSet rs, int rowNum) throws SQLException {
						RolePath rp = new RolePath();
						rp.setRoleId(rs.getString("role_id"));
						rp.setPath(rs.getString("path"));
						return rp;
					}
				});
	}

}
