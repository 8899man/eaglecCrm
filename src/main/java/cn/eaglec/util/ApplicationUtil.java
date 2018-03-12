/**
 * Project Name:bms
 * File Name:ApplicationUtil.java
 * Package Name:cn.inovance.iotgp.bms.web.util
 * Date:2014-4-3下午6:16:25
 * Copyright (c) 2014, Shenzhen Inovance technology Co., Ltd  All Rights Reserved.
 *
 */

package cn.eaglec.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

/**
 * ClassName:ApplicationUtil. <br/>
 * Function: 获取 ApplicationContext <br/>
 * Date: 2014-4-3 下午6:16:25 <br/>
 */
public class ApplicationUtil {

	private static Log logger = LogFactory.getLog(ApplicationUtil.class);

	private static ApplicationContext applicationContext;

	public static void setApplicationContext(
			ApplicationContext applicationContext) {
		synchronized (ApplicationUtil.class) {
			logger.debug("setApplicationContext, notifyAll");
			ApplicationUtil.applicationContext = applicationContext;
			ApplicationUtil.class.notifyAll();
		}
	}

	public static ApplicationContext getApplicationContext() {
		synchronized (ApplicationUtil.class) {
			while (applicationContext == null) {
				try {
					logger.debug("getApplicationContext, wait...");
					ApplicationUtil.class.wait(60000);
					if (applicationContext == null) {
						logger.warn(
								"Have been waiting for ApplicationContext to be set for 1 minute",
								new Exception());
					}
				} catch (InterruptedException ex) {
					logger.debug("getApplicationContext, wait interrupted");
				}
			}
			return applicationContext;
		}
	}

	/**
	 * 这是一个便利的方法，帮助我们快速得到一个BEAN
	 * 
	 * @param beanName
	 *            bean的名字
	 * @return 返回一个bean对象
	 */
	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	public static Object getBusinessBean(String beanName) {
		String business = PropertiesUtils.getProperty("business", "");
		Object t = null;
		if (StringUtils.isNotBlank(business)
				&& StringUtils.isNotBlank(beanName)) {
			try {
				t = applicationContext.getBean(business
						+ (beanName.charAt(0) + "").toUpperCase()
						+ beanName.substring(1));
				
			} catch (Exception e) {
//				e.printStackTrace();
				t = applicationContext.getBean(beanName);
			}
		}
		if (t == null) {
			t = applicationContext.getBean(beanName);
		}
		return t;

	}
}
