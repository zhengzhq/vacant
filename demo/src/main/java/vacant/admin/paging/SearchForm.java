package vacant.admin.paging;

import java.util.HashMap;
import java.util.Map;

public class SearchForm {

	private int pageNum=1; // 页码
	private int numPerPage=10;
	private Map<String, String> conditions=new HashMap<String, String>(); // 条件
	
	public Map<String, String> getConditions() {
		return conditions;
	}
	public void setConditions(Map<String, String> conditions) {
		this.conditions = conditions;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
}
