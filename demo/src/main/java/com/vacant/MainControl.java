package com.vacant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vacant.security.UserService;
import com.vacant.security.VacantMenu;

@Controller
public class MainControl {

	@Autowired
	private UserService userService;

	@RequestMapping(path = "/main")
	public String main(Model model) {
		List<VacantMenu> zxtList = userService.zxlList();
		model.addAttribute("zxtList", zxtList);
		return "main";
	}

}
