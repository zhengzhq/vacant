package com.vacant.role;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vacant.AjaxResponse;
import com.vacant.BaseControl;
import com.vacant.demo.paging.Book;
import com.vacant.demo.paging.PageService;
import com.vacant.demo.paging.SearchForm;
import com.vacant.dept.DeptService;
import com.vacant.dept.VacantDept;

@Controller
@RequestMapping(path = "/vacant/dept")
public class DeptCtrl extends BaseControl{
	
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
		VacantDept o = new VacantDept();
		model.addAttribute("o",o);
		return v("edit");
	}

	@PostMapping(path = "save")
	@ResponseBody
	public AjaxResponse save(VacantDept dept) {
		deptService.save(dept);
		return AjaxResponse.dialogOk("vacant_dept");
	}

	@GetMapping(path = "edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		VacantDept o = deptService.findByPk(id);
		model.addAttribute("o",o);
		return v("edit");
	}
}
