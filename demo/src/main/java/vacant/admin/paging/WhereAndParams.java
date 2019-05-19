package vacant.admin.paging;

/**
 * 条件和值
 * @author zzq
 *
 */
public class WhereAndParams {

	// 查询条件字符串
	private String where;
	
	// 查询参数
	private Object[] params;

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
}
