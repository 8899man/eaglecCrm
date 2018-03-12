package cn.eaglec.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class BaseDao implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	protected DataAccess db;
	
	/**
	 * 获得当前事物的session.
	 * 
	 * @return org.hibernate.Session
	 */
	protected Session getCurrentSession()
	{
		return db.getCurrentSession();
	}

	/**
	 * 保存一个对象.
	 * @param <T>
	 * 
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	public <T> Serializable save(T o)
	{
		return db.save(o);
	}

	/**
	 * 删除一个对象.
	 * @param <T>
	 * 
	 * @param o
	 *            对象
	 */
	public <T> void delete(T o)
	{
		db.save(o);
	}

	/**
	 * 更新一个对象.
	 * @param <T>
	 * 
	 * @param o
	 *            对象
	 */
	public <T> void update(T o)
	{
		 db.update(o);
	}

	/**
	 * 保存或更新一个对象.
	 * @param <T>
	 * 
	 * @param o
	 *            对象
	 */
	public <T> void saveOrUpdate(T o)
	{
		db.saveOrUpdate(o);
	}

	/**
	 * 通过主键获得对象.
	 * @param <T>
	 * 
	 * @param c
	 *            类名.class
	 * @param id
	 *            主键
	 * @return 对象
	 */
	public <T> T getById(Class<T> c, Serializable id)
	{
		return db.getById(c, id);
	}

	/**
	 * 通过HQL语句获取一个对象.
	 * @param <T>
	 * 
	 * @param hql
	 *            HQL语句
	 * @return 对象
	 */
	public <T> T getByHql(String hql)
	{
		return db.getByHql(hql);
	}
	
	/**
	 * 通过HQL语句获取一个对象.
	 * @param <T>
	 * 
	 * @param hql
	 *            HQL语句
	 * @return 对象集合
	 */
	public <T> List<T> getListByHql(String hql)
	{
		return db.getListByHql(hql);
	}
	/**
	 * 通过HQL语句获取一个对象.
	 * @param <T>
	 * 
	 * @param hql
	 *            HQL语句
	 * @return 对象集合
	 */
	
	public <T> List<T> getListByHql(String hql,Map<String, Object> params)
	{
		return db.getListByHql(hql,params);
	}

	/**
	 * 通过HQL语句获取一个对象.
	 * @param <T>
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return 对象
	 */
	public <T> T getByHql(String hql, Map<String, Object> params)
	{
		return db.getByHql(hql, params);
	}

	/**
	 * 获得对象列表.
	 * @param <T>
	 * 
	 * @param hql
	 *            HQL语句
	 * @return List
	 */
	public <T> List<T> find(String hql)
	{
		return db.find(hql);
	}

	/**
	 * 获得对象列表.
	 * @param <T>
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return List
	 */
	public <T> List<T> find(String hql, Map<String, Object> params)
	{
		return db. find(hql, params);
	}

	/**
	 * 获得分页后的对象列表.
	 * @param <T>
	 * 
	 * @param hql
	 *            HQL语句
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return List
	 */
	public <T> List<T> find(String hql, int page, int rows)
	{
		return db.find(hql,  page,  rows);
	}

	/**
	 * 获得分页后的对象列表.
	 * @param <T>
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return List
	 */
	public <T> List<T> find(String hql, Map<String, Object> params, int page,int rows)
	{
		return db.find( hql,  params,  page, rows);
	}

	/**
	 * 统计数目.
	 * 
	 * @param hql
	 *            HQL语句(select count(*) from T)
	 * @return long
	 */
	public Long count(String hql)
	{
		return db.count(hql);
	}

	/**
	 * 统计数目.
	 * 
	 * @param hql
	 *            HQL语句(select count(*) from T where xx = :xx)
	 * @param params
	 *            参数
	 * @return long
	 */
	public Long count(String hql, Map<String, Object> params)
	{
		return db.count(hql, params);
	}

	/**
	 * 执行一条HQL语句.
	 * 
	 * @param hql
	 *            HQL语句
	 * @return 响应结果数目
	 */
	public int executeHql(String hql)
	{
		return db.executeHql(hql);
	}

	/**
	 * 执行一条HQL语句.
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return 响应结果数目
	 */
	public int executeHql(String hql, Map<String, Object> params)
	{
		return db.executeHql(hql, params);
	}

	/**
	 * 获得结果集.
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 结果集
	 */
	public List<Map> findBySql(String sql)
	{
		return db.findBySql(sql);
	}

	/**
	 * 获得结果集.
	 * @param <T>
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 结果集
	 */
	public <T> List<T> procedureCall(String sql)
	{
		return db.procedureCall(sql);
	}

	/**
	 * 获得结果集.
	 * 
	 * @param sql
	 *            SQL语句
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return 结果集
	 */
	public List<Map> findBySql(String sql, int page, int rows)
	{
		return db.findBySql( sql,  page,  rows);
	}

	/**
	 * 获得结果集.
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 结果集
	 */
	public List<Map> findBySql(String sql, Map<String, Object> params)
	{
		return db.findBySql( sql,  params);
	}
	
	
	
	
	
	
	

	/**
	 * 获得结果集.
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return 结果集
	 */
	public List<Map> findBySql(String sql, Map<String, Object> params,
			int page, int rows) {
		return db.findBySql(sql, params, page, rows);
	}

	/**
	 * 执行SQL语句.
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 响应行数
	 */
	public int executeSql(String sql)
	{
		return db.executeSql(sql);
	}

	/**
	 * 执行SQL语句.
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 响应行数
	 */
	public int executeSql(String sql, Map<String, Object> params)
	{
		return db.executeSql(sql, params);
	}

	/**
	 * 统计.
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 数目
	 */
	public BigInteger countBySql(String sql)
	{
		return db.countBySql(sql);
	}

	/**
	 * 统计.
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 数目
	 */
	public BigInteger countBySql(String sql, Map<String, Object> params)
	{
		return db.countBySql(sql, params);
	}
	
	/**
	 * 统计.
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 数目
	 */
	public int getTotalSQLCount(String sql, Map paramMap)
	{
		return db.getTotalSQLCount(sql,  paramMap);
	}
	
	/**
	 * 统计.
	 * 
	 * @param hql
	 *            Hql语句
	 * @param params
	 *            参数
	 * @return 数目
	 */
	public int getTotalCount(String hql, Map paramMap)
	{
		return db.getTotalCount(hql, paramMap);
	}
	
	protected <T> T listToOne(List<T> list){
		if(list != null && list.isEmpty() == false){
			return list.get(0);
		}
		return null;
	}
	
	public <T>  List<T> executeQueryHQL(String hql,Map<String, Object> params){
		return db.find(hql, params);
	}
	
	public <T> List<T> executeQueryHQL(String hql, Map<String, List<String>> param1,
			Map<String, Object> params) {
		return db.find(hql, param1, params);
	}
	
	protected <T>  List<T> executeQueryHQL(String hql){
		return executeQueryHQL(hql, null);
	}
	
	public String arrayToString(String[] target){
		if(target == null || target.length == 0){
			return "''";
		}
		StringBuffer result = new StringBuffer();
		for(String str : target){
			result.append("'"+str.trim()+"'").append(",");
		}
		return result.toString().substring(0,result.length()-1);
	}
	
	public String listToString(List<String> list){
		if(list == null || list.size()==0){
			return "''";
		}
		StringBuffer result = new StringBuffer();
		for(String str : list){
			result.append("'"+str.trim()+"'").append(",");
		}
		return result.toString().substring(0,result.length()-1);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T findById(Class<T> clazz,String id){
		Object object = this.getCurrentSession().get(clazz, id);
		return object == null ? null : (T)object;
	}
	
	
	protected String GetMapValue(Map map,String key)
	{
		if (!map.containsKey(key))
		{
			return "";
		}
		if (map.get(key)==null)
		{
		   return "";
		}
		return map.get(key).toString();
	}
	
	public Map findSingleRowBySql(String sql, Map<String, Object> params)
	{
		return db.findSingleRowBySql(sql, params);
	}


}
