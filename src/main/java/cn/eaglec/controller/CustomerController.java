package cn.eaglec.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.eaglec.domain.Infomation;
import cn.eaglec.domain.User;
import cn.eaglec.dto.Json;
import cn.eaglec.dto.SessionInfo;
import cn.eaglec.service.UserService;
import cn.eaglec.util.BaseResult;
import cn.eaglec.util.MD5Util;
import cn.eaglec.util.Webutil;
import cn.eaglec.util.redis.SessionService;

@Controller
public class CustomerController extends BaseController {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	/**
	 * 所有的页面默认进入这里面过来
	 */
	@RequestMapping("/")
	public String index(Model model, String languageKey, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("index come in ==========");
		return "index";
	}
	/**
	 * 进入登录页面
	 * 
	 * @param resourceId
	 * @param model
	 */
	@RequestMapping(value="/sys/userLogin",method = RequestMethod.GET)
	public String userLogin(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		//model.addAttribute("userId", "ifno");
		//SessionService.getInstance().save("crm","nihao");
		//Webutil.setAttribute("crm","nihao");
		//String nihao = (String)SessionService.getInstance().get("crm");
		
		return "/login/login";
	}
	/**
	 * 进入客户投诉列表页面
	 * 
	 * @param resourceId
	 * @param model
	 */
	@RequestMapping(value="/sys/customerInfoList",method = RequestMethod.GET)
	public String apkVersionList(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userId", "ifnoMEMEDA");
		SessionService.getInstance().save("123","123");
		return "/client/index";
	}
	
	/**
	 * 进入用户列表页面
	 * 
	 * @param resourceId
	 * @param model
	 */
	@RequestMapping(value="/sys/userList",method = RequestMethod.GET)
	public String userList(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		model.addAttribute("userId", "userId");
		model.addAttribute("userName", userName);
		return "/client/userList";
	}
	
	/**
	 * 进入用户列表页面(微信端用户反馈页面)
	 * 
	 * @param resourceId
	 * @param model
	 */
	@RequestMapping(value="/sys/feedback",method = RequestMethod.GET)
	public String feedback(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		return "/feedback/feedback";
	}
	
	/**
	 * 用户反馈成功页面(微信端用户反馈成功页面)
	 * 
	 * @param resourceId
	 * @param model
	 */
	@RequestMapping(value="/sys/feedbackSuccess",method = RequestMethod.GET)
	public String feedbackSuccess(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		return "/feedback/feedbackSuccess";
	}
	/**
	 * 校验用户名和密码
	 * 
	 * @param resourceId
	 * @param model
	 */
	@RequestMapping(value="/sys/checkUserLogin",method = RequestMethod.POST)
	public void checkUserLogin(User user,Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Json json = new Json();
		json.setSuccess(0);
		json.setMsg("登录失败");
		String userName = user.getUserName();
		String password = user.getPassword();
		String md5Password = MD5Util.md5(password);
		//用户名和md5Password再次拼接
		String userNameMd5Password = MD5Util.md5(userName+md5Password);
		User userInfo = userService.userLogin(userName, userNameMd5Password, request, response);
		if(userInfo != null){
			json.setSuccess(1);
			json.setMsg("登录成功");
			json.setUserId(userInfo.getId());
			json.setUserName(userInfo.getUserName());
		}
		BaseResult.writeJson(request, response, json);
		return;
	}
	
	/**
	 * 保存反馈信息
	 * 
	 * @param resourceId
	 * @param model
	 */
	@RequestMapping(value="/sys/saveFeedBack",method = RequestMethod.POST)
	public void saveFeedBack(Infomation info,Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Json json = new Json();
		json.setSuccess(0);
		json.setMsg("登录失败");
		//保存infomation
		Infomation infomation = new Infomation();
		infomation.setId(java.util.UUID.randomUUID().toString());
		org.springframework.beans.BeanUtils.copyProperties(info,infomation);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateTime = sf.format(date);
		infomation.setDate(dateTime);
		try{
			userService.saveFeedBack(info, request, response);
			json.setSuccess(1);
			json.setMsg("反馈成功");
		} catch(Exception e){
			json.setSuccess(0);
			json.setMsg("反馈失败，请稍后再试");
		}
		
		BaseResult.writeJson(request, response, json);
		return;
	}
	
	
}
