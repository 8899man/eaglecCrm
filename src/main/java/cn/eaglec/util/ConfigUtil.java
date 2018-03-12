package cn.eaglec.util;


public class ConfigUtil {

	
	/**
	 * 获得sessionInfo名字
	 * 
	 * @return
	 */
	public static final String getSessionInfoName() {
		return PropertiesUtils.getInstenc().getProperty("sessionInfoName");
	}
	
	/**
	 * 通过键获取值
	 * 
	 * @param key
	 * @return
	 */
	public static final String get(String key) {
		return PropertiesUtils.getInstenc().getProperty(key);
	}



}
