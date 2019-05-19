package vacant.demo.upload.attach;

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
public class AttachService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Attach> listByUploadId(String uploadId) {
		String sql = "select * from demo_upload_attach where upload_id=?";
		return jdbcTemplate.query(sql, new String[] { uploadId }, mapper());
	}

	private RowMapper<Attach> mapper() {
		return new RowMapper<Attach>() {
			@Override
			public Attach mapRow(ResultSet rs, int rowNum) throws SQLException {
				Attach attach = new Attach();
				attach.setId(rs.getString("id"));
				attach.setUploadId(rs.getString("upload_id"));
				attach.setSize(rs.getInt("size"));
				attach.setPath(rs.getString("path"));
				attach.setOrigName(rs.getString("orig_name"));
				attach.setCreateTime(rs.getString("create_time"));
				attach.setCreateUser(rs.getString("create_user"));
				return attach;
			}
		};
	}

	public Attach findByPk(String id) {
		List<Attach> list = jdbcTemplate.query("select * from demo_upload_attach where id=?", new String[] { id },
				mapper());
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
}
