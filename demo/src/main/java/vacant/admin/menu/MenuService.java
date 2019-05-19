package vacant.admin.menu;

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
@Transactional
public class MenuService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void save(VacantMenu menu) {
		String id = menu.getId();
		String parentId = menu.getParentId();
		int xssx = menu.getXssx();
		String name = menu.getName();
		String path = menu.getPath();
		String rel = menu.getRel();

		if (StringUtils.isEmpty(id)) {
			id = UUID.randomUUID().toString();
			String sql = "insert into vacant_menu values(?,?,?,?,?,?)";
			jdbcTemplate.update(sql, id, parentId, xssx, name, path, rel);
			menu.setId(id);
		} else {
			String sql = "update vacant_menu set parent_id=?,xssx=?,name=?,path=?,";
			sql += "rel=? where id=?";
			jdbcTemplate.update(sql, parentId, xssx, name, path, rel, id);
		}
	}

	public VacantMenu findByPk(String id) {
		List<VacantMenu> list = jdbcTemplate.query("select * from vacant_menu where id=?", new String[] { id },
				new RowMapper<VacantMenu>() {
			@Override
			public VacantMenu mapRow(ResultSet rs, int rowNum) throws SQLException {
				VacantMenu menu = menu(rs);
				// menu.setChildren(menuList(rs.getString("id")));
				return menu;
			}
		});
		if (list.size() == 1) {
			return list.get(0);
		}
		throw new RuntimeException("vacant_menu表主键错误：" + id);
	}

	public void delete(String id) {
		if (hasChild(id)) {
			throw new RuntimeException("存在子节点，不能删除！");
		}
		jdbcTemplate.update("delete from vacant_menu where id=?", id);
		jdbcTemplate.update("delete from vacant_role_menu where menu_id=?", id);
	}

	public boolean hasChild(String id) {
		String sql = "select 1 from vacant_menu where parent_id=? limit 1";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, id);
		return list.size() > 0;
	}

	public List<VacantMenu> children(String parentId, boolean recursive) {
		return jdbcTemplate.query("select * from vacant_menu where parent_id=? order by xssx",
				new String[] { parentId }, new RowMapper<VacantMenu>() {
	
			@Override
			public VacantMenu mapRow(ResultSet rs, int rowNum) throws SQLException {
				VacantMenu menu = menu(rs);
				if(recursive) {
					List<VacantMenu> children = children(menu.getId(), recursive);
					if(children.isEmpty()) {
						children = null;
					}
					menu.setChildren(children);
				}
				return menu;
			}
		});
	}

	public List<VacantMenu> childrenForRole(String roleId, String parentId) {
		String sql = "select a.*, (case when b.id is null then 'false' else 'true' end) checked ";
		sql += "from vacant_menu a left join vacant_role_menu b on a.id=b.menu_id and b.role_id=? ";
		sql += "where a.parent_id=? order by a.xssx ";
		return jdbcTemplate.query(sql, new Object[] { roleId, parentId }, new RowMapper<VacantMenu>() {
			@Override
			public VacantMenu mapRow(ResultSet rs, int rowNum) throws SQLException {
				VacantMenu menu = menu(rs);
				menu.setChecked(rs.getString("checked"));
				List<VacantMenu> children = childrenForRole(roleId, menu.getId());
				if(children.isEmpty()) {
					children = null;
				}
				menu.setChildren(children);
				return menu;
			}
		});
	}

	public List<VacantMenu> childrenForUser(String parentId, String userId, boolean recursive) {
		String sql = "select * from vacant_menu m, vacant_role_menu rm, vacant_user u ";
		sql += "where m.id=rm.menu_id and rm.role_id=u.role_id and u.id=? and m.parent_id=? ";
		sql += " order by xssx";
		return jdbcTemplate.query(sql, new String[] { userId, parentId }, new RowMapper<VacantMenu>() {
			@Override
			public VacantMenu mapRow(ResultSet rs, int rowNum) throws SQLException {
				VacantMenu menu = menu(rs);
				if(recursive) {
					List<VacantMenu> children = childrenForUser(menu.getId(), userId, recursive);
					if(children.isEmpty()) {
						children = null;
					}
					menu.setChildren(children);
				}
				return menu;
			}
		});
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
