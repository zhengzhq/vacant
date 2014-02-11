package vacant.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import vacant.service.DictionaryService;

public class ContextRefreshedListener implements
		ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private DictionaryService dictionaryService;

	public void onApplicationEvent(ContextRefreshedEvent event) {
		dictionaryService.loadDicts();
	}

}
