package com.vacant.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vacant.AjaxResponse;

@Controller
@RequestMapping(path = "/vacant/security/menu")
public class MenuControl {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MenuService menuService;

	private static final String view = "vacant/security/menu/vacant_security_menu";

	private String v(String page) {
		return view + "_" + page;
	}

	@RequestMapping
	public String list(Model model) {
		model.addAttribute("menuList", menuService.zxlList());
		return view;
	}
	
	@GetMapping(path = "add")
	public String add(@RequestParam String parentId, Model model) {
		VacantMenu menu = new VacantMenu();
		menu.setParentId(parentId);
		model.addAttribute("menu", menu);
		return v("edit");
	}
	
	@PostMapping(path = "save")
	@ResponseBody
	public AjaxResponse save(VacantMenu menu) {
		menuService.save(menu);
		return AjaxResponse.dialogOk("vacant_security_menu");
	}

	@GetMapping(path = "edit")
	public String edit(@RequestParam String id, Model model) {
		VacantMenu menu = menuService.findByPk(id);
		model.addAttribute("menu", menu);
		return v("edit");
	}

	@RequestMapping(path = "delete")
	@ResponseBody
	public AjaxResponse delete(@RequestParam String id, Model model) {
		if(menuService.hasChild(id)) {
			return AjaxResponse.error("存在子节点，不能删除！");
		}
		menuService.delete(id);
		return AjaxResponse.ok();
	}
	
}
