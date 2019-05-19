package vacant.admin.menu;

import java.util.List;

public class VacantMenu {

	private String id;
	private String parentId;
	private int xssx;
	private String name;
	private String path;
	private String rel;
	private String checked; // 是否选中
	
	private List<VacantMenu> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getXssx() {
		return xssx;
	}

	public void setXssx(int xssx) {
		this.xssx = xssx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public List<VacantMenu> getChildren() {
		return children;
	}

	public void setChildren(List<VacantMenu> children) {
		this.children = children;
	}

	/**
	 * @return the checked
	 */
	public String getChecked() {
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(String checked) {
		this.checked = checked;
	}
	
	public String getDesc() {
		return String.format("%s %s,path=%s,rel=%s", xssx, name, path, rel);
	}
}
