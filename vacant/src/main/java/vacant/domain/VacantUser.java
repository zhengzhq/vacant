package vacant.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="vacant_user")
public class VacantUser extends WriteOffable {
	
	@Transient
	private Set<String> resourceUrlSet;

	public Set<String> getResourceList() {
		return resourceUrlSet;
	}

	public void setResourceList(Set<String> resourceUrlSet) {
		this.resourceUrlSet = resourceUrlSet;
	}

	//-----------------------------------------------------------------
	
	@Column(name="area_code")
	private String areaCode;
	
	@Column(name="department_id")
	private String departmentId;

	@Column(name="login_name")
	private String loginName;

	@Column(name="password")
	private String password;

	@Column(name="name")
	private String name;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
