package vacant.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import vacant.util.DictCode;

@Entity
@Table(name = "vacant_user")
public class VacantUser extends WrittenOffable {

	@ManyToOne
	@JoinColumn(name = "organ_id", insertable = false, updatable = false, nullable = true)
	private VacantOrgan organ;
	
	@ManyToOne
	@JoinColumn(name = "department_id", insertable = false, updatable = false, nullable = true)
	private VacantDepartment department;

	@ManyToOne
	@JoinColumn(name = "role_id", insertable = false, updatable = false, nullable = true)
	private VacantRole role;

	@Transient
	private String genderValue;

	@Transient
	private Set<String> resourceUrlSet;

	public VacantDepartment getDepartment() {
		return department;
	}

	public void setDepartment(VacantDepartment department) {
		this.department = department;
	}

	public Set<String> getResourceUrlSet() {
		return resourceUrlSet;
	}

	public void setResourceUrlSet(Set<String> resourceUrlSet) {
		this.resourceUrlSet = resourceUrlSet;
	}

	// -----------------------------------------------------------------

	@Column(name = "area_code")
	private String areaCode;

	@Column(name = "organ_id")
	private String organId;

	@Column(name = "department_id")
	private String departmentId;

	@Column(name = "login_name")
	private String loginName;

	@Column(name = "password")
	private String password;

	@Column(name = "name")
	private String name;

	@DictCode(type="gender")
	@Column(name = "gender")
	private String gender;

	@Column(name = "role_id")
	private String roleId;

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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public VacantRole getRole() {
		return role;
	}

	public void setRole(VacantRole role) {
		this.role = role;
	}

	public String getGenderValue() {
		return genderValue;
	}

	public void setGenderValue(String genderValue) {
		this.genderValue = genderValue;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public VacantOrgan getOrgan() {
		return organ;
	}

	public void setOrgan(VacantOrgan organ) {
		this.organ = organ;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

}
