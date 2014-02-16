package vacant.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import vacant.constant.Global;
import vacant.constant.YesOrNo;
import vacant.service.VacantDictionaryService;
import vacant.service.VacantUserService;

public class ContextRefreshedListener implements
		ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private VacantUserService userService;
	@Autowired
	private VacantDictionaryService dictionaryService;

	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (Global.IS_USE_CACHE.equals(YesOrNo.YES)) {
			dictionaryService.loadDicts();
			userService.loadUserMap();
		}
	}

}
