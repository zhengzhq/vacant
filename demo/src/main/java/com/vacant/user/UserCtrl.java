package com.vacant.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.vacant.BaseCtrl;
import com.vacant.Utils;
import com.vacant.demo.paging.Book;
import com.vacant.demo.paging.PageService;
import com.vacant.demo.paging.SearchForm;
import com.vacant.role.RoleService;
import com.vacant.user.VacantUser;
import com.vacant.user.UserService;

@Controller
@RequestMapping(path = "/vacant/user")
public class UserCtrl extends BaseCtrl {

	@Override
	protected String v() {
		return "vacant/user/vacant_user";
	}

	@Autowired
	private UserService userService;

	@Autowired
	private PageService pageService;
	
	@Autowired
	private RoleService roleService;

	@RequestMapping
	public String list(@ModelAttribute SearchForm searchForm, Model model) {
		Map<String, String> conditions = searchForm.getConditions();
		if (conditions.isEmpty()) {
			conditions.put("user.area_code_lk", "2201");
			conditions.put("role_id_eq", "");
		}
		String from = "from vacant_user user join vacant_dept dept on user.dept_id=dept.id ";
		from += "left join vacant_role role on user.role_id=role.id ";
		String sql = "select user.*, dept.name dept_name, role.name role_name " + from;
		String sql2 = "select count(*) totalCount " + from;
		Book book = pageService.turnTo(sql, searchForm, sql2);
		model.addAttribute("book", book);
		model.addAttribute("roleList", roleService.all());
		return v();
	}

	@GetMapping(path = "add")
	public String add(Model model) {
		VacantUser o = new VacantUser();
		o.setCreateTime(Utils.dateTime());
		model.addAttribute("o", o);
		model.addAttribute("roleList", roleService.all());
		return v("add");
	}

	@PostMapping(path = "add")
	@ResponseBody
	public AjaxResponse add(VacantUser user) {
		try {
			userService.add(user);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		return AjaxResponse.dialogOk("vacant_user");
	}

	@GetMapping(path = "edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		VacantUser o = userService.findByPk(id);
		model.addAttribute("o", o);
		model.addAttribute("roleList", roleService.all());
		return v("edit");
	}

	@PostMapping(path = "edit")
	@ResponseBody
	public AjaxResponse edit(VacantUser user, HttpServletRequest req) {
		try {
			userService.edit(user);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req,"保存用户编辑");
		return AjaxResponse.dialogOk("vacant_user");
	}

	@RequestMapping(path = "delete/{id}")
	@ResponseBody
	public AjaxResponse delete(@PathVariable String id, Model model) {
		try {
			userService.delete(id);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		return AjaxResponse.ok();
	}

}
