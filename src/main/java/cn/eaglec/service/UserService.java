package cn.eaglec.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.eaglec.domain.Infomation;
import cn.eaglec.domain.User;

public interface UserService {
	
	/***
	 * 用户登录
	 * @param userName
	 * @param password
	 * @return
	 */
	public User userLogin(String userName, String password,HttpServletRequest request,
			HttpServletResponse response);
	
	/***
	 * 保存客户的反馈信息
	 * @param Infomation
	 * @return
	 */
	public void saveFeedBack(Infomation info,HttpServletRequest request,
			HttpServletResponse response);

}
