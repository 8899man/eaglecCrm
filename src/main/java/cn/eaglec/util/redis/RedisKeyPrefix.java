package cn.eaglec.util.redis;

public class RedisKeyPrefix {


	public static final String KEY_DELIMITER = ":";

	/** 用户登陆session数据. */
	public static final String LOGIN_SESSION = "login";
	
	/** 系统数据. */
	public static final String SYSTEM = "airc";
	
	/** 授权信息系统数据. */
	public static final String Authority = "authority";

	/** GDHS服务器session数据. */
	public static final String GDHS_SESSION = "gdhs";

	/** CDAG服务器session数据. */
	public static final String CDAG_SESSION = "cdag";

	/** CD运行状态数据. */
	public static final String CD_DATA = "cdData";
	
	/** RDTS服务器session数据. */
	public static final String RDTS_SESSION = "rdts";
	
	/** MDDS服务器session数据. */
	public static final String MDDS_SESSION = "mdds";


}
