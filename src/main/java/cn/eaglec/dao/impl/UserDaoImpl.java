package cn.eaglec.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.eaglec.dao.BaseDao;
import cn.eaglec.dao.DataAccess;
import cn.eaglec.dao.UserDao;
import cn.eaglec.domain.Infomation;
import cn.eaglec.domain.User;

@Repository(value="userDaoImpl")
public class UserDaoImpl extends BaseDao  implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	private static final long serialVersionUID = 1L;
	@Autowired
	private DataAccess dao;
	
	public User findUserById(String userName, String password) {

		Map<String, Object> params = new HashMap<String,Object>();
		params.put("userName", userName);
		params.put("password", password);
		List<User> list = this.executeQueryHQL("from User u where u.userName =:userName and u.password =:password", params);
		return this.listToOne(list);
	
	}

	public void saveFeedBack(Infomation infomation) {
		dao.save(infomation);
	}

}
