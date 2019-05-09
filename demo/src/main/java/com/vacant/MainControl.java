package com.vacant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vacant.menu.MenuService;
import com.vacant.menu.VacantMenu;

@Controller
public class MainControl {

	@Autowired
	private MenuService menuService;

	@RequestMapping(path = "/main")
	public String main(Model model) {
		List<VacantMenu> zxtList = menuService.childrenForUser("root", Utils.userId(), false);
		model.addAttribute("zxtList", zxtList);
		return "main";
	}

	@RequestMapping(path = "/main/menu/{parentId}")
	@ResponseBody
	public List<VacantMenu> menuList(@PathVariable String parentId) {
		return menuService.childrenForUser(parentId, Utils.userId(), true);
	}
}
