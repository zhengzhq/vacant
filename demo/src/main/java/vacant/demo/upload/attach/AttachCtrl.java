package vacant.demo.upload.attach;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "demo/attach")
public class AttachCtrl {
	
	@Autowired
	private AttachService attachService;

	@RequestMapping(path = "download/{id}")
	public void download(@PathVariable String id, HttpServletResponse response) throws Exception {
		Attach attach = attachService.findByPk(id);
		String path = attach.getPath();
		String fileName = path.substring(path.lastIndexOf("/")+1);
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		FileCopyUtils.copy(new FileInputStream(new File(path)), response.getOutputStream());
	}
}
