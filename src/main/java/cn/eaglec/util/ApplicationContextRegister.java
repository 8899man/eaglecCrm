/**
 * Project Name:iotgp-cds
 * File Name:ApplicationContextRegister.java
 * Package Name:cn.inovance.iotgp.cds.web.util
 * Date:2014-5-14下午1:52:47
 * Copyright (c) 2014, Shenzhen Inovance technology Co., Ltd  All Rights Reserved.
 *
*/

package cn.eaglec.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ClassName:ApplicationContextRegister <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2014-5-14 下午1:52:47 <br/>
 * @author   z1979
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class ApplicationContextRegister implements ApplicationContextAware {

	 private Log log = LogFactory.getLog(getClass());
	  
	  public void setApplicationContext(ApplicationContext applicationContext)
	    throws BeansException {
		  ApplicationUtil.setApplicationContext(applicationContext);
		  log.debug("ApplicationContext registed");
	  }

}

