package com.vacant.dept;

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

import com.vacant.Utils;

@Service
@Transactional
public class DeptService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void save(VacantDept dept) {
		String id = dept.getId();
		String areaCode = dept.getAreaCode();
		String name = dept.getName();

		String sql = null;
		if (StringUtils.isEmpty(id)) {
			id = UUID.randomUUID().toString();
			sql = "insert into vacant_dept (area_code, name, create_time, id) values(?,?,?,?)";
			dept.setId(id);
			String createTime = Utils.dateTime();
			jdbcTemplate.update(sql, areaCode, name, createTime, id);
		} else {
			sql = "update vacant_dept set area_code=?,name=? where id=?";
			jdbcTemplate.update(sql, areaCode, name, id);
		}
	}

	public VacantDept findByPk(String id) {
		List<VacantDept> list = jdbcTemplate.query("select * from vacant_dept where id=?", new String[] { id },
				mapper());
		return list.get(0);
	}

	private RowMapper<VacantDept> mapper() {
		return new RowMapper<VacantDept>() {
			@Override
			public VacantDept mapRow(ResultSet rs, int rowNum) throws SQLException {
				VacantDept dept = new VacantDept();
				dept.setId(rs.getString("id"));
				dept.setAreaCode(rs.getString("area_code"));
				dept.setName(rs.getString("name"));
				dept.setCreateTime(rs.getString("create_time"));
				return dept;
			}
		};
	}

	public void delete(String id) {
		if(hasUser(id)) {
			throw new RuntimeException("该单位下已创建用户，不能删除！");
		}
		jdbcTemplate.update("delete from vacant_dept where id=?", id);
	}

	public boolean hasUser(String id) {
		String sql = "select 1 from vacant_user where dept_id=? limit 1";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, id);
		return list.size() > 0;
	}

}
