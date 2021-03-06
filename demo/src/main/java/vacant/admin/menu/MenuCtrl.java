package vacant.admin.menu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vacant.AjaxResponse;
import vacant.BaseCtrl;

@Controller
@RequestMapping(path = "/vacant/menu")
public class MenuCtrl extends BaseCtrl {
	
	@Autowired
	private MenuService menuService;

	@Override
	protected String v() {
		return "vacant/menu/vacant_menu";
	}

	@RequestMapping
	public String list(Model model) {
		return v();
	}
	
	@PostMapping(path = "tree")
	@ResponseBody
	public List<VacantMenu> tree() {
		List<VacantMenu> tree = menuService.children("root", true);
		return tree;
	}
	
	@GetMapping(path = "add")
	public String add(@RequestParam String parentId, Model model) {
		VacantMenu menu = new VacantMenu();
		menu.setParentId(parentId);
		model.addAttribute("menu", menu);
		return v("edit");
	}
	
	@PostMapping(path = "save")
	@ResponseBody
	public AjaxResponse save(VacantMenu menu, HttpServletRequest req) {
		String czjlName = StringUtils.isEmpty(menu.getId())?"添加菜单":"修改菜单";
		try {
			menuService.save(menu);
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req, czjlName);
		return AjaxResponse.dialogCloseAndReload("vacant_menu");
	}

	@GetMapping(path = "edit")
	public String edit(@RequestParam String id, Model model) {
		VacantMenu menu = menuService.findByPk(id);
		model.addAttribute("menu", menu);
		return v("edit");
	}

	@RequestMapping(path = "delete")
	@ResponseBody
	public AjaxResponse delete(@RequestParam String id, Model model, HttpServletRequest req) {
		try {
			menuService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req, "删除菜单");
		return AjaxResponse.ok();
	}
	
}
