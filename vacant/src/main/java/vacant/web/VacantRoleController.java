package vacant.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vacant.domain.VacantRole;
import vacant.service.VacantRoleService;
import vacant.util.AjaxResult;
import vacant.util.Page;

@Controller
@RequestMapping("/role")
public class VacantRoleController {

	@Autowired
	private VacantRoleService roleService;

	@RequestMapping(value = "/main", method = GET)
	public String page1() {
		return "/role";
	}

	@RequestMapping(value = "/query_page")
	@ResponseBody
	public Page<VacantRole> queryRoleByPage(
			@RequestParam(value = "loginName", required = false) String loginName,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "rows", required = false, defaultValue = "10") int rows)
			throws NoSuchFieldException {

		return roleService.getPage(loginName, name, page, rows);
	}

	@RequestMapping(value = "/save_role", method = POST)
	@ResponseBody
	public AjaxResult saveRole(VacantRole role) {
		roleService.save(role);
		return AjaxResult.success();
	}

	@RequestMapping(value = "/ajax/remove_role", method = POST)
	@ResponseBody
	public AjaxResult removeRole(@RequestParam("id") String id,
			@RequestParam("writtenOffReason") String writtenOffReason) {
		roleService.remove(id, writtenOffReason);
		return AjaxResult.success();
	}
}
