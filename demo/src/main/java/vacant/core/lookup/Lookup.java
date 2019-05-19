package vacant.core.lookup;

import java.util.List;

public class Lookup {

	private String id;
	private String parentId;
	private String type;
	private String code;
	private String text;
	private String state;
	
	private List<Lookup> children;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Lookup> getChildren() {
		return children;
	}

	public void setChildren(List<Lookup> children) {
		this.children = children;
	}

}
