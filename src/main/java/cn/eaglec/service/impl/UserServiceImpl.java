package cn.eaglec.service.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.eaglec.dao.UserDao;
import cn.eaglec.domain.Infomation;
import cn.eaglec.domain.User;
import cn.eaglec.service.UserService;


@Service
public class UserServiceImpl implements UserService {

	@Autowired private UserDao userDao;
	
	public User userLogin(String userName, String password,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		User user = userDao.findUserById(userName, password);
		return user;
	}

	public void saveFeedBack(Infomation info, HttpServletRequest request,
			HttpServletResponse response) {
		
		userDao.saveFeedBack(info);
		
	}
	
}
