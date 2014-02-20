package vacant.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于生成easyui tree的json数据
 * 
 * @author zheng zhiqiang
 * 
 */
public class EasyuiTreeNode {
	private String id;
	private String text;
	private String state;
	private boolean checked;
	private List<EasyuiTreeNode> children = new ArrayList<EasyuiTreeNode>();
	private Map<String, String> attributes = new HashMap<String, String>();

	public static final String STATE_CLOSED = "closed";
	public static final String STATE_OPEN = "open";

	public EasyuiTreeNode(String id, String text, String url) {
		this.id = id;
		this.text = text;
		this.state = STATE_OPEN;
		this.attributes.put("url", url);
	}

	public EasyuiTreeNode(String id, String text, String url,
			List<EasyuiTreeNode> children) {
		this.id = id;
		this.text = text;
		this.children = children;
		this.attributes.put("url", url);
		this.state = STATE_CLOSED;
	}
	
	public EasyuiTreeNode(String id, String text, List<EasyuiTreeNode> children) {
		this.id = id;
		this.text = text;
		this.children = children;
	}

	public EasyuiTreeNode(String id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<EasyuiTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<EasyuiTreeNode> children) {
		this.children = children;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

}
