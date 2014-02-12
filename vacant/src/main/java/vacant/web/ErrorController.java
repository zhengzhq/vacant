package vacant.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import vacant.service.VacantResourceService;
import vacant.service.VacantUserService;

@RequestMapping("/error")
@Controller
public class ErrorController {

	@Autowired
	private VacantUserService userService;
	@Autowired
	private VacantResourceService resourceService;

	@RequestMapping("/no_privilege")
	public String noPrivilege() {
		return "/error/no_privilege";
	}

	@RequestMapping("/system_error")
	public String systemError() {
		return "/error/system_error";
	}
}
