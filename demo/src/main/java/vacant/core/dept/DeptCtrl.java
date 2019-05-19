package vacant.core.dept;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vacant.AjaxResponse;
import vacant.BaseCtrl;
import vacant.core.paging.Book;
import vacant.core.paging.PageService;
import vacant.core.paging.SearchForm;

@Controller
@RequestMapping(path = "/vacant/dept")
public class DeptCtrl extends BaseCtrl{
	
	@Override
	protected String v() {
		return "vacant/dept/vacant_dept";
	}
	
	@Autowired
	private DeptService deptService;
	
	@Autowired
	private PageService pageService;

	@RequestMapping
	public String list(@ModelAttribute SearchForm searchForm, Model model) {
		Map<String, String> conditions = searchForm.getConditions();
		if(conditions.isEmpty()) {
			conditions.put("area_code_lk", "2201");
		}
		String sql = "select * from vacant_dept";
		String sql2 = "select count(*) totalCount from vacant_dept";
		Book book = pageService.turnTo(sql, searchForm, sql2);
		model.addAttribute("book", book);
		return v();
	}

	@GetMapping(path = "add")
	public String add(Model model) {
		Dept o = new Dept();
		model.addAttribute("o",o);
		return v("edit");
	}

	@PostMapping(path = "save")
	@ResponseBody
	public AjaxResponse save(Dept dept, HttpServletRequest req) {
		String czjlName = StringUtils.isEmpty(dept.getId())?"添加用户":"修改用户";
		try {
			deptService.save(dept);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req, czjlName);
		return AjaxResponse.dialogCloseAndReload("vacant_dept");
	}

	@GetMapping(path = "edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		Dept o = deptService.findByPk(id);
		model.addAttribute("o",o);
		return v("edit");
	}

	@RequestMapping(path = "delete/{id}")
	@ResponseBody
	public AjaxResponse delete(@PathVariable String id, Model model, HttpServletRequest req) {
		try {
			deptService.delete(id);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req, "删除单位");
		
		return AjaxResponse.ok();
	}

	@RequestMapping(path = "lookup")
	public String lookup(@ModelAttribute SearchForm searchForm, Model model) {
		String sql = "select * from vacant_dept";
		String sql2 = "select count(*) totalCount from vacant_dept";
		Book book = pageService.turnTo(sql, searchForm, sql2);
		model.addAttribute("book", book);
		return v("lookup");
	}
	
}
