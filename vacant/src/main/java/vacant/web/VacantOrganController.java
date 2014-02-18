package vacant.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vacant.service.VacantOrganService;
import vacant.util.EasyuiTreeNode;

/**
 * 组织机构
 * @author zzq
 *
 * 2014年2月18日
 */
@Controller
@RequestMapping("/organ")
public class VacantOrganController {
	
	@Autowired
	private VacantOrganService organService;

	@RequestMapping("/tree")
	@ResponseBody
	public List<EasyuiTreeNode> getOrganTree() {
		return organService.tree();
	}
}
