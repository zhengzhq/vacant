package vacant.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vacant.domain.VacantDepartment;
import vacant.domain.VacantUser;
import vacant.util.AjaxResult;

@Controller
@RequestMapping("/model1")
public class Model1Controller {

	@RequestMapping(value = "/main", method = GET)
	public String page1() {
		return "/model1/model1_main";
	}

	@RequestMapping(value = "/page2", method = GET)
	public String page2() {
		return "/model1/model1_page2";
	}

	@RequestMapping(value = "/query_page")
	@ResponseBody
	public List<VacantUser> queryPage(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "rows", required = false, defaultValue = "10") int rows) {
		ArrayList<VacantUser> list = new ArrayList<VacantUser>();
		
		VacantDepartment d = new VacantDepartment();
		d.setName("dept");
		
		VacantUser user = new VacantUser();
		user.setName("a");
		user.setDepartment(d);
		list.add(user);
		user = new VacantUser();
		user.setName("b");
		user.setDepartment(d);
		list.add(user);
		return list;
	}
}
