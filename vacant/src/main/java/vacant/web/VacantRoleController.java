package vacant.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import vacant.service.VacantUserService;

@Controller
@RequestMapping("/resource")
public class VacantRoleController {

	@Autowired
	private VacantUserService userService;

	@RequestMapping(value = "/main", method = GET)
	public String page1() {
		return "/resource";
	}

}
