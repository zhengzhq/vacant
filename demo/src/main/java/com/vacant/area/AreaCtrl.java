package com.vacant.area;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vacant.AjaxResponse;
import com.vacant.BaseCtrl;
import com.vacant.Utils;

@Controller
@RequestMapping(path = "/vacant/area")
public class AreaCtrl extends BaseCtrl{
	
	@Autowired
	private AreaService areaService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping
	public String list(Model model) {
		String sql = "select count(*) yx, count(case when length(code)=4 then 1 end) sj, ";
		sql += "count(case when length(code)=6 then 1 end) xq, ";
		sql += "count(case when length(code)=9 then 1 end) xz, ";
		sql += "count(case when length(code)=12 then 1 end) sq ";
		sql += "from vacant_area where state='有效' ";
		Map<String, Object> stats = jdbcTemplate.queryForMap(sql);
		model.addAttribute("stats", stats);
		return v();
	}

	@PostMapping(path = "tree")
	@ResponseBody
	public Area tree() {
		return areaService.root();
	}

	@GetMapping(path = "add")
	public String add(Area parent, Model model) {
		Area o = new Area();
		o.setParentId(parent.getId());
		o.setParentCode(parent.getCode());
		o.setState("有效");
		model.addAttribute("o", o);
		return v("add");
	}
	
	@PostMapping(path = "insert")
	@ResponseBody
	public AjaxResponse insert(Area area, HttpServletRequest req) {
		try {
			areaService.insert(area);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req, "添加行政区划");
		return AjaxResponse.dialogCloseAndReload("vacant_area");
	}

	@GetMapping(path = "edit")
	public String edit(@RequestParam String id, Model model) {
		Area o = areaService.findByPk(id);
		if(StringUtils.isEmpty(o.getParentId())) {
			model.addAttribute("msg","不能修改行政区划根节点");
			return "error";
		}
		String code = o.getCode();
		o.setParentCode(Utils.parentCode(code));
		o.setSubCode(Utils.subCode(code));
		model.addAttribute("o", o);
		return v("edit");
	}

	@PostMapping(path = "update")
	@ResponseBody
	public AjaxResponse update(Area area, HttpServletRequest req) {
		try {
			areaService.update(area);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req, "修改行政区划");
		return AjaxResponse.dialogCloseAndReload("vacant_area");
	}
	
	@RequestMapping(path = "delete")
	@ResponseBody
	public AjaxResponse delete(@RequestParam String id, Model model, HttpServletRequest req) {
		try {
			areaService.delete(id);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		czjl(req, "删除行政区划");
		return AjaxResponse.ok();
	}

	@Override
	protected String v() {
		return "vacant/area/vacant_area";
	}

	@RequestMapping(path = "lookup/{op}")
	public String lookup(Model model, @PathVariable String op) {
		model.addAttribute("op", op);
		return v("lookup");
	}	
}
