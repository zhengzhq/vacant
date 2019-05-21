package vacant.demo.upload.attach;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vacant.Utils;

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
				attach.setRelativePath(rs.getString("relative_path"));
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

	public void delete(String id) {
		Attach attach = findByPk(id);
		String fullPath = Utils.fullPath(attach.getRelativePath());
		new File(fullPath).delete();
		jdbcTemplate.update("delete from demo_upload_attach where id=?", id);
		
	}
}
