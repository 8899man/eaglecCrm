package cn.eaglec.dao;

import cn.eaglec.domain.Infomation;
import cn.eaglec.domain.User;



public interface UserDao {

	/***
	 * 根据用户名和密码查找用户
	 * @param userName
	 * @param password
	 * @return
	 */
	public User findUserById(String userName,String password);
	
	/***
	 * 保存反馈信息
	 * @param userName
	 * @param password
	 * @return
	 */
	public void saveFeedBack(Infomation infomation);
}
