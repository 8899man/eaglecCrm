package cn.eaglec.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.eaglec.dao.UserDao;
import cn.eaglec.service.InitSystemService;

public class InitSystemServiceImpl implements InitSystemService {

	private static final Logger logger = Logger
			.getLogger(InitSystemServiceImpl.class);
	
	@Autowired
	public UserDao userDao;
	
	public void initSystem() {
		try {
			logger.info("开始初始化系统");
			//初始化系统参数函数
		} catch (Exception e) {
			logger.error("初始化系统-失败", e);
		}

	}

}
