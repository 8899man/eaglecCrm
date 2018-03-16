package cn.eaglec.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class MongoBaseDao implements Serializable{
	

	@Autowired
	private MongoDataAccess db;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * 保存一条数据
	 * @param t 泛型对象
	 * @param collectionName 需要查询的表名
	 */
	protected <T> void save(T t,String collectionName)
	{
		db.save(t, collectionName);
	}
    
	
	/**
	 * 批量保存数据
	 * @param list 泛型对象
	 * @param collectionName 需要查询的表名
	 */
	protected <T> void save(List<T> list,String collectionName)
	{
		db.save(list, collectionName);
	}
	
	/**
	 * 根据条件查询数据
	 * @param params 参数
	 * @param page 当前页数
	 * @param rows 每页数据条数
	 * @param sortName 排序名称
	 * @param sortOrder 排序类型("asc" or "desc")
	 * @param CollectionName 需要查询的表名
	 * @return List 返回list
	 */
	protected <T> List<T> findByQuery(Class<T> clazz,Map<String,Object> params,int page, int rows,String sortName,String sortOrder,String collectionName)
	{
		return db.findByQuery(clazz, params, page, rows, sortName, sortOrder, collectionName);
	}
    
    /**
     * 根据id查询一条数据
     * @param id 主键
     * @param collectionName 需要查询的表名
     * @return
     */
	protected <T> T getById(Class<T> clazz,String id,String collectionName)
	{
		return db.getById(clazz, id, collectionName);
	}
    
    /**
     * 根据条件查询一条数据
     * @param params 条件参数
     * @param sortName 排序名称
     * @param sortOrder 排序类型("asc" or "desc")
     * @param collectionName 需要查询的表名
     * @return
     */
	protected <T> T getByQuery(Class<T> clazz,Map<String, Object> params,String sortName,String sortOrder,String collectionName)
	{
		return db.getByQuery(clazz, params, sortName, sortOrder, collectionName);
	}
    
    /**
     * 根据条件统计数量
     * @param params 条件参数
     * @param collectionName 需要查询的表名
     * @return
     */
	protected Long CountByQuery(Map<String,Object> params,String collectionName)
	{
		return db.CountByQuery(params, collectionName);
	}
   
    /**
     * 更新一个对象  
     * @param t
     * @param collectionName
     */
	protected <T> void update(T t,String collectionName)
	{
		db.update(t, collectionName);
	}
    
    /**
     * 根据id删除一个对象
     * @param id
     * @param collectionName
     */
	protected void deleteById(String id,String collectionName)
	{
		db.deleteById(id, collectionName);
	}
    
    /**
     * 根据条件删除
     * @param params
     * @param collectionName
     */
	protected void deleteByQuery(Map<String, Object> params,String collectionName)
	{
		db.deleteByQuery(params, collectionName);
	}


}
