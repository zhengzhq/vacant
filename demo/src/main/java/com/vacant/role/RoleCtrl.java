package com.vacant.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vacant.AjaxResponse;
import com.vacant.BaseControl;

@Controller
@RequestMapping(path = "/vacant/role")
public class RoleCtrl extends BaseControl{
	
	@Override
	protected String v() {
		return "/vacant/role/vacant_role";
	}
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping
	public String list(Model model) {
		model.addAttribute("list", roleService.all());
		return v();
	}

	@GetMapping(path = "add")
	public String add(Model model) {
		VacantRole o = new VacantRole();
		model.addAttribute("role",o);
		return v("edit");
	}

	@GetMapping(path = "edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		VacantRole o = roleService.findByPk(id);
		model.addAttribute("role", o);
		return v("edit");
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
