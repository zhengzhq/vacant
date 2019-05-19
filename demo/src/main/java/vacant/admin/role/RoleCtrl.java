package vacant.admin.role;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vacant.AjaxResponse;
import vacant.BaseCtrl;
import vacant.admin.menu.MenuService;
import vacant.admin.menu.VacantMenu;
import vacant.admin.paging.SearchForm;

@Controller
@RequestMapping(path = "/vacant/role")
public class RoleCtrl extends BaseCtrl{
	
	@Override
	protected String v() {
		return "/vacant/role/vacant_role";
	}
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;
	
	@RequestMapping
	public String list(@ModelAttribute SearchForm searchForm, Model model) {
		model.addAttribute("list", roleService.all());
		return v();
	}

	@GetMapping(path = "add")
	public String add(Model model) {
		Role o = new Role();
		model.addAttribute("role",o);
		//model.addAttribute("menuList",menuService.zxlList());
		return v("edit");
	}

	@GetMapping(path = "edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		Role o = roleService.findByPk(id);
		model.addAttribute("role", o);
		return v("edit");
	}

	@RequestMapping(path="menu/{id}")
	@ResponseBody
	public List<VacantMenu> menuListForRole(@PathVariable String id) {
		return menuService.childrenForRole(id, "root");
	}
	
	@PostMapping(path = "save")
	@ResponseBody
	public AjaxResponse save(Role role, HttpServletRequest req) {
		String czjlName = StringUtils.isEmpty(role.getId())?"添加角色":"修改角色";
		try {
			roleService.save(role);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req, czjlName);
		return AjaxResponse.dialogCloseAndReload("vacant_role");
	}

	@RequestMapping(path = "delete/{id}")
	@ResponseBody
	public AjaxResponse delete(@PathVariable String id, Model model, HttpServletRequest req) {
		try {
			roleService.delete(id);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req, "删除角色");
		return AjaxResponse.ok();
	}
	
}
