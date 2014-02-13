package vacant.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vacant.domain.VacantResource;
import vacant.service.VacantResourceService;

@Controller
@RequestMapping("/resource")
public class VacantResourceController {

	@Autowired
	private VacantResourceService resourceService;

	@RequestMapping(value = "/main", method = GET)
	public String page1() {
		return "/resource";
	}
	
	@RequestMapping("/ajax/get_all_resources")
	@ResponseBody
	public Map<String, List<VacantResource>> getAllResources() {
		Map<String, List<VacantResource>> map = new HashMap<String, List<VacantResource>>();
		map.put("rows", resourceService.getAllResourceList());
		return map;
	}

}
