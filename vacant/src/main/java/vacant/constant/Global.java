package vacant.constant;

public class Global {

	public static final String SESSION_ATTRIBUTE_USER = "user";
	public static final String REQUEST_ATTRIBUTE_TREE_DATA = "treeData";
	
	// 系统参数
	// 是否记录用户操作日志
	public static final String IS_LOG_USER_OPERATION = YesOrNo.YES;
	// 是否使用缓存，如果是，则在spring父容器初始化完成后，
	// 将字典、地区代码、资源等信息加载到相应的service类中，
	// 以后所有相关操作都从缓存中读取；否则，直接从数据库中
	// 读取
	public static final String IS_USE_CACHE = YesOrNo.NO;

}
