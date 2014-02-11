package vacant.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 字典项
 * @author zzq
 *
 */
@Entity
@Table(name = "vacant_dictionary_item")
public class VacantDictionaryItem extends BaseDomain {

	@Column(name = "dictionary_id")
	private String dictionaryId;

	private String code;

	private String value;

	public String getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(String dictionaryId) {
		this.dictionaryId = dictionaryId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
