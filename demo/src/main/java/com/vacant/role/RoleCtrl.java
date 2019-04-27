package com.vacant.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vacant.AjaxResponse;
import com.vacant.BaseControl;
import com.vacant.security.MenuService;
import com.vacant.security.VacantMenu;

@Controller
@RequestMapping(path = "/vacant/role")
public class RoleCtrl extends BaseControl{
	
	@Override
	protected String v() {
		return "/vacant/role/vacant_role";
	}
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;
	
	@RequestMapping
	public String list(Model model) {
		model.addAttribute("list", roleService.all());
		return v();
	}

	@GetMapping(path = "add")
	public String add(Model model) {
		VacantRole o = new VacantRole();
		model.addAttribute("role",o);
		//model.addAttribute("menuList",menuService.zxlList());
		return v("edit");
	}

	@GetMapping(path = "edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		VacantRole o = roleService.findByPk(id);
		//model.addAttribute("menuList",menuService.menuListForRole(id, "root"));
		model.addAttribute("role", o);
		return v("edit");
	}
	
	@RequestMapping(path="menu/{id}")
	@ResponseBody
	public List<VacantMenu> menuList(@PathVariable String id) {
		return menuService.menuListForRole(id, "root");
	}

	@PostMapping(path = "save")
	@ResponseBody
	public AjaxResponse save(VacantRole role) {
		roleService.save(role);
		return AjaxResponse.dialogOk("vacant_role");
	}

	@RequestMapping(path = "delete/{id}")
	@ResponseBody
	public AjaxResponse delete(@PathVariable String id, Model model) {
		if(roleService.hasUser(id)) {
			return AjaxResponse.error("该角色已经分配给用户，不能删除！");
		}
		roleService.delete(id);
		return AjaxResponse.ok("vacant_role");
	}
	
}
