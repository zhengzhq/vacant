package vacant.demo.upload.attach;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.coobird.thumbnailator.Thumbnails;
import vacant.AjaxResponse;
import vacant.BaseCtrl;
import vacant.Utils;
import vacant.admin.paging.Book;
import vacant.admin.paging.PageService;
import vacant.admin.paging.SearchForm;

@Controller
@RequestMapping(path = "demo/attach")
public class AttachCtrl extends BaseCtrl {

	@Autowired
	private AttachService attachService;
	@Autowired
	private PageService pageService;

	@RequestMapping
	public String list(@ModelAttribute SearchForm searchForm, Model model) {
		Map<String, String> conditions = searchForm.getConditions();
		if (conditions.isEmpty()) {
			conditions.put("create_time_lk", Utils.date());
		}
		String sql = "select * from demo_upload_attach";
		String sql2 = "select count(*) totalCount from demo_upload_attach";
		Book book = pageService.turnTo(sql, searchForm, sql2);
		model.addAttribute("book", book);
		return v();
	}

	@RequestMapping(path = "download/{id}")
	public void download(@PathVariable String id, HttpServletResponse response) throws Exception {
		Attach attach = attachService.findByPk(id);
		String relativePath = attach.getRelativePath();
		String fileName = relativePath.substring(relativePath.lastIndexOf("/") + 1);
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		FileCopyUtils.copy(new FileInputStream(new File(Utils.fullPath(relativePath))), response.getOutputStream());
	}

	@RequestMapping(path = "preview/{id}")
	public void preview(@PathVariable String id, HttpServletResponse response) throws Exception {
		Attach attach = attachService.findByPk(id);
		String relativePath = attach.getRelativePath();
		String fileName = relativePath.substring(relativePath.lastIndexOf("/") + 1);
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		Thumbnails.of(new FileInputStream(Utils.fullPath(relativePath))).size(48, 64)
				.toOutputStream(response.getOutputStream());
//		FileCopyUtils.copy(new FileInputStream(new File(Utils.fullPath(relativePath))), response.getOutputStream());
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
		return "demo/attach/demo_attach";
	}
}
