package vacant.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vacant.domain.VacantDepartment;
import vacant.service.VacantDepartmentService;
import vacant.util.AjaxResult;
import vacant.util.Page;

@Controller
@RequestMapping("/department")
public class VacantDepartmentController {

	@Autowired
	private VacantDepartmentService departmentService;

	@RequestMapping
	public String main() {
		return "/department";
	}
//
//	@RequestMapping(value = "/query_page")
//	@ResponseBody
//	public Page<VacantDepartment> queryDepartmentByPage(
//			@RequestParam(value = "loginName", required = false) String loginName,
//			@RequestParam(value = "name", required = false) String name,
//			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
//			@RequestParam(value = "rows", required = false, defaultValue = "10") int rows)
//			throws NoSuchFieldException {
//
//		return departmentService.getPage(loginName, name, page, rows);
//	}
//
//	@RequestMapping("/save_department")
//	@ResponseBody
//	public AjaxResult saveDepartment(VacantDepartment department) {
//		departmentService.save(department);
//		return AjaxResult.success();
//	}
//
//	@RequestMapping("/remove_department")
//	@ResponseBody
//	public AjaxResult removeDepartment(@RequestParam("id") String id) {
//		departmentService.remove(id);
//		return AjaxResult.success();
//	}
//
//	@RequestMapping("/get_all_department")
//	@ResponseBody
//	public List<VacantDepartment> getAllDepartmentList() {
//		return departmentService.getAllDepartmentList();
//	}
}
