package vacant.admin.czjl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import vacant.BaseCtrl;
import vacant.Utils;
import vacant.admin.paging.Book;
import vacant.admin.paging.PageService;
import vacant.admin.paging.SearchForm;

@Controller
@RequestMapping(path = "/vacant/czjl")
public class CzjlCtrl extends BaseCtrl {

	@Override
	protected String v() {
		return "vacant/czjl/vacant_czjl";
	}

	@Autowired
	private PageService pageService;

	@RequestMapping
	public String list(@ModelAttribute SearchForm searchForm, Model model) {
		Map<String, String> conditions = searchForm.getConditions();
		if (conditions.isEmpty()) {
			conditions.put("czsj_lk", Utils.date());
		}
		String sql = "select * from vacant_czjl";
		String sql2 = "select count(*) totalCount from vacant_czjl";
		Book book = pageService.turnTo(sql, searchForm, sql2, "order by czsj desc");
		model.addAttribute("book", book);
		return v();
	}

}
