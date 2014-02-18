package vacant.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vacant_department")
public class VacantDepartment extends WrittenOffable {

	@Column(name = "organ_id")
	private String organId;

	@Column(name = "name")
	private String name;

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
