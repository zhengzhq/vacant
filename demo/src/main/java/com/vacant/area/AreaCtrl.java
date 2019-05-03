package com.vacant.area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
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


	@RequestMapping
	public String list(Model model) {
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
	public AjaxResponse insert(Area area) {
		try {
			areaService.insert(area);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		return AjaxResponse.dialogOk("vacant_area");
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
	public AjaxResponse update(Area area) {
		try {
			areaService.update(area);
		} catch (Exception e) {
			return AjaxResponse.error(e.getMessage());
		}
		
		return AjaxResponse.dialogOk("vacant_area");
	}
	
	@RequestMapping(path = "delete")
	@ResponseBody
	public AjaxResponse delete(@RequestParam String id, Model model) {
		if(areaService.hasChild(id)) {
			return AjaxResponse.error("存在子节点，不能删除！");
		}
		areaService.delete(id);
		return AjaxResponse.ok();
	}

	@Override
	protected String v() {
		return "vacant/area/vacant_area";
	}
	
}
