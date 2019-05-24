package vacant.demo.upload;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import vacant.AjaxResponse;
import vacant.BaseCtrl;
import vacant.Utils;
import vacant.admin.paging.Book;
import vacant.admin.paging.PageService;
import vacant.admin.paging.SearchForm;
import vacant.demo.upload.attach.Attach;
import vacant.demo.upload.attach.AttachService;

@Controller
@RequestMapping(path = "/demo/upload")
public class UploadCtrl extends BaseCtrl {

	private Logger logger = LoggerFactory.getLogger(UploadCtrl.class);

	@Override
	protected String v() {
		return "demo/upload/demo_upload";
	}

	@Autowired
	private UploadService uploadService;
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
		String sql = "select * from demo_upload";
		String sql2 = "select count(*) totalCount from demo_upload";
		Book book = pageService.turnTo(sql, searchForm, sql2);
		model.addAttribute("book", book);
		return v();
	}

	@GetMapping(path = "add/{type}")
	public String add(Model model, @PathVariable String type) {
		Upload o = new Upload();
		o.setType(type);
		model.addAttribute("o", o);
		return v("add" + type);
	}

	@PostMapping(path = "add")
	@ResponseBody
	public AjaxResponse add(Upload upload, HttpServletRequest req, @RequestParam MultipartFile files[]) {
		try {
			uploadService.insert(upload, files);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req, "添加上传记录");
		return AjaxResponse.dialogCloseAndReload("demo_upload");
	}

	@GetMapping(path = "edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		Upload o = uploadService.findByPk(id);
		String type = o.getType();
		model.addAttribute("o", o);
		
		List<Attach> attachList = attachService.listByUploadId(o.getId());
		model.addAttribute("attachList", attachList);
		
		return v("edit"+type);
	}

	@PostMapping(path = "edit")
	@ResponseBody
	public AjaxResponse edit(Upload upload, HttpServletRequest req, @RequestParam MultipartFile files[]) {
		try {
			uploadService.update(upload, files);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req, "编辑上传记录");
		return AjaxResponse.dialogCloseAndReload("demo_upload");
	}

	@RequestMapping(path = "delete/{id}")
	@ResponseBody
	public AjaxResponse delete(@PathVariable String id, Model model, HttpServletRequest req) {
		try {
			uploadService.delete(id);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req, "删除上传记录");

		return AjaxResponse.ok();
	}

}
