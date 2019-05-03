package com.vacant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginCtrl extends BaseControl {

	@RequestMapping(path = "/login")
	public String login(HttpServletRequest req) {
		return "login";
	}

	@RequestMapping(path = "/logout")
	public String logout(HttpServletRequest req) {
		czjl(req, "退出");
		return "login";
	}
	
	@Override
	protected String v() {
		return null;
	}

}