package cn.eaglec.service.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.eaglec.dao.DataAccess;
import cn.eaglec.service.BaseService;
@Service
public class BaseServiceImpl implements BaseService {

	@Autowired 
	protected DataAccess db;
	 
	public <T> void save(T t) {
		db.save(t);
	}

	 
	public <T> void update(T t) {
		db.update(t);
	}

	 
	public <T> void delete(T t) {
		db.delete(t);
	}

	 
	public <T> void saveOrUpdate(T o) {
		db.	saveOrUpdate(o);
	}

	 
	public <T> T getById(Class<T> c, Serializable id) {
		return db.getById(c, id);
	}

	 
	public <T> T getByHql(String hql) {
		return db.getByHql(hql);
	}

	 
	public <T> List<T> getListByHql(String hql) {
		return db.getListByHql(hql);
	}

	 
	public <T> List<T> getListByHql(String hql, Map<String, Object> params) {
		return db.getListByHql(hql,  params);
	}

	 
	public <T> T getByHql(String hql, Map<String, Object> params) {
		return db.getByHql( hql,params);
	}

	 
	public <T> List<T> find(String hql) {
		return db.find(hql);
	}

	 
	public <T> List<T> find(String hql, Map<String, Object> params) {
		return db.find(hql,params);
	}

	 
	public <T> List<T> find(String hql, int page, int rows) {
		return db.find( hql,  page,  rows);
	}

	 
	public <T> List<T> find(String hql, Map<String, Object> params, int page,int rows) {
		return db.find( hql, params,  page, rows);
	}

	 
	public Long count(String hql) {
		return db.count(hql);
	}

	 
	public Long count(String hql, Map<String, Object> params) {
		return db.count( hql,  params);
	}

	 
	public int executeHql(String hql) {
		return db.executeHql(hql);
	}

	 
	public int executeHql(String hql, Map<String, Object> params) {
		return db.executeHql(hql,  params);
	}

	 
	public List<Map> findBySql(String sql) {
		return db.findBySql(sql);
	}

	 
	public <T> List<T> procedureCall(String sql) {
		return db.procedureCall(sql);
	}

	 
	public List<Map> findBySql(String sql, int page, int rows) {
		return db.findBySql( sql,  page,  rows);
	}

	 
	public List<Map> findBySql(String sql, Map<String, Object> params) {
		return db.findBySql( sql,  params);
	}

	 
	 

	 

	 
	public List<Map> findBySql(String sql, Map<String, Object> params,
			int page, int rows) {
		return db.findBySql( sql, params, page,  rows);
	}

	 
	public int executeSql(String sql) {
		return db.executeSql(sql);
	}

	 
	public int executeSql(String sql, Map<String, Object> params) {
		return db.executeSql(sql,  params);
	}

	 
	public BigInteger countBySql(String sql) {
		return db.countBySql(sql);
	}

	 

	 
	public int getTotalSQLCount(String sql, Map paramMap) {
		return db.getTotalSQLCount(sql,paramMap);
	}

	 
	public int getTotalCount(String hql, Map paramMap) {
		return db.getTotalCount( hql,  paramMap);
	}

	
	 
	public <T> int deleteById(Class<T> clazz,String id)
	{
		return db.deleteById(clazz, id);
	}


	public BigInteger countBySql(String sql, Map<String, Object> params) {
		return db.countBySql(sql,  params);
	}



}
