package vacant.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vacant.domain.VacantDictionaryItem;
import vacant.service.VacantDictionaryService;

@Controller
@RequestMapping("/dictionary")
public class VacantDictionaryController {

	@Autowired
	private VacantDictionaryService dictionaryService;

	public List<VacantDictionaryItem> getDictionaryItemList(
			@RequestParam("dictionaryType") String dictionaryType) {
		return dictionaryService.getDictionaryItemList(dictionaryType);
	}

}
