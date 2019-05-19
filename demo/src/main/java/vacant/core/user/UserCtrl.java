package vacant.core.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vacant.AjaxResponse;
import vacant.BaseCtrl;
import vacant.Utils;
import vacant.core.lookup.Lookup;
import vacant.core.lookup.LookupService;
import vacant.core.paging.Book;
import vacant.core.paging.PageService;
import vacant.core.paging.SearchForm;
import vacant.core.role.RoleService;

@Controller
@RequestMapping(path = "/vacant/user")
public class UserCtrl extends BaseCtrl {

	@Override
	protected String v() {
		return "vacant/user/vacant_user";
	}

	@Autowired
	private UserService userService;

	@Autowired
	private PageService pageService;
	
	@Autowired
	private RoleService roleService;

	@RequestMapping
	public String list(@ModelAttribute SearchForm searchForm, Model model) {
		Map<String, String> conditions = searchForm.getConditions();
		if (conditions.isEmpty()) {
			conditions.put("user.area_code_lk", "2201");
			conditions.put("role_id_eq", "");
		}
		String from = "from vacant_user user join vacant_dept dept on user.dept_id=dept.id ";
		from += "left join vacant_role role on user.role_id=role.id ";
		String sql = "select user.*, dept.name dept_name, role.name role_name " + from;
		String sql2 = "select count(*) totalCount " + from;
		Book book = pageService.turnTo(sql, searchForm, sql2);
		model.addAttribute("book", book);
		model.addAttribute("roleList", roleService.all());
		return v();
	}

	@GetMapping(path = "add")
	public String add(Model model) {
		User o = new User();
		o.setCreateTime(Utils.dateTime());
		model.addAttribute("o", o);
		model.addAttribute("roleList", roleService.all());
		return v("add");
	}

	@PostMapping(path = "add")
	@ResponseBody
	public AjaxResponse add(User user, HttpServletRequest req) {
		try {
			userService.add(user);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req,"添加用户");
		return AjaxResponse.navTabCloseAndReload("vacant_user");
	}

	@GetMapping(path = "edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		User o = userService.findByPk(id);
		model.addAttribute("o", o);
		model.addAttribute("roleList", roleService.all());
		return v("edit");
	}

	@PostMapping(path = "edit")
	@ResponseBody
	public AjaxResponse edit(User user, HttpServletRequest req) {
		try {
			userService.edit(user);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req,"保存用户编辑");
		return AjaxResponse.navTabCloseAndReload("vacant_user");
	}

	@RequestMapping(path = "delete/{id}")
	@ResponseBody
	public AjaxResponse delete(@PathVariable String id, Model model, HttpServletRequest req) {
		try {
			userService.delete(id);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req,"删除用户");
		return AjaxResponse.ok();
	}

	// 显示修改密码页面
	@GetMapping(path = "changepwd")
	public String changepwd() {
		return v("changepwd");
	}

	@PostMapping(path = "changepwd")
	@ResponseBody
	public AjaxResponse changepwd(HttpServletRequest req) {
		try {
			String oldPassword = req.getParameter("oldPassword");
			String newPassword = req.getParameter("newPassword");
			userService.changePwd(oldPassword, newPassword);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req,"修改密码");
		return AjaxResponse.close();
	}
	
	@Autowired
	LookupService lookupService;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@RequestMapping(path = "jmxnbj")
	@ResponseBody
	public AjaxResponse jmxnbj() {
		try {
//			String sql = "insert into vacant_lookup values(uuid(),'1','abc',uuid(),'是短发舒服','1')";
//			for(int i=0; i<5000; i++) {
//				jdbcTemplate.update(sql);
//			}
			long t1 = System.currentTimeMillis();
			for(int i=0;i<1000;i++) {
				lookupService.text("common_state", "1");
			}
			long t2 = System.currentTimeMillis();
			System.out.println("数据库解码用时：" + (t2-t1));
			Map<String, Lookup> map = new HashMap<String, Lookup>();
			Lookup l = new Lookup();
			l.setType("common_state");
			l.setCode("1");;
			l.setText("是短发舒服");;
			map.put("common_state,1", l);
			for(int i=0; i<5000; i++) {
				l = new Lookup();
				String code = Utils.uuid();
				l.setType("abc");
				l.setCode(code);
				l.setText("是短发舒服");;
				map.put("abc," + code, l);
			}
			t1 = System.currentTimeMillis();
			for(int i=0;i<1000;i++) {
				map.get("common_state,1");
			}
			t2 = System.currentTimeMillis();
			System.out.println("内存解码用时：" + (t2-t1));
			
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		return AjaxResponse.ok();
	}

}
