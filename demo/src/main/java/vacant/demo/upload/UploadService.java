package vacant.demo.upload;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vacant.Utils;

@Service
@Transactional
public class UploadService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Upload> mapper() {
		return new RowMapper<Upload>() {
			@Override
			public Upload mapRow(ResultSet rs, int rowNum) throws SQLException {
				Upload upload = new Upload();
				upload.setId(rs.getString("id"));
				upload.setType(rs.getString("type"));
				upload.setDesc(rs.getString("desc"));
				upload.setCreateTime(rs.getString("create_time"));
				upload.setCreateUser(rs.getString("create_user"));
				return upload;
			}
		};
	}

	public Upload findByPk(String id) {
		List<Upload> list = jdbcTemplate.query("select * from demo_upload where id=?", new String[] { id }, mapper());
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	public void insert(Upload upload, MultipartFile files[]) throws Exception {
		String id = Utils.uuid(); // upload id
		String createTime = Utils.dateTime();
		String createUser = Utils.userName();
		// 保存attach
		String sql = "insert into demo_upload_attach values(uuid(),?,?,?,?,?,?)";
		for (MultipartFile file : files) {
			String origName = file.getOriginalFilename();
			String path = Utils.attachPath(origName);
			File dir = new File(path).getParentFile();
			if (!dir.exists()) {
				dir.mkdirs();
			}
			file.transferTo(new File(path));

			jdbcTemplate.update(sql, id, file.getSize(), path, origName, createTime, createUser);
		}
		// 保存upload
		sql = "insert into demo_upload values(?,?,?,?,?)";
		jdbcTemplate.update(sql, id, upload.getType(), upload.getDesc(), createTime, createUser);
	}

}
