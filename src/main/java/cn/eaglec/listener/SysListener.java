package cn.eaglec.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import cn.eaglec.service.InitSystemService;
import cn.eaglec.util.ApplicationUtil;
import cn.eaglec.util.ExceptionUtil;
import cn.eaglec.util.MongoDBConst;
import cn.eaglec.util.MongoDBUtils;
import cn.eaglec.util.PropertiesUtils;

public class SysListener implements ServletContextListener{
	
	private static final Logger logger = Logger.getLogger(SysListener.class);

	public void contextInitialized(ServletContextEvent sce) {

		try {
			// 初始化系统
			initSystem();
			
			startMongoDb();
			
			
		} catch (Exception ex) {
			logger.error("contextInitialized方法执行发生错误：" + "\n"
					+ ExceptionUtil.getStackMsg(ex));
		}
		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
		
	}

	/*
	 * 初始化系统
	 */
	private void initSystem()
	{
		try {
			InitSystemService initService = (InitSystemService) ApplicationUtil
					.getApplicationContext().getBean("initService");
			initService.initSystem();
		} catch (Exception ex) {
			logger.error("initSystem方法执行发生错误：" + "\n"
					+ ExceptionUtil.getStackMsg(ex));
		}
	}
	
	
	private static void startMongoDb(){
		String host = PropertiesUtils.getProperty("mongodb.host");
		int port = Integer.parseInt(PropertiesUtils.getProperty("mongodb.port","27017"));
		String dbname = PropertiesUtils.getProperty("mongodb.dbname");
		String userName = PropertiesUtils.getProperty("mongodb.username");
		String password = PropertiesUtils.getProperty("mongodb.password");
		MongoDBUtils.connectMongoDB(host, port, dbname,userName,password);
		MongoDBUtils.createIndex(MongoDBConst.COLLECTION_CRM);
	}
}
