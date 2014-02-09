package vacant.web;

import java.util.HashMap;
import java.util.Map;

public class AppConfig {

	private Map<String, Map<String, String>> dicts = new HashMap<String, Map<String, String>>();

	public void loadDicts() {
		Map<String, String> genderDict = new HashMap<String, String>();
		genderDict.put("1", "男");
		genderDict.put("2", "女");
		dicts.put("gender", genderDict);
	}
	
	public Map<String, Map<String, String>> getDicts() {
		return dicts;
	}

}
