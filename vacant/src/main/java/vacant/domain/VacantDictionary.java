package vacant.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 字典
 * @author zzq
 *
 */
@Entity
@Table(name = "vacant_dictionary")
public class VacantDictionary extends BaseDomain {

	@OneToMany
	@JoinColumn(name="dictionary_id")
	private List<VacantDictionaryItem> items;

	private String type;

	private String name;

	public List<VacantDictionaryItem> getItems() {
		return items;
	}

	public void setItems(List<VacantDictionaryItem> items) {
		this.items = items;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
