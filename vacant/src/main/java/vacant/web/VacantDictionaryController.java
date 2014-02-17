package vacant.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vacant.domain.VacantDictionaryItem;
import vacant.service.VacantDictionaryService;

@Controller
@RequestMapping("/dict")
public class VacantDictionaryController {

	@Autowired
	private VacantDictionaryService dictionaryService;

	@RequestMapping("/get_by_type")
	@ResponseBody
	public List<VacantDictionaryItem> getItemListByType(
			@RequestParam("type") String type) {
		return dictionaryService.getItemListByType(type);
	}

}
