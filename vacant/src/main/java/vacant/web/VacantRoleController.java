package vacant.web;

import java.util.List;

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

	@RequestMapping
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

	@RequestMapping("/save_role")
	@ResponseBody
	public AjaxResult saveRole(VacantRole role) {
		roleService.save(role);
		return AjaxResult.success();
	}

	@RequestMapping("/remove_role")
	@ResponseBody
	public AjaxResult removeRole(@RequestParam("id") String id) {
		roleService.remove(id);
		return AjaxResult.success();
	}

	@RequestMapping("/get_all_role")
	@ResponseBody
	public List<VacantRole> getAllRoleList() {
		return roleService.getAllRoleList();
	}
}
