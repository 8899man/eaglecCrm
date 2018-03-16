package cn.eaglec.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import cn.eaglec.service.InitSystemService;
import cn.eaglec.util.ApplicationUtil;
import cn.eaglec.util.ExceptionUtil;
import cn.eaglec.util.FileListenerUtil;
import cn.eaglec.util.FileTool;
import cn.eaglec.util.MongoDBUtils;
import cn.eaglec.util.PropertiesUtils;


public class ConfigLoadListener implements ServletContextListener {



	private static Logger logger = Logger.getLogger(ConfigLoadListener.class);
	
	private static PropertyPlaceholderConfigurer cfg = new PropertyPlaceholderConfigurer();
	private static String fileName = "config.properties";
	private static Properties properties;
	private static Properties config ;
	private static boolean isInit = false;
	
	static {
		try {
			//初始化系统
			initSystem();
			//启动monogDB
			startMongoDb();
			properties = new Properties();
			config = new Properties();
			properties.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(fileName));
			
			isInit = true;
		} catch (Exception ex) {
			
			isInit = false;
			
			logger.error("ConfigLoadListener构造方法执行发生错误：" + "\n"
					+ ExceptionUtil.getStackMsg(ex));
		}
	}
	
	public void contextInitialized(ServletContextEvent sce) {
		
		if (!isInit) {
			return;
		}
		
		/*try {
			String path = (String) properties.get("conf.dir");
			String developPath = (String) properties.get("conf.dir.develop");
			logger.info("配置文件所在路径" + path);

			File dirname = new File(path);
			if (!dirname.isDirectory()) { // 目录不存在
				dirname = new File(developPath);
				if (!dirname.isDirectory()) {
					return;
				} else {
					path = developPath;
				}
			}

			List<File> fileList = FileTool.getinstance().getListFiles(path);
			for (File file : fileList) {
				if (file.getName().endsWith(".properties")) {
					readFileByLines(file);
					logger.info("");
				}
			}

			PropertiesUtils.setProperties(config);
			PropertiesUtils.getProperties().setProperty("protocolJsVersion",
					new Date().getTime() + "");
			// 自动刷新配置
			autoReloadConfig(path);
		} catch (Exception ex) {
			logger.error("contextInitialized方法执行发生错误：" + "\n"
					+ ExceptionUtil.getStackMsg(ex));
		}*/
	}

	public void contextDestroyed(ServletContextEvent sce) {
		if (!isInit)
		{
			return;
		}
	}
	
	/*
	 * 读取配置
	 */
	private void readFileByLines(File file)
	{
		try {
			doReadFileByLines(file);
		} catch (Exception ex) {
			logger.error("readFileByLines方法执行发生错误：" + "\n"
					+ ExceptionUtil.getStackMsg(ex));
		}
	}
	
	/**
	 * 读取配置
	 * @param file
	 */
	private void doReadFileByLines(File file) {
		if (!isInit) {
			return;
		}

		logger.info("配置文件名：" + file.getName());
		BufferedReader reader = null;
		InputStreamReader isr = null;
		try {
			 isr = new InputStreamReader(new FileInputStream(
					file), "UTF-8");
			reader = new BufferedReader(isr);
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束

			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				tempString = tempString.trim();
				if (tempString.equals("") || tempString.startsWith("#")) {
					continue;
				}

				int index = tempString.indexOf("=");
				if (index == -1) {
					continue;
				}

				String key = tempString.substring(0, index).trim();
				String value = tempString.substring(index + 1,
						tempString.length()).trim();
//				logger.info(key + "=" + value);

				// 将配置项写入JVM系统变量，使spring配置文件可以使用占位符
				System.getProperties().setProperty(key, value);

				config.put(key, value);

				line++;
			}
		} catch (IOException e) {
			logger.error("读取文件错误", e);
		} finally {
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(isr);
		}
    }
	
	
	/*
	 * 监控配置文件目录，自动刷新配置
	 */
	private void autoReloadConfig(String dirPath)
	{
		try
		{
			doAutoReloadConfig(dirPath);
		}
		catch(Exception ex)
		{
			logger.error("autoReloadConfig方法执行发生错误：" + "\n"
					+ ExceptionUtil.getStackMsg(ex));
		}
	}
	
	/**
	 * 监控配置文件目录，自动刷新配置
	 * @param dirPath
	 */
	private void doAutoReloadConfig(String dirPath){
		if (!isInit) {
			return;
		}

		logger.info("启动自动刷新配置, 监控目录：" + dirPath);

		try {

			FileListenerUtil.runFileListenter(
					new FileAlterationListenerAdaptor() {
						/**
						 * 文件创建修改
						 */
						@SuppressWarnings("rawtypes")
						@Override
						public void onFileChange(File file) {

							long l = System.currentTimeMillis();
							String name = file.getName();

							try {
								if (name.endsWith(".properties")) {
									readFileByLines(file);
									PropertiesUtils.setProperties(config);
									logger.info("配置文件刷新成功！文件名:" + name);

								}

							} catch (Exception e) {
								logger.error("文件自动刷新失败！文件名：" + name, e);
							}
						}

					}, dirPath);

			PropertiesUtils.getProperties().setProperty("protocolJsVersion",
					new Date().getTime() + "");
			logger.info("自动刷新配置启动成功...........");

		} catch (Exception e) {
			logger.error("自动刷新配置启动失败...........", e);
		}
	}


	
	/*
	 * 初始化系统
	 */
	private static void initSystem()
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
		MongoDBUtils.createIndex(MongoDBConst.COLLECTION_HISTORY_TD_RUN_DATA);
	}
}
