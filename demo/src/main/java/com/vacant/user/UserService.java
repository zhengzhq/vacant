package com.vacant.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vacant.Utils;
import com.vacant.dept.VacantDept;
import com.vacant.security.RolePath;

@Service
@Transactional
public class UserService implements UserDetailsService{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	public void add(VacantUser user) {
		String username = user.getUsername();
		if (isUsernameExists(username)) {
			throw new RuntimeException("与其他用户名冲突：" + username);
		}
		
		String id = user.getId();
		String areaCode = user.getAreaCode();
		String deptId = user.getDeptId();
		String name = user.getName();
		String password = new BCryptPasswordEncoder().encode(user.getPassword());
		String state = user.getState();

		id = UUID.randomUUID().toString();
		String sql = "insert into vacant_user ";
		sql += "(area_code, dept_id, name, username, password, state, create_time, id) ";
		sql += "values(?,?,?,?,?,?,?,?)";
		String createTime = Utils.dateTime();
		jdbcTemplate.update(sql, areaCode, deptId, name, username, password, state, createTime, id);

		user.setId(id);

	}

	public void edit(VacantUser user) {
		String id = user.getId();
		String username = user.getUsername();
		String sql = "select 1 from vacant_user where username=? and id!=? limit 1";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, username, id);
		if (list.size()>0) {
			throw new RuntimeException("用户名已存在：" + username);
		}
		
		String areaCode = user.getAreaCode();
		String deptId = user.getDeptId();
		String name = user.getName();
		String state = user.getState();

		sql = "update vacant_user ";
		sql += "set area_code=?, dept_id=?, name=?, username=?, state=? where id=?";
		jdbcTemplate.update(sql, areaCode, deptId, name, username, state, id);

	}

	public VacantUser findByPk(String id) {
		String sql = "select user.*, ";
		sql += "(select name from vacant_dept where id=user.dept_id limit 1) dept_name ";
		sql += "from vacant_user user where id=?";
		List<VacantUser> list = jdbcTemplate.query(sql, new String[] { id }, mapper());
		return list.get(0);
	}

	private RowMapper<VacantUser> mapper() {
		return new RowMapper<VacantUser>() {
			@Override
			public VacantUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				VacantDept dept = new VacantDept();
				dept.setName(rs.getString("dept_name"));

				VacantUser user = new VacantUser();
				user.setId(rs.getString("id"));
				user.setAreaCode(rs.getString("area_code"));
				user.setDeptId(rs.getString("dept_id"));
				user.setDept(dept);
				user.setName(rs.getString("name"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setState(rs.getString("state"));
				user.setCreateTime(rs.getString("create_time"));
				user.setRoleId(rs.getString("role_id"));
				return user;
			}
		};
	}
	
	public boolean isUsernameExists(String username) {
		String sql = "select 1 from vacant_user where username=? limit 1";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, username);
		return list.size() > 0;
	}

	// 判断用户是否已登录过系统
	public boolean hasLoggedIn(String id) {
		// TODO Auto-generated method stub
		return true;
	}

	public void delete(String id) {
		if(hasLoggedIn(id)) {
			throw new RuntimeException("用户已登录过系统，不能删除");
		}
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String sql = "select user.*, ";
		sql += "(select name from vacant_dept where id=user.dept_id limit 1) dept_name ";
		sql += "from vacant_user user where username=?";
		List<VacantUser> list = jdbcTemplate.query(sql, new String[] { username }, mapper());
		return list.get(0);
	}

}
