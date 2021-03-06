package vacant.admin.area;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import vacant.Utils;
import vacant.admin.lookup.LookupService;

@Service
@Transactional
public class AreaService {

	private Logger logger = LoggerFactory.getLogger(AreaService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private LookupService lookupService;

	private Map<String, Area> areaMap = null;

	private RowMapper<Area> mapper(boolean recursive) {
		return new RowMapper<Area>() {
			@Override
			public Area mapRow(ResultSet rs, int rowNum) throws SQLException {
				Area area = new Area();
				area.setId(rs.getString("id"));
				area.setParentId(rs.getString("parent_id"));
				area.setCode(rs.getString("code"));
				area.setName(rs.getString("name"));
				area.setFullName(rs.getString("full_name"));
				area.setState(rs.getString("state"));
				area.setCreateTime(rs.getString("create_time"));
				String desc = String.format("%s(%s,%s)", area.getName(), area.getCode(),
						lookupService.text(LookupService.COMMON_STATE, area.getState()));
				area.setDesc(desc);
				if (recursive) {
					area.setChildren(children(area.getId(), recursive));
				}
				return area;
			}
		};
	}

	public Area root() {
		String sql = "select * from vacant_area where parent_id is null";
		logger.debug(sql);
		List<Area> list = jdbcTemplate.query(sql, new String[] {}, mapper(true));
		if (list.size() > 0) {
			return list.get(0);
		}
		return list.get(0);
	}

	/**
	 * 以指定id为跟节点的行政区划树
	 * 
	 * @param parentId
	 * @return
	 */
	public Area tree(String id) {
		String sql = "select * from vacant_area where id=?";
		String[] params = { id };
		Utils.log(logger, sql, params);
		List<Area> list = jdbcTemplate.query(sql, params, mapper(true));
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<Area> children(String parentId, boolean recursive) {
		String sql = "select * from vacant_area where parent_id=?";
		String[] params = { parentId };
		Utils.log(logger, sql, params);
		List<Area> list = jdbcTemplate.query(sql, params, mapper(recursive));
		if (list.size() > 0) {
			return list;
		}
		return null; // 为了使叶节点的图表与非叶节点不一样，不能返回空list
	}

	public void insert(Area area) {
		String parentId = area.getParentId();
		Area parent = findByPk(parentId);

		String id = area.getId();
		String code = parent.getCode() + area.getSubCode();
		String name = area.getName();
		String fullName = parent.getFullName() + name;
		String state = area.getState();
		String createTime = Utils.dateTime();

		id = Utils.uuid();
		String sql = "insert into vacant_area (parent_id, code, name, full_name, state, create_time, id) ";
		sql += "values(?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, parentId, code, name, fullName, state, createTime, id);

		area.setId(id);
	}

	public void update(Area area) {
		String parentId = area.getParentId();
		Area parent = findByPk(parentId);

		String id = area.getId();
		String code = parent.getCode() + area.getSubCode();
		String name = area.getName();
		String fullName = parent.getFullName() + name;
		String state = area.getState();
		// 如果存在有效的子节点，则不能将该节点设置为无效状态
		if (state.equals("无效")) {
			String sql = "select 1 from vacant_area where parent_id=? and state='有效' limit 1";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, id);
			if (list.size() > 0) {
				throw new RuntimeException("存在有效子节点，不能将当前节点设置为无效状态");
			}
		}

		String sql = "update vacant_area set code=?,name=?,full_name=?,state=? where id=?";
		jdbcTemplate.update(sql, code, name, fullName, state, id);
	}

	public Area findByPk(String id) {
		String sql = "select * from vacant_area a where a.id=?";
		List<Area> list = jdbcTemplate.query(sql, new String[] { id }, mapper(false));
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public boolean hasChild(String id) {
		String sql = "select 1 from vacant_area where parent_id=? limit 1";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, id);
		return list.size() > 0;
	}

	public void delete(String id) {
		if (hasChild(id)) {
			throw new RuntimeException("存在子节点，不能删除！");
		}
		jdbcTemplate.update("delete from vacant_area where id=?", id);
	}

	// 获取指定code对应的行政区划名
	public String name(String code) {
		if(StringUtils.isEmpty(code)) {
			return "";
		}
		return Utils.null2blank(getAreaMap().get(code).getName());
	}

	// 获取指定code对应的行政区划名
	public String fullName(String code) {
		if(StringUtils.isEmpty(code)) {
			return "";
		}
		return Utils.null2blank(getAreaMap().get(code).getFullName());
	}

	private Map<String, Area> getAreaMap() {

		if (areaMap == null) {
			areaMap = new HashMap<String, Area>();
			String sql = "select * from vacant_area";
			List<Area> list = jdbcTemplate.query(sql, new String[] {}, mapper(false));
			for (Area area : list) {
				areaMap.put(area.getCode(), area);
			}
		}
		return areaMap;
	}

}
