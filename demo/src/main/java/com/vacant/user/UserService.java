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
import com.vacant.dept.Dept;

@Service
@Transactional
public class UserService implements UserDetailsService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	public void add(User user) {
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
		String roleId = user.getRoleId();

		id = UUID.randomUUID().toString();
		String sql = "insert into vacant_user ";
		sql += "(area_code, dept_id, name, username, password, state, create_time, role_id, id) ";
		sql += "values(?,?,?,?,?,?,?,?,?)";
		String createTime = Utils.dateTime();
		jdbcTemplate.update(sql, areaCode, deptId, name, username, password, state, createTime, roleId, id);

		user.setId(id);

	}

	public void edit(User user) {
		String id = user.getId();
		String state = user.getState();
		String roleId = user.getRoleId();

		String sql = "update vacant_user ";
		sql += "set state=?, role_id=? where id=?";
		jdbcTemplate.update(sql, state, roleId, id);

	}

	public User findByPk(String id) {
		String sql = "select user.*, ";
		sql += "(select name from vacant_dept where id=user.dept_id limit 1) dept_name ";
		sql += "from vacant_user user where id=?";
		List<User> list = jdbcTemplate.query(sql, new String[] { id }, mapper());
		return list.get(0);
	}

	private RowMapper<User> mapper() {
		return new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				Dept dept = new Dept();
				dept.setName(rs.getString("dept_name"));

				User user = new User();
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
	public boolean hasCzjl(String id) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select 1 from vacant_czjl where czr_id=? limit 1",
				id);
		return list.size() > 0;
	}

	public void delete(String id) {
		if (hasCzjl(id)) {
			throw new RuntimeException("存在与用户相关的操作记录，不能删除");
		}
		jdbcTemplate.update("delete from vacant_user where id=?", id);

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String sql = "select user.*, ";
		sql += "(select name from vacant_dept where id=user.dept_id limit 1) dept_name ";
		sql += "from vacant_user user where username=?";
		List<User> list = jdbcTemplate.query(sql, new String[] { username }, mapper());
		return list.get(0);
	}

	// 修改密码
	public void changePwd(String oldPassword, String newPassword) {
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		User user = findByPk(Utils.userId());
		if(!encode.matches(oldPassword,user.getPassword())) {
			throw new RuntimeException("旧密码错误");
		}
		
		String password = new BCryptPasswordEncoder().encode(newPassword);
		jdbcTemplate.update("update vacant_user set password=? where id=?", password, user.getId());
	}

}
