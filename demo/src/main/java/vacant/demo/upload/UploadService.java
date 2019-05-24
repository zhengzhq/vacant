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

import net.coobird.thumbnailator.Thumbnails;
import vacant.Utils;
import vacant.demo.upload.attach.Attach;
import vacant.demo.upload.attach.AttachService;

@Service
@Transactional
public class UploadService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private AttachService attachService;

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
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	public void delete(String id) {
		List<Attach> list = attachService.listByUploadId(id);
		for (Attach attach : list) {
			attachService.delete(attach.getId());
		}
		jdbcTemplate.update("delete from demo_upload where id=?", id);

	}

	public void insert(Upload upload, MultipartFile files[]) throws Exception {
		String id = Utils.uuid(); // upload id
		String createTime = Utils.dateTime();
		String createUser = Utils.userName();
		// 保存attach
		String sql = "insert into demo_upload_attach values(uuid(),?,?,?,?,?,?)";
		for (MultipartFile file : files) {
			String origName = file.getOriginalFilename();
			String relativePath = Utils.attachPath(origName);
			String fullPath = Utils.fullPath(relativePath);
			File dir = new File(fullPath).getParentFile();
			if (!dir.exists()) {
				dir.mkdirs();
			}
			// file.transferTo(new File(fullPath));
			Thumbnails.of(file.getInputStream()).size(744, 992).outputFormat("jpg")
					.toFile(new File(fullPath));

			jdbcTemplate.update(sql, id, file.getSize(), relativePath, origName, createTime, createUser);
		}
		// 保存upload
		sql = "insert into demo_upload values(?,?,?,?,?)";
		jdbcTemplate.update(sql, id, upload.getType(), upload.getDesc(), createTime, createUser);
	}

	public void update(Upload upload, MultipartFile[] files) throws Exception {
		String id = upload.getId();
		String createTime = Utils.dateTime();
		String createUser = Utils.userName();
		// 删除原有attach
		List<Attach> list = attachService.listByUploadId(id);
		for (Attach attach : list) {
			attachService.delete(attach.getId());
		}
		
		// 保存新attach
		String sql = "insert into demo_upload_attach values(uuid(),?,?,?,?,?,?)";
		for (MultipartFile file : files) {
			String origName = file.getOriginalFilename();
			String relativePath = Utils.attachPath(origName);
			String fullPath = Utils.fullPath(relativePath);
			File dir = new File(fullPath).getParentFile();
			if (!dir.exists()) {
				dir.mkdirs();
			}
			file.transferTo(new File(fullPath));

			jdbcTemplate.update(sql, id, file.getSize(), relativePath, origName, createTime, createUser);
		}
		// 保存upload
		sql = "update demo_upload set `desc`=? where id=?";
		jdbcTemplate.update(sql, upload.getDesc(), id);
	}

}
