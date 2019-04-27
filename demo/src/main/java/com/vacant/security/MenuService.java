package com.vacant.security;

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
			jdbcTemplate.update(sql, parentId, xssx, name, path, rel, gybz, id);
		}
	}

	public List<VacantMenu> zxlList() {
		return menuList("root");
	}

	private List<VacantMenu> menuList(String parentId) {
		return jdbcTemplate.query("select * from vacant_menu where parent_id=? order by xssx",
				new String[] { parentId }, new MenuRowMapper());
	}

	public boolean hasChild(String id) {
		String sql = "select 1 from vacant_menu where parent_id=? limit 1";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, id);
		return list.size()>0;
	}

	@Transactional
	public void delete(String id) {
		jdbcTemplate.update("delete from vacant_menu where id=?", id);
		jdbcTemplate.update("delete from vacant_role_menu where menu_id=?", id);
	}

	private class MenuRowMapper implements RowMapper<VacantMenu> {

		@Override
		public VacantMenu mapRow(ResultSet rs, int rowNum) throws SQLException {
			VacantMenu menu = menu(rs);
			menu.setChildren(menuList(rs.getString("id")));
			return menu;
		}
	}

	public VacantMenu findByPk(String id) {
		List<VacantMenu> list = jdbcTemplate.query("select * from vacant_menu where id=?",
				new String[] { id }, new MenuRowMapper());
		if (list.size()==1) {
			return list.get(0);
		}
		throw new RuntimeException("vacant_menu表主键错误：" + id);
	}
	
	public List<VacantMenu> menuListForRole(String roleId, String parentMenuId) {
		String sql = "select a.*, (case when b.id is null then 'false' else 'true' end) checked ";
		sql += "from vacant_menu a left join vacant_role_menu b on a.id=b.menu_id and b.role_id=? ";
		sql += "where a.parent_id=? order by a.xssx ";
		return jdbcTemplate.query(sql, new Object[] {roleId, parentMenuId}, new TreeRowMapper(roleId));
	}
	private class TreeRowMapper implements RowMapper<VacantMenu> {
		private String roleId;
		
		public TreeRowMapper(String roleId) {
			this.roleId = roleId;
		}

		@Override
		public VacantMenu mapRow(ResultSet rs, int rowNum) throws SQLException {
			VacantMenu menu = menu(rs);
			menu.setChecked(rs.getString("checked"));
			menu.setChildren(menuListForRole(roleId, menu.getId()));
			return menu;
		}
	}
	
	private VacantMenu menu(ResultSet rs) throws SQLException {
		VacantMenu menu = new VacantMenu();
		String id = rs.getString("id");
		menu.setId(id);
		menu.setParentId(rs.getString("parent_id"));
		menu.setXssx(rs.getInt("xssx"));
		menu.setName(rs.getString("name"));
		menu.setPath(rs.getString("path"));
		menu.setRel(rs.getString("rel"));
		menu.setChecked("false");
		return menu;
	}
}
