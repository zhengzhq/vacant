package vacant.demo.upload;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vacant.Utils;

@Service
@Transactional
public class UploadService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Upload findByPk(String id) {
		// TODO Auto-generated method stub
		return null;
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
			if(!dir.exists()) {
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
