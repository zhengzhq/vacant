package vacant.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vacant.domain.VacantResource;
import vacant.domain.VacantUser;
import vacant.service.VacantResourceService;
import vacant.service.VacantUserService;
import vacant.util.AjaxResult;

@Controller
public class LoginController {
	public static final String SESSION_USER = "user";

	@Autowired
	private VacantUserService userService;
	@Autowired
	private VacantResourceService resourceService;

	@RequestMapping(value = "/login", method = GET)
	public String login() {
		return "login";
	}

	@ResponseBody
	@RequestMapping("/login_validate")
	public AjaxResult loginValidate(VacantUser user) {

		VacantUser user2 = userService.findUserByLoginName(user.getLoginName());
		if (user != null) {
			if (user2.getPassword().equals(user.getPassword())) {
				return AjaxResult.success();
			}

		}
		return AjaxResult.fail("用户名或密码错误。");
	}

	@RequestMapping(value = "/login", method = POST)
	public String login(HttpServletRequest request, VacantUser user) {
		user = userService.findUserByLoginName(user.getLoginName());
		// 把用户权限放到用户中
		List<VacantResource> resourceList = resourceService
				.getResourceListByUserId(user.getId());
		user.setResourceList(resourceList);
		request.getSession().setAttribute(SESSION_USER, user);
		return "main";
	}
}
