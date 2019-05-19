package vacant.admin.lookup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 为了统一数据字典，所有常量均应在此处定义，其名称、值与相应的字典一致
 * 
 * @author zzq
 *
 */
@Service
@Transactional
@DependsOn("jdbcTemplate")
public class LookupService {

	public static final String COMMON_STATE = "common_state"; // 状态
	public static final String COMMON_STATE_YX = "1"; // 有效
	public static final String COMMON_STATE_WX = "0"; // 无效
	
	public static final String DEMO_UPLOAD_TYPE_1 = "1"; // 1个图片
	public static final String DEMO_UPLOAD_TYPE_N = "n"; // 多个图片
	public static final String DEMO_UPLOAD_TYPE_F = "f"; // 文件

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<String, String> lookupMap = null;

	// 获取指定type的code对应的text
	public String text(String type, String code) {
//		String sql = "select text from vacant_lookup where state='%s' and type=? and code=? limit 1";
//		sql = String.format(sql, COMMON_STATE_YX);
//		String text = jdbcTemplate.queryForObject(sql, String.class, type, code);

		if (lookupMap == null) {
			lookupMap = new HashMap<String, String>();
			String sql = "select concat(type,',',code) code, text from vacant_lookup";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : list) {
				lookupMap.put((String) map.get("code"), (String) map.get("text"));
			}
		}
		String text = lookupMap.get(type + "," + code);
		if (text == null) {
			text = "";
		}
		return text;
	}

	public List<Lookup> queryList(String type) {
		return rootList(type, "全部");
	}

	public List<Lookup> editList(String type) {
		return rootList(type, "请选择");
	}

	/**
	 * 以树的形式返回指定类型的字典，并使用指定的文本作为空值对象的项目，用于查询和表单编辑
	 * 
	 * @param type
	 * @param text
	 * @return
	 */
	public List<Lookup> rootList(String type, String text) {
		String sql = "select * from vacant_lookup where state='%s' and parent_id='root' and type=?";
		sql = String.format(sql, COMMON_STATE_YX);
		List<Lookup> list = jdbcTemplate.query(sql, new String[] { type }, mapper());

		Lookup l = new Lookup();
		l.setCode("");
		l.setText(text);
		list.add(0, l);
		return list;
	}

	public List<Lookup> children(String parentId) {
		String sql = String.format("select * from vacant_lookup where state='%s' and parent_id=?", COMMON_STATE_YX);
		List<Lookup> list = jdbcTemplate.query(sql, new String[] { parentId }, mapper());
		if (list.isEmpty()) {
			return null;
		}
		return list;
	}

	private RowMapper<Lookup> mapper() {
		return new RowMapper<Lookup>() {
			@Override
			public Lookup mapRow(ResultSet rs, int rowNum) throws SQLException {
				Lookup l = new Lookup();
				l.setId(rs.getString("id"));
				l.setParentId(rs.getString("parent_id"));
				l.setType(rs.getString("type"));
				l.setCode(rs.getString("code"));
				l.setText(rs.getString("text"));
				l.setState(rs.getString("state"));

				return l;
			}
		};
	}

	public static void main(String... args) throws Exception {
		System.out.println(
				String.format("select * from vacant_lookup where state='%s' and parent_id=?", COMMON_STATE_YX));
	}
}
