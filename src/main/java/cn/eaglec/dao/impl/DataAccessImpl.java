package cn.eaglec.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.eaglec.dao.DataAccess;

@Repository("DataAccessImpl")
public class DataAccessImpl implements DataAccess {


	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * 获得当前事物的session.
	 * 
	 * @return org.hibernate.Session
	 */
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public <T> Serializable save(T o) {
		if (o != null) {
			return getCurrentSession().save(o);
		}
		return null;
	}

	public <T> T getById(Class<T> c, Serializable id) {
		return (T) getCurrentSession().get(c, id);
	}

	public <T> T getByHql(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}
	public <T> List<T> getListByHql(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		List<T> l = q.list();
		return l;
	}

	public <T> T getByHql(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	
	public <T> void delete(T o) {
		if (o != null) {
			getCurrentSession().delete(o);
		}
	}

	 
	public <T> void update(T o) {
		if (o != null) {
			getCurrentSession().update(o);
		}
	}

	 
	public <T> void saveOrUpdate(T o) {
		if (o != null) {
			getCurrentSession().saveOrUpdate(o);
		}
	}

	 
	public <T> List<T> find(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		return q.list();
	}

	 
	public <T> List<T> find(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	 
	public <T> List<T> find(String hql, Map<String, Object> params, int page,
			int rows) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		if (page>=1) {
			 q.setFirstResult((page - 1) * rows);
		}
		if (rows>=1) {
			q.setMaxResults(rows);
		}
		return q.list();
	}

	 
	public <T> List<T> find(String hql, int page, int rows) {
		Query q = getCurrentSession().createQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	 
	public Long count(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		return (Long) q.uniqueResult();
	}

	 
	public Long count(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	 
	public int executeHql(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}

	 
	public int executeHql(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	 
	public List<Map> findBySql(String sql) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	 
	public <T> List<T> procedureCall(String sql) {

		return null;
	}

	 
	public List<Map> findBySql(String sql, int page, int rows) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	 
	public List<Map> findBySql(String sql, Map<String, Object> params) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	 
	public Map findSingleRowBySql(String sql, Map<String, Object> params)
	{
		Map map = null;
		List<Map> list = findBySql(sql,params);
		if (list==null || list.size()<=0)
		{
			return null;
		}
		map = list.get(0);
		return map;
	}

	 
	
	 
	 
	 
	public List<Map> findBySql(String sql, Map<String, Object> params,
			int page, int rows) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	 
	public int executeSql(String sql) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		return q.executeUpdate();
	}

	 
	public int executeSql(String sql, Map<String, Object> params) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	 
	public BigInteger countBySql(String sql) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		return (BigInteger) q.uniqueResult();
	}

	
	public BigInteger countBySql(String sql, Map<String, Object> params) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (BigInteger) q.uniqueResult();
	}

	 
	public <T> List<T> getListByHql(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
		return l;
	}
	
	public <T> List<T> getListByHqlAndListParam(String hql,Map<String, Object> params, Map<String, List<String>> param) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		if (param != null && !param.isEmpty()) {
			for (String key : param.keySet()) {
				q.setParameterList(key, param.get(key));
			}
		}
		List<T> l = q.list();
		return l;
	}

	
	public int getTotalSQLCount(String sql, Map paramMap)
	  {
	      SQLQuery query = getCurrentSession().createSQLQuery(sql);
	      if (paramMap != null) {
	         Iterator it = paramMap.keySet().iterator();
	        while (it.hasNext()) {
	          Object key = it.next();
	          setParam(query, key.toString(), paramMap.get(key));
	        }
	      }

	      long start = Calendar.getInstance().getTimeInMillis();
	       List list = query.list();
	       long end = Calendar.getInstance().getTimeInMillis();
	     //System.out.println("getTotalSQLCount SQL: " + sql);
	     //System.out.println("getTotalSQLCount SQL took time: " + (end - start) + "millsec");
	     int i =0;
	     if(null != list &&list.size() >=1)
	     {
	    	 i = Integer.parseInt(list.get(0).toString());
	     }
	      return i;
	  }
	
	 public int getTotalCount(String hql, Map paramMap)
	   {
	       Query query = getCurrentSession().createQuery(hql);
	       if (paramMap != null) {
	    	 Iterator it = paramMap.keySet().iterator();
	         while (it.hasNext()) {
	           Object key = it.next();
	           setParam(query, key.toString(), paramMap.get(key));
	         }
	       }
	       List list = query.list();
	       int i =0;
	       if(null != list &&list.size() >=1)
		     {
		    	 i = Integer.parseInt(list.get(0).toString());
		     }
		      return i;
	 
	   }
	
	  private void setParam(Query query, String key, Object value)
	  {
	     if ((value instanceof List))
	      query.setParameterList(key, (List)value);
	    else if (value.getClass().isArray())
	      query.setParameterList(key, (Object[])(Object[])value);
	    else
	       query.setParameter(key, value);
	  }
	  
	  protected Long getTotalCount(String hql){
		    Query query = getCurrentSession().createQuery(hql);
			return (Long) query.uniqueResult();
	}

	   
	
	   
	public <T> int deleteById(Class<T> clazz,String id){
		T be = this.getById(clazz, id);
		if(be != null){
			this.delete(be);
			return 1;
		}
		return 0;
	}

	 
	public <T> List<T> find(String hql, Map<String, List<String>> param1,
			Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if (param1 != null && !param1.isEmpty()) {
			for (String key : param1.keySet()) {
				q.setParameterList(key, param1.get(key));
			}
		}
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}
	
	public <T> List<T> findBySql(String sql, Map<String, List<String>> param1,
			Map<String, Object> params) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		if (param1 != null && !param1.isEmpty()) {
			for (String key : param1.keySet()) {
				q.setParameterList(key, param1.get(key));
			}
		}
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

}
