package vacant.demo.upload.attach;

public class Attach {

	private String id;
	private int size; // 文件大小，字节数
	private String path; // 相对路径，含文件名
	private String orig_name; // 原始文件名
	private String createTime; // 创建时间
	private String createUser; // 创建人

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOrig_name() {
		return orig_name;
	}

	public void setOrig_name(String orig_name) {
		this.orig_name = orig_name;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

}
