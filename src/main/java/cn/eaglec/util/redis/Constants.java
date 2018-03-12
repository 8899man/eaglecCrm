package cn.eaglec.util.redis;

public class Constants {
	/**
	 * 所在server服务器
	 */
	public static String serverName = "cdag";
	/**
	 * session cookie of key
	 */
	public static String session_key_flag_of_cookie = "session_key";
	
	public static String webserverName = "bms";
	
	/** 
	 * 4194304/1024/1024 = 4
	 * 限制上传的图片最大的不能超过4M
	 * 
	 * **/
	public  static int  UPLOAD_IMAGE_MAX = 4194304; 
	
	/** 设备图片最大容量不能超过10M  **/
	public  static int  UPLOAD_DEVICE_IMAGE_MAX = 10485760;
	
	/** 工程人员管理默认角色  **/
	public  static String  ROLE_ID = "f305a490-cdf1-426f-9c2a-5144d002bc3b";
	
	/** 工程人员管理默认角色  **/
	public  static String  BEARING_TEMPERATURE = "轴承温度";
	
	/** 工程人员管理默认角色  **/
	public  static String  MAIN_TENANCE_TIME = "保养时间";
	
	/** 工程人员管理默认角色  **/
	public  static String  RUN_TIME = "运行时间";
	
	/** 工程人员管理默认角色  **/
	public  static String  TEMPERATURE = "排气温度";
	
}
