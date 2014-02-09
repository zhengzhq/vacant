package vacant.web;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vacant.constant.Global;
import vacant.domain.VacantResource;
import vacant.domain.VacantUser;
import vacant.service.VacantResourceService;
import vacant.service.VacantUserService;
import vacant.util.AjaxResult;

@Controller
public class LoginController {

	@Autowired
	private VacantUserService userService;
	@Autowired
	private VacantResourceService resourceService;

	@RequestMapping("/login")
	public String login() {
		return "/login/login";
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

	@RequestMapping(value = "/main")
	public String login(HttpServletRequest request, VacantUser user) {
		user = userService.findUserByLoginName(user.getLoginName());
		List<VacantResource> resourceList = resourceService
				.getResourceListByUserId(user.getId());
		// 把用户权限放到用户中
		Set<String> resourceUrlSet = resourceService
				.getResourceUrlSet(resourceList);
		user.setResourceUrlSet(resourceUrlSet);
		request.getSession().setAttribute(Global.SESSION_ATTRIBUTE_USER, user);

		return "/login/main";
	}

	@RequestMapping(value = "/top")
	public String top() {
		return "/login/top";
	}

	@RequestMapping(value = "/left")
	public String left(HttpServletRequest request) {
		// 菜单
		VacantUser user = (VacantUser) request.getSession().getAttribute(
				Global.SESSION_ATTRIBUTE_USER);
		List<VacantResource> resourceList = resourceService
				.getResourceListByUserId(user.getId());
		String treeData = resourceService.getTreeData(resourceList);
		request.setAttribute(Global.REQUEST_ATTRIBUTE_TREE_DATA, treeData);
		return "/login/left";
	}

	@RequestMapping(value = "/right")
	public String right() {
		return "/login/right";
	}
}
