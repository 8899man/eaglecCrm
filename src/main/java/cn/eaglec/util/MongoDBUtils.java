package cn.eaglec.util;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class MongoDBUtils {
	
private static Logger logger = Logger.getLogger(MongoDBUtils.class);
	
	private static Mongo mongo ;

	private static String dbName;
	private static String userName;
	private static String password;
	
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 初始化MongoDB客户端
	 * MongoDB自主管理连接池，默认最大843个，和系统有关
	 * @param host
	 * @param port
	 * @param dbName
	 */
	public static void connectMongoDB(String host,int port,String dbName,String userName,String password){
		
		MongoDBUtils.dbName = dbName;
		MongoDBUtils.userName = userName;
		MongoDBUtils.password = password;
		try {
			
			mongo = new MongoClient(host, port);
			logger.info("连接MongoDB成功。"+host+":"+port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 关闭mongo连接，在web容器关闭时调用
	 */
	public static void close(){
		mongo.close();
	}
	

	/**
	 * 获取一个连接,指定表名
	 * @param collectionName
	 * @return
	 */
	public static DBCollection getDBCollection(String collectionName){
		DB db = mongo.getDB(dbName);
//		if(!db.isAuthenticated()){
//			boolean auth = db.authenticate(userName, password.toCharArray());
//			if(!auth){
//				logger.error("鉴权失败");
//				return null;
//			}
//		}
		DBCollection collection = db.getCollection(collectionName);
		return collection;
	}
	public static void createIndex(String collectionName) {
		DBCollection collection = getDBCollection(collectionName);
		BasicDBObject query = new BasicDBObject();
		query.put("updateDay", -1);
		query.put("deviceId", -1);
		collection.createIndex(query);
	}

}
