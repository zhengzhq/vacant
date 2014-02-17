package vacant.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vacant.domain.VacantResource;
import vacant.service.VacantResourceService;
import vacant.util.AjaxResult;

@Controller
@RequestMapping("/resource")
public class VacantResourceController {

	@Autowired
	private VacantResourceService resourceService;

	@RequestMapping
	public String main() {
		return "/resource";
	}

	@RequestMapping("/ajax/get_all_resources")
	@ResponseBody
	public List<VacantResource> getAllResources() {
		return resourceService.getTopResourceListWithChildren();
	}

	@RequestMapping("/ajax/save_resource")
	@ResponseBody
	public AjaxResult saveResource(VacantResource resource) {
		resourceService.save(resource);
		return AjaxResult.success();
	}

	@RequestMapping("/ajax/remove_resource")
	@ResponseBody
	public AjaxResult removeResource(@RequestParam("id") String id) {
		resourceService.remove(id);
		return AjaxResult.success();
	}

	@RequestMapping("/ajax/grant_resource_to_all_roles")
	@ResponseBody
	public AjaxResult grantResourceToAllRoles(@RequestParam("id") String id) {
		resourceService.grantToAllRols(id);
		return AjaxResult.success();
	}
}
