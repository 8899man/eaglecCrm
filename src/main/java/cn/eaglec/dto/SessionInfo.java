package cn.eaglec.dto;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.eaglec.domain.User;


/**
 * sessionInfo模型，只要登录成功，就需要设置到session里面，便于系统使用.
 */
public class SessionInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;

	private Map<String,String> allActions;
	
	/** 权限验证----方法级别验证的原始数据**/
	private Set<String> validatorMethod = new HashSet<String>();
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Map<String,String> getAllActions() {
		return allActions;
	}

	public void setAllActions(Map<String,String> allActions) {
		this.allActions = allActions;
	}

	public Set<String> getValidatorMethod() {
		return validatorMethod;
	}

	public void setValidatorMethod(Set<String> validatorMethod) {
		this.validatorMethod = validatorMethod;
	}
	
	
}
