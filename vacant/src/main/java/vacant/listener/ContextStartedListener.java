package vacant.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

import vacant.service.DictionaryService;

public class ContextStartedListener implements ApplicationListener<ContextStartedEvent> {

	@Autowired
	private DictionaryService dictionaryService;
	
	public void onApplicationEvent(ContextStartedEvent event) {
		dictionaryService.loadDicts();
	}

}
