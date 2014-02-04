package vacant.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class WriteOffable extends AbstractDomain {
	
	/** 是否已注销 */
	@Column(name="is_written_off")
	private String isWrittenOff;
	
	/** 注销日期 */
	@Column(name="written_off_date")
	private String writtenOffDate;
	
	/** 注销原因 */
	@Column(name="written_off_reason")
	private String writtenOffReason;

	public String getIsWrittenOff() {
		return isWrittenOff;
	}

	public void setIsWrittenOff(String isWrittenOff) {
		this.isWrittenOff = isWrittenOff;
	}

	public String getWrittenOffDate() {
		return writtenOffDate;
	}

	public void setWrittenOffDate(String writtenOffDate) {
		this.writtenOffDate = writtenOffDate;
	}

	public String getWrittenOffReason() {
		return writtenOffReason;
	}

	public void setWrittenOffReason(String writtenOffReason) {
		this.writtenOffReason = writtenOffReason;
	}

}
