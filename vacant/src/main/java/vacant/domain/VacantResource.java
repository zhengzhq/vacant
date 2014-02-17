package vacant.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import vacant.util.DictCode;

@Entity
@Table(name="vacant_resource")
public class VacantResource extends BaseDomain {
	@Transient
	private String isPageValue;
	
	@Transient
	private List<VacantResource> children = new ArrayList<VacantResource>();
	
	//--------------------------------
	
	@Column(name="is_top")
	private String isTop;
	
	@Column(name="url")
	private String url;
	
	@Column(name="is_page")
	@DictCode(type="yes_or_no")
	private String isPage;
	
	@Column(name="display_order")
	private String displayOrder;
	
	@Column(name="name")
	private String name;
	
	@Column(name="parent_id")
	private String parentId;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIsPage() {
		return isPage;
	}

	public void setIsPage(String isPage) {
		this.isPage = isPage;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String order) {
		this.displayOrder = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIsPageValue() {
		return isPageValue;
	}

	public void setIsPageValue(String isPageValue) {
		this.isPageValue = isPageValue;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public List<VacantResource> getChildren() {
		return children;
	}

	public void setChildren(List<VacantResource> children) {
		this.children = children;
	}
	
}
