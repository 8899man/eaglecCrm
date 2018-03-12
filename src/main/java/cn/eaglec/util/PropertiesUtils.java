package cn.eaglec.util;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;



public class PropertiesUtils {
	private static  PropertiesUtils singl = null;
	private static Properties properties;
	private static Logger logger = Logger.getLogger(PropertiesUtils.class);
	
    public static PropertiesUtils getInstenc(){
		try {
			return singl == null ? new PropertiesUtils() : singl;
		} catch (Exception ex) {
			logger.error("PropertiesUtils.getInstenc()方法执行过程发生错误："
					+ ExceptionUtil.getStackMsg(ex));
			return null;
		}
    }
    
    public static void setProperties(Properties properties){
		try {
			PropertiesUtils.properties = properties;
		} catch (Exception ex) {
			logger.error("PropertiesUtils.setProperties(Properties properties)方法执行过程发生错误："
					+ ExceptionUtil.getStackMsg(ex));
		}
    }
    
    public static Properties getProperties(){
		try {
			return PropertiesUtils.properties;
		} catch (Exception ex) {
			logger.error("PropertiesUtils.getProperties()方法执行过程发生错误："
					+ ExceptionUtil.getStackMsg(ex));
			return null;
		}
    }
	
    public static String getProperty(String key){
		if (key == null) {
			key = "";
		}
		try {
			return properties.getProperty(key);
		} catch (Exception ex) {
			logger.error("PropertiesUtils.getProperty(String key)方法执行过程发生错误："
					+ "[key=" + key + "]" + ExceptionUtil.getStackMsg(ex));
			return null;
		}
    }

    public static String getProperty(String key,String defaultValue){
		if (key == null) {
			key = "";
		}
		try {
			if (StringUtils.isBlank(properties.getProperty(key))) {
				return defaultValue;
			}
			return properties.getProperty(key);
		} catch (Exception ex) {
			String dv = "";
			if (defaultValue == null) {
				dv = "null";
			} else {
				dv = defaultValue;
			}
			logger.error("PropertiesUtils.getProperty(String key,String defaultValue)方法执行过程发生错误："
					+ "[key=" + key + "]" + "[defaultValue=" + dv + "]"
					+ ExceptionUtil.getStackMsg(ex));
			return defaultValue;
		}
    }
}
