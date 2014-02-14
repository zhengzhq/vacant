package vacant.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="vacant_resource")
public class VacantResource extends BaseDomain {
	
	@Column(name="url")
	private String url;
	
	@Column(name="is_display")
	private String isDisplay;
	
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

	public String getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
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
	
	public String get_parentId() {
		return parentId;
	}
	
}
