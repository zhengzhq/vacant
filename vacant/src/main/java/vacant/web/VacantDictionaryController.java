package vacant.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vacant.domain.VacantDictionaryItem;
import vacant.service.VacantDictionaryService;

@Controller
@RequestMapping("/dict")
public class VacantDictionaryController {

	@Autowired
	private VacantDictionaryService dictionaryService;

	@RequestMapping("/get/{type}")
	@ResponseBody
	public List<VacantDictionaryItem> getItemListByType(
			@PathVariable("type") String type) {
		return dictionaryService.getItemListByType(type);
	}

}
