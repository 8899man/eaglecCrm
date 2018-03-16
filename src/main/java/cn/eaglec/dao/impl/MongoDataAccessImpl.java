package cn.eaglec.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import cn.eaglec.dao.MongoDataAccess;
import cn.eaglec.util.MongoDBUtils;

@Repository
public class MongoDataAccessImpl implements MongoDataAccess{
	


	
	private static Logger logger = Logger.getLogger(MongoDataAccessImpl.class);
	
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 保存一条数据
	 * @param t 泛型对象
	 * @param collectionName 需要查询的表名
	 */
	public <T> void save(T t, String collectionName) {
		DBCollection collection = MongoDBUtils.getDBCollection(collectionName);
		DBObject dbObject = this.BeanToDBObject(t);
		if(dbObject != null){
			collection.save(dbObject);
		}
	}

	/**
	 * 批量保存数据
	 * @param list 泛型对象
	 * @param collectionName 需要查询的表名
	 */
	public <T> void save(List<T> list, String collectionName) {
		DBCollection collection = MongoDBUtils.getDBCollection(collectionName);
		List<DBObject> dbObjectList = new ArrayList<DBObject>();
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(T t:list){ 
			DBObject dbObject = this.BeanToDBObject(t);
			if(dbObject != null){
				dbObjectList.add(dbObject);
			}
		}
		try{
			collection.insert(dbObjectList);
			logger.info("insert into mongodb");
		}catch(Exception e){
			logger.error("Mongno存储异常", e);
			collection = MongoDBUtils.getDBCollection(collectionName);
			collection.insert(dbObjectList);
		}
		
	}
	
	/**
	 * 通过条件查询所有数据
	 */
	public <T> List<T> findByQuery(Class<T> clazz,Map<String, Object> params,String collectionName) {
		DBCollection collection = MongoDBUtils.getDBCollection(collectionName);
		List<T> list = new ArrayList<T>();
		DBCursor cursor = null;
		
		if(params != null){
			BasicDBObject query = new BasicDBObject();
			Set<String> keySet = params.keySet();
	        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
	            String key = it.next();
	            Object value = params.get(key);
	            query.put(key, value);
	        }
	        cursor = collection.find(query);
		}else{
			cursor = collection.find();
		}
		
		while (cursor.hasNext()) {
	           
	           DBObject dbObject = cursor.next();
	           if(dbObject == null){
	        	   break;
	           }
	           T t = this.DBObjectToBean(dbObject,clazz);
	           
	           list.add(t);
	    }
		
