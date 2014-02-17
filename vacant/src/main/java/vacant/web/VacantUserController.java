package vacant.web;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vacant.domain.VacantUser;
import vacant.service.VacantUserService;
import vacant.util.AjaxResult;
import vacant.util.Page;

@Controller
@RequestMapping("/user")
public class VacantUserController {

	@Autowired
	private VacantUserService userService;

	@RequestMapping
	public String page1() {
		return "/user";
	}

	@RequestMapping(value = "/query_page")
	@ResponseBody
	public Page<VacantUser> queryUserByPage(
			@RequestParam(value = "loginName", required = false) String loginName,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "rows", required = false, defaultValue = "10") int rows)
			throws NoSuchFieldException {

		return userService.getPage(loginName, name, page, rows);
	}

	@RequestMapping(value = "/save_user", method = POST)
	@ResponseBody
	public AjaxResult saveUser(VacantUser user) {
		userService.save(user);
		return AjaxResult.success();
	}

	@RequestMapping(value = "/ajax/remove_user", method = POST)
	@ResponseBody
	public AjaxResult removeUser(@RequestParam("id") String id,
			@RequestParam("writtenOffReason") String writtenOffReason) {
		userService.remove(id, writtenOffReason);
		return AjaxResult.success();
	}
}
