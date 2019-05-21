package vacant.demo.upload.attach;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vacant.AjaxResponse;
import vacant.BaseCtrl;
import vacant.Utils;

@Controller
@RequestMapping(path = "demo/attach")
public class AttachCtrl extends BaseCtrl {

	@Autowired
	private AttachService attachService;

	@RequestMapping(path = "download/{id}")
	public void download(@PathVariable String id, HttpServletResponse response) throws Exception {
		Attach attach = attachService.findByPk(id);
		String relativePath = attach.getRelativePath();
		String fileName = relativePath.substring(relativePath.lastIndexOf("/") + 1);
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		FileCopyUtils.copy(new FileInputStream(new File(Utils.fullPath(relativePath))), response.getOutputStream());
	}

	@RequestMapping(path = "delete/{id}")
	@ResponseBody
	public AjaxResponse delete(@PathVariable String id, Model model, HttpServletRequest req) {
		try {
			attachService.delete(id);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req, "删除附件");

		return AjaxResponse.ok();
	}

	@Override
	protected String v() {
		return "demo/upload/attach/demo_upload_attach";
	}
}
