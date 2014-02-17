package vacant.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vacant_role")
public class VacantRole extends BaseDomain {

	private String name;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