		return list;
	}

	/**
	 * 通过条件查询数据
	 * @param params 参数
	 * @param page 当前页数
	 * @param rows 每页数据条数
	 * @param sortName 排序名称
	 * @param sortOrder 排序("asc" or "desc")
	 * @param CollectionName 需要查询的表名
	 * @return List 返回list
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findByQuery(Class<T> clazz,Map<String, Object> params, int page, int rows,
			String sortName, String sortOrder,String collectionName) {
		DBCollection collection = MongoDBUtils.getDBCollection(collectionName);
		collection.createIndex(new BasicDBObject("createTime", -1));
		List<T> list = new ArrayList<T>();

		DBCursor cursor = null;
		if(params != null){
			BasicDBObject query = new BasicDBObject();
			Set<String> keySet = params.keySet();
	        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
	            String key = it.next();
	            Object value = params.get(key);
	            query.put(key, value);
	        }
	        cursor = collection.find(query);
		}else{
			cursor = collection.find();
		}
		
		
        //执行查询，先排序(类mysql orderby再建一个BasicDBObject即可，1：asc,－1:desc) 
        //后分页
        int begin = (page-1)*rows;
        if(StringUtils.isNotBlank(sortName)&&StringUtils.isNotBlank(sortOrder)){
        	if(sortOrder.equals("desc")){
        		cursor.sort(new BasicDBObject(sortName,-1));
        	}else{
        		cursor.sort(new BasicDBObject(sortName,1));
        	}
        }
		cursor.skip(begin).limit(rows);

		while (cursor.hasNext()) {
           
           DBObject dbObject = cursor.next();
           if(dbObject == null){
        	   break;
           }
           T t = this.DBObjectToBean(dbObject,clazz);
           
           list.add(t);
        }
		
		return list;
	}

	
	
	
	
	
	
	/**
     * 通过id查询一条数据
     * @param id 主键
     * @param collectionName 需要查询的表名
     * @return
     */
	public <T> T getById(Class<T> clazz,String id, String collectionName) {
		if(id == null){
			return null;
		}
		DBCollection collection = MongoDBUtils.getDBCollection(collectionName);
		BasicDBObject query = new BasicDBObject();
		query.put("id", id);
		DBObject dbObject = collection.findOne(query);
		
		if(dbObject == null){
			return null;
		}
		Object object = this.DBObjectToBean(dbObject,clazz);

		return (T) object;
	}

	/**
     * 通过id查询一条数据
     * @param id 主键
     * @param collectionName 需要查询的表名
     * @return
     */
	public <T> T getByObjectId(Class<T> clazz,String objectId, String collectionName) {
		if(objectId == null){
			return null;
		}
		DBCollection collection = MongoDBUtils.getDBCollection(collectionName);
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(objectId));
		DBObject dbObject = collection.findOne(query);
		
		if(dbObject == null){
			return null;
		}
		Object object = this.DBObjectToBean(dbObject,clazz);

		return (T) object;
	}
	
	/**
     * 根据条件查询一条数据
     * @param params 条件参数
     * @param sortName 排序名称
     * @param sortOrder 排序类型("asc" or "desc")
     * @param collectionName 需要查询的表名
     * @return
     */
    public <T> T getByQuery(Class<T> clazz,Map<String, Object> params,String sortName,String sortOrder,String collectionName){
		DBCollection collection = MongoDBUtils.getDBCollection(collectionName);
		BasicDBObject query = new BasicDBObject();

//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Set<String> keySet = params.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
            String key = it.next();
            Object value = params.get(key);
            query.put(key, value);
        }

        DBCursor cursor = collection.find(query);
        if(StringUtils.isNotBlank(sortName)&&StringUtils.isNotBlank(sortOrder)){
        	if(sortOrder.equals("desc")){
        		cursor.sort(new BasicDBObject(sortName,-1));
        	}else{
        		cursor.sort(new BasicDBObject(sortName,1));
        	}
        }
		cursor.skip(0).limit(1);
		T t = null;
		while(cursor.hasNext()){
			DBObject dbObject = cursor.next();
        	if(dbObject == null){
         	   break;
            }
        	t = this.DBObjectToBean(dbObject,clazz);
	        break;
		}
		
		return t;
    }
	
	/**
     * 通过条件统计数量
     * @param params map
     * @param collectionName 需要查询的表名
     * @return
     */
	public Long CountByQuery(Map<String, Object> params, String collectionName) {
		DBCollection collection = MongoDBUtils.getDBCollection(collectionName);
		BasicDBObject query = new BasicDBObject();
		long count = 0;
		if(params != null){
			Set<String> keySet = params.keySet();
	        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
	            String key = it.next();
	            Object value = params.get(key);
	            query.put(key, value);
	        }
	        count = collection.count(query);
		}else{
			count = collection.count();
		}
		
        return count;
	}
	
	

	/**
     * 更新一个对象  
     * @param t
     * @param collectionName
     */
	public <T> void update(T t, String collectionName) {
		
		DBCollection collection = MongoDBUtils.getDBCollection(collectionName);
		DBObject dbObject = this.BeanToDBObject(t);
		
		DBObject query=new BasicDBObject();
		query.put("_id",new ObjectId(dbObject.get("id").toString()));
		
		dbObject.removeField("_id");//去掉_id字段
		if(dbObject != null){
			WriteResult result= collection.update(query,dbObject);
	        //获取上次操作结果是否有错误.
//	        if(result.isUpdateOfExisting()){
//	        	
//	        }else{
//	        	logger.error("更新失败:"+dbObject.get("id").toString());
//	        }
		}
	}

	
	/**
     * 根据id删除一个对象
     * @param id
     * @param collectionName
     */
	public void deleteById(String id, String collectionName) {
		DBCollection collection = MongoDBUtils.getDBCollection(collectionName);

		if(StringUtils.isNotBlank(id)){
			BasicDBObject query = new BasicDBObject();
			
			query.put("_id", new ObjectId(id));
	        
	        WriteResult result= collection.remove(query);
	        //获取上次操作结果是否有错误.
//	        if(result.isUpdateOfExisting()){
//	        	
//	        }else{
//	        	logger.error("删除失败:"+id);
//	        }
		}else{
			
		}
		
	}

	
	/**
     * 根据条件删除
     * @param params
     * @param collectionName
     */
	public void deleteByQuery(Map<String, Object> params,String collectionName) {
		DBCollection collection = MongoDBUtils.getDBCollection(collectionName);

		if(params != null){
			BasicDBObject query = new BasicDBObject();
			Set<String> keySet = params.keySet();
	        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
	            String key = it.next();
	            Object value = params.get(key);
	            query.put(key, value);
	        }
	        WriteResult result= collection.remove(query);
	        //获取上次操作结果是否有错误.
//	        if(result.isUpdateOfExisting()){
//	        	
//	        }else{
//	        	logger.error("删除失败："+params);
//	        }
		}else{
			
		}
		
	}

	
	
	/**
	 * 将查询出来的DBObject转换为需要的对象Object
	 * @param dbObject
	 * @return bean
	 */
	public <T> T DBObjectToBean(DBObject dbObject,Class<T> clazz){
		
		T t = null;
		try{
			ObjectId i = (ObjectId)dbObject.get("_id");
			dbObject.put("id",i.toString());
			String str = JSON.toJSONString(dbObject);
			t = JSON.parseObject(str, clazz);
		}catch(Exception e){
			logger.error("DBObject转换为Object——异常",e);
		}
		return t;
		
	}
	
	
	/**
	 * 将对象Object转换为DBObject
	 * @param bean 数据对象
	 * @param dbObject 需要接收数据的DBObject对象
	 * @throws Exception
	 */
	public DBObject BeanToDBObject(Object bean){
		DBObject dbObject = null;
		try{
			
			String str = JSON.toJSONStringWithDateFormat(bean, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
			
			dbObject = (DBObject) com.mongodb.util.JSON.parse(str);
	
		}catch(Exception e){
			logger.error("Object转换为DBObject——异常",e);
		}
		return dbObject;
		
	}


}
