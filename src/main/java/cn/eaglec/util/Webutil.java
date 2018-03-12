package cn.eaglec.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.eaglec.dto.SessionInfo;
import cn.eaglec.util.redis.Constants;
import cn.eaglec.util.redis.RedisKeyPrefix;
import cn.eaglec.util.redis.SessionService;
/*import cn.inovance.iotas.web.common.redis.Constants;
import cn.inovance.iotas.web.common.redis.RedisKeyPrefix;
import cn.inovance.iotas.web.common.redis.SessionService;
import cn.inovance.iotas.web.common.util.ConfigUtil;
import cn.inovance.iotas.web.common.util.PropertiesUtils;
import cn.inovance.iotas.web.common.util.RequestUtils;*/

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
public class Webutil {

	private static final Log log = LogFactory.getLog(Webutil.class);

	private static String useRedis = PropertiesUtils.getInstenc().getProperty("redis.useRedis");

	/**
	 * 资源文件名
	 */
	private static final String GLOBAL_PROPERTIES = "redis.properties";

	/**
	 * 持久属性集
	 */
	private static Properties properties;

	private static String propertyname = "useRedis";

	private final static String keySeparator = ":";
	


	/**
	 * 设置request属性
	 * 
	 * @param request
	 * @param attrname
	 *            属性名称
	 * @param value
	 *            属性值<br>
	 *            调用方式如：Webutil.setRequestAttribute(req,usernam,allen)
	 */
	public static void setRequestAttribute(HttpServletRequest request,
			String attrname, Object value) {
		request.setAttribute(attrname, value);
	}

	/**
	 * 获取某属性在request中的属性
	 * 
	 * @param request
	 * @param attrname
	 *            属性名称 调用方式如：Webutil.getRequestAttribute(req,usernam)
	 */
	public static Object getRequestAttribute(HttpServletRequest request,
			String attrname) {
		if (!"false".equals(useRedis) && useRedis != null) {
			return getAttribute(request, attrname);
		} else {
			return request.getAttribute(attrname);
		}
	}

	/**
	 * 设置session属性
	 * 
	 * @param request
	 *            request对象
	 * @param attrname
	 *            属性名称
	 * @param value
	 *            属性值<br>
	 *            调用方式如：Webutil.setSessionAttribute(request,usernam,allen)
	 */
	public static void setSessionAttribute(HttpServletRequest request,
			String attrname, Object value) {

		if (!"false".equals(useRedis) && useRedis != null) {

			String key = geneRedisKey(request, attrname);
			SessionService.getInstance().save(key, value);
		} else {
			request.getSession().setAttribute(attrname, value);
		}
		saveOnlinelog(request, null, false, attrname);
	}

	/**
	 * getSid:取session id
	 * 
	 * @param @param request
	 * @param @return 设定文件
	 * @return String DOM对象
	 * @throws
	 * @since CodingExample　Ver 1.0
	 * @author daihf
	 * @Date 2011 May 5, 2011 1:35:48 PM
	 */
	private static String getSid(HttpSession session) {
		StringBuffer sb = new StringBuffer(session.getId());
		sb.append(keySeparator);
		sb.append(Constants.webserverName);
		return sb.toString();
	}

	/**
	 * getCid:取cookie id
	 * 
	 * @param @param request
	 * @param @return 设定文件
	 * @return String DOM对象
	 * @throws
	 * @since CodingExample　Ver 1.0
	 * @author daihf
	 * @Date 2011 May 5, 2011 1:36:16 PM
	 */
	public static String getCid(HttpServletRequest request) {
		Cookie cookie = RequestUtils.getCookie(request,
				Constants.session_key_flag_of_cookie);
		if (cookie != null) {
			// 取出session的ID
			return cookie.getValue();

		} else {
			return "";
		}
	}

	public static Cookie setCookie(HttpServletRequest request,
			HttpServletResponse response) {
		String sid = getSid(request.getSession());
		return RequestUtils.setCookie(request, response,
				Constants.session_key_flag_of_cookie, sid);
	}

	public static void validateCookie(HttpServletRequest request,
			HttpServletResponse response) {
		Cookie cookie = RequestUtils.getCookie(request,
				Constants.session_key_flag_of_cookie);

		if (cookie != null) {
			// 清除cookie
			RequestUtils.setCookie(request, response,
					Constants.session_key_flag_of_cookie, "", 0);
		}

	}

	/**
	 * 获取某属性在session中的属性
	 * 
	 * @param request
	 * @param attrname
	 *            属性名称 调用方式如：Webutil.getSessionAttribute(request,usernam)
	 */
	// public static Object getSessionAttribute(HttpSession session,
	// String attrname) {
	//
	// if (!"false".equals(useRedis)) {
	//
	// String key = geneRedisKey(session, attrname);
	// return SessionService.getInstance().get(key, true);
	//
	// } else {
	//
	// return session.getAttribute(attrname);
	//
	// }
	// }

	// public static Object getSessionAttribute(HttpSession session,
	// String cookieId, String attrname) {
	// if (!"false".equals(useRedis)) {
	// if ("".equals(cookieId)) {
	// return null;
	// }
	// String key = RedisKeyPrefix.LOGIN_SESSION + keySeparator + cookieId
	// + keySeparator + attrname;
	// return SessionService.getInstance().get(key, true);
	//
	// } else {
	//
	// return session.getAttribute(attrname);
	//
	// }
	// }

	/**
	 * 获取某属性在session中的属性
	 * 
	 * @param request
	 * @param attrname
	 *            属性名称 调用方式如：Webutil.getSessionAttribute(request,usernam)
	 */
	public static Object getAttribute(HttpServletRequest request,
			String attrname) {

		
		
		if (!"false".equals(useRedis)) {
			SessionService sessionService = SessionService.getInstance();
			
			String key = "";
			Object obj = null;
			String securityCode = request.getParameter("securityCode");
			if(StringUtils.isNotBlank(securityCode)){
				key = geneRedisKey(securityCode);
				obj = sessionService.get(attrname);
			}else{
				key = geneRedisKey(request, attrname);
				obj = sessionService.get(attrname,sessionService.getWebSessionTimeoutSecends());
			}

			
			return obj;

		} else {
			return request.getSession().getAttribute(attrname);
		}
	}
	/**
	 * 获取某属性在session中的属性
	 * 
	 * @param request
	 * @param attrname
	 *            属性名称 调用方式如：Webutil.getSessionAttribute(request,usernam)
	 */
	public static Object getSessionInfoAttribute(HttpServletRequest request) {
		String attrname = request.getParameter("sid");
		if(StringUtils.isBlank(attrname)){
			return null;
		}
		if (!"false".equals(useRedis)) {
			SessionService sessionService = SessionService.getInstance();
			
			String key = "";
			Object obj = null;
			String securityCode = request.getParameter("securityCode");
			if(StringUtils.isNotBlank(securityCode)){
				key = geneRedisKey(securityCode);
				obj = sessionService.get(attrname);
			}else{
				key = geneRedisKey(request, attrname);
				obj = sessionService.get(attrname,sessionService.getWebSessionTimeoutSecends());
			}

			
			return obj;

		} else {
			return request.getSession().getAttribute(attrname);
		}
	}
	
	/**
	 * 获取某属性在session中的属性
	 * 
	 * @param request
	 * @param attrname
	 *            属性名称 调用方式如：Webutil.getSessionAttribute(request,usernam)
	 */
	public static Object getAttributeByCid(HttpServletRequest request,String cid,
			String attrname) {
		
		if (!"false".equals(useRedis)) {
			if(cid == null || cid.isEmpty()){
				return null;
			}
			String key = geneRedisKey(cid, attrname);
			SessionService sessionService = SessionService.getInstance();
			return sessionService.get(key,
					sessionService.getWebSessionTimeoutSecends());

		} else {
			return request.getSession().getAttribute(attrname);
		}
	}
	/**
	 * 获取某属性在session中的属性
	 * 
	 * @param request
	 * @param attrname
	 *            属性名称 调用方式如：Webutil.getSessionAttribute(request,usernam)
	 */
	public static Object getAttribute(HttpServletRequest request,
			Cookie cookie, String attrname) {
		if (!"false".equals(useRedis)) {
			String key = geneRedisKey(cookie, attrname);
			SessionService sessionService = SessionService.getInstance();
			return sessionService.get(key,
					sessionService.getWebSessionTimeoutSecends());

		} else {
			return request.getSession().getAttribute(attrname);
		}
	}

	/**
	 * 设置session属性
	 * 
	 * @param request
	 *            request对象
	 * @param attrname
	 *            属性名称
	 * @param value
	 *            属性值<br>
	 *            调用方式如：Webutil.setSessionAttribute(request,usernam,allen)
	 */
	public static void setAttribute(HttpServletRequest request,
			HttpServletResponse response, String attrname, Object value) {
		Cookie cookie = null;
		if (!"false".equals(useRedis) && useRedis != null) {
			cookie = setCookie(request, response);
			SessionService.getInstance().save(attrname, value);
		} else {
			request.getSession().setAttribute(attrname, value);
		}
		saveOnlinelog(request, cookie, false, attrname);
	}
	
	/**
	 * 国际化设置页面风格
	 * 
	 * @param request
	 *            request对象
	 * @param attrname
	 *            属性名称
	 * @param value
	 *            属性值<br>
	 *            调用方式如：Webutil.setSessionAttribute(request,usernam,allen)
	 */
	public static void setAttribute(HttpServletRequest request,
			HttpServletResponse response, String attrname, String style) {
		Cookie cookie = null;
		//先删除之前的信息
		Webutil.removeAttribute(request,attrname);
		if (!"false".equals(useRedis) && useRedis != null) {
			cookie = setCookie(request, response);
			SessionService.getInstance().save(attrname, style);
		}
	}
	
	
	
	/**
	 * 获取用户和key的对应
	 */
	public static Object getRedisKey(String attrname){
		return SessionService.getInstance().get(attrname);
		
	}
	/**
	 * 更新Sessiion属性
	 * 
	 * @param request
	 *            request对象
	 * @param attrname
	 *            属性名称
	 * @param value
	 *            属性值<br>
	 *            调用方式如：Webutil.setSessionAttribute(request,usernam,allen)
	 */
	public static void updateAttribute(HttpServletRequest request,
			String attrname, Object value) {
		if (!"false".equals(useRedis) && useRedis != null) {
			String key = geneRedisKey(request, attrname);
			SessionService.getInstance().save(key, value);
		} else {
			request.getSession().setAttribute(attrname, value);
		}
		// saveOnlinelog(request, null, false, attrname);
	}

	public static void updateAttribute(String key, Object value) {
		if (!"false".equals(useRedis) && useRedis != null) {
			SessionService.getInstance().save(key, value);
		} else {
			
		}
		// saveOnlinelog(request, null, false, attrname);
	}
	
	/**
	 * 从session中移除某一属性值
	 * 
	 * @param session
	 * @param attrname
	 */
	// public static void removeSessionAttribute(HttpSession session,
	// String attrname) {
	// if (!"false".equals(useRedis) && useRedis != null) {
	// String key = geneRedisKey(session, attrname);
	// SessionService.getInstance().remove(key);
	// } else {
	// session.removeAttribute(attrname);
	// }
	// }

	/**
	 * 从session中移除某一属性值
	 * 
	 * @param session
	 * @param attrname
	 */
	public static void removeAttribute(HttpServletRequest request,
			String attrname) {
		if (!"false".equals(useRedis) && useRedis != null) {
			SessionService.getInstance().remove(attrname);
		} else {
			request.getSession().removeAttribute(attrname);
		}

	}
	/**
	 * 删除属性
	 * auth :zxb
	 */
	public static void removeAttr(String name,String value){
		if (!"false".equals(useRedis) && useRedis != null) {
			if(name == null){
				SessionService.getInstance().remove(value);
			}else{
				SessionService.getInstance().remove(name);
				SessionService.getInstance().remove(value);
			}
		}
	}
	
	
	public static void removeAttr(String key){
		
		SessionService.getInstance().remove(key);
	}

	/**
	 * 设置session失效
	 * 
	 * @param session
	 */
	public static void sessionInvalidate(HttpSession session) {

		session.invalidate();

	}

	/**
	 * 取得Sessionid
	 * 
	 * @param session
	 * @return
	 */
	public static String getSessionId(HttpSession session) {
		return session.getId();
	}

	/**
	 * 
	 * sessionInvalidate:(设置session失效). <br/>
	 * 
	 * @param attrname
	 * @since JDK 1.7
	 */
	public static void sessionInvalidate(HttpServletRequest request) {
		request.getSession().invalidate();

	}

	// public static String geneRedisKey(HttpSession session, String attrname) {
	// String sid = getSid(session);
	// String key = RedisKeyPrefix.LOGIN_SESSION + keySeparator + sid
	// + keySeparator + attrname;
	// return key;
	// }

	public static String geneRedisKey(Cookie cookie, String attrname) {
		String cid = cookie.getValue();
		String key = RedisKeyPrefix.SYSTEM + keySeparator+ RedisKeyPrefix.LOGIN_SESSION + keySeparator + cid
				+ keySeparator + attrname;
		return key;
	}
	public static String geneRedisKey(String cid, String attrname) {
		String key = RedisKeyPrefix.SYSTEM + keySeparator+ RedisKeyPrefix.LOGIN_SESSION + keySeparator + cid
				+ keySeparator + attrname;
		return key;
	}
	public static String geneRedisKey(HttpServletRequest request,
			String attrname) {
		String cid = getCid(request);
		String key = RedisKeyPrefix.SYSTEM + keySeparator+ RedisKeyPrefix.LOGIN_SESSION + keySeparator + cid
				+ keySeparator + attrname;
		return key;
	}
	
	public static String geneRedisKey(String attrname) {
		String key = RedisKeyPrefix.SYSTEM + keySeparator+ RedisKeyPrefix.LOGIN_SESSION + keySeparator + attrname;
		return key;
	}
	
	public static String geneAuthorityRedisKey(String attrname) {
		String key = RedisKeyPrefix.SYSTEM + keySeparator+ RedisKeyPrefix.Authority + keySeparator + attrname;
		return key;
	}
	

	/**
	 * 
	 * SaveOnlinelog:(保存用户登录历史). <br/>
	 * 
	 * @author w1898
	 * @param session
	 * @param isleave
	 * @param attrname
	 * @since JDK 1.7
	 */
	public static void saveOnlinelog(HttpServletRequest request, Cookie cookie,
			boolean isleave, String attrname) {
		SessionInfo sessionInfo = null;
		if (!isleave) {
			log.debug("向session存入属性：" + attrname);
			if (!ConfigUtil.getSessionInfoName().equals(attrname)) {// 如果存入的属性是sessionInfo的话
				return;
			}
			sessionInfo = (SessionInfo) getAttribute(request, cookie, attrname);
		} else {
			log.debug("session销毁：" + Webutil.getCid(request));
			sessionInfo = (SessionInfo) getAttribute(request,
					ConfigUtil.getSessionInfoName());
		}
		if (sessionInfo != null) {
			// System.out.println(sessionInfo.getUser().getName() + "注销了");
		}
		if (isleave) {
			removeAttribute(request, ConfigUtil.getSessionInfoName());
		}
	}
	
	
	public static Set<Object> find(String pattern){
		return SessionService.getInstance().find(pattern);
	}
	
	public static List<Object> multiGet(Set<String> keys){
		return SessionService.getInstance().mulitGet(keys);
	}
	 
	 /**
     * 设置用户和key的对应
     */
	/*public static void setRedisKey(String attrname,UserRedisKey redisKeyValue){
		SessionService.getInstance().save(attrname, redisKeyValue);
	}*/
	
	/**
	 * 解析简单的json字符串
	 * @param jsonStr
	 * @return
	 */
	public static Map<String,Object> parseJson(String jsonStr)
	{
		 Map<String, Object> listMap = JSON.parseObject(jsonStr, new TypeReference<Map<String,Object>>(){});
		 return listMap;
	}
	
	
	/**
	 * setAttribute:保存关键字及对应的值，同时设置关键字超时时间. <br/>
	 * 
	 * @author z1979
	 * @param key
	 *            关键字
	 * @param value
	 *            值域
	 * @param timeoutSecends
	 *            数据超时时间（秒）
	 * @since JDK 1.7
	 */
	public static void setAttribute(String key, Object value, long timeoutSecends){
		SessionService.getInstance().saveTimeoutTime(key, value,timeoutSecends);
	}
	
	public static void setAttribute(String key, Object value) {
		
		SessionService.getInstance().save(key, value);

	}
	
	public static Object getAttribute(String key) {
		
		return SessionService.getInstance().get(key);

	}
	
	public static Object getAttribute(String key,boolean isReset) {
		
		return SessionService.getInstance().get(key,isReset);

	}
	
	/**
	 * 根据sid获取
	 * @param sid
	 * @return
	 */
	public static Object getAttributeBySid(String sid){
		if (!"false".equals(useRedis)) {
			SessionService sessionService = SessionService.getInstance();
			Object obj = sessionService.get(sid,sessionService.getWebSessionTimeoutSecends());
			return obj;

		}
		return null;
	}
	
	/**
	 * 获取国际化页面风格
	 * @param sid
	 * @return
	 */
	public static Object getAttributeByStyle(String style){
		if (!"false".equals(useRedis)) {
			SessionService sessionService = SessionService.getInstance();
			Object obj = sessionService.get(style,sessionService.getWebSessionTimeoutSecends());
			if(null == obj || "" == obj){
				return "zh_CN";
			}else{
				return obj;
			}
		}
		return null;
	}
	
	/**
	 * 生成redis的登录key 格式： airc:login:customerCode:userName
	 * @param userName
	 * @param customerCode
	 * @return
	 */
	public static String geneRedisLoginKey(String userName, String customerCode) {
		StringBuilder sb = new StringBuilder();
		sb.append(RedisKeyPrefix.SYSTEM);
		sb.append(keySeparator);
		sb.append(RedisKeyPrefix.LOGIN_SESSION);
		sb.append(keySeparator);
		sb.append(customerCode);
		sb.append(keySeparator);
		sb.append(userName);
		return sb.toString();
	}
	
	public static void publish(String channelName,Object message){
		SessionService.getInstance().convertAndSend(channelName, message);
	}
	/** 
     * 图片添加水印 并保存
     * @param file 图片文件
     * @param outImgPath 添加水印后图片输出路径 
     * @param markContentColor 水印文字的颜色 
     * @param waterMarkContent 水印的文字 
     */ 
	
	public static void watermarking(File file ,String outImgPath,Color markContentColor,String waterMarkContent){
        try {
        	log.info("开始添加时间水印");
			Image srcImg = ImageIO.read(file);
			int srcImgWidth = srcImg.getWidth(null);
			int srcImgHeight = srcImg.getHeight(null);
			//加水印
			BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufImg.createGraphics();
			g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
	        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);//减少锯齿
	        //设置水印文字颜色和大小
			int fontSize = (new Double(srcImgWidth*0.035).intValue()) > 32 ? 32 :new Double(srcImgWidth*0.035).intValue();
            Font font = new Font("微软雅黑", Font.PLAIN,fontSize); 
	        g.setColor(markContentColor);      
	        g.setFont(font);
	        
	        //设置水印文字坐标：右上角
	        int x = new Double(srcImgWidth *0.585).intValue();  
	        int y = new Double(srcImgHeight*0.05).intValue() > 32 ? new Double(srcImgHeight*0.05).intValue() : 32;
	        
        	//绘制
	        g.drawString(waterMarkContent, x, y);  
	        g.dispose(); 
	        
	        // 输出图片  
            FileOutputStream outImgStream = new FileOutputStream(outImgPath);  
            ImageIO.write(bufImg, "jpg", outImgStream);
            outImgStream.flush();  
            outImgStream.close();
            
        	log.info("添加时间水印结束");
		} catch (Exception e) {
			log.error("添加时间水印失败！", e);
		}
	}

/**
 * 设置redis的超时时间，注意（之前的redis超时时间是无效的，现在重写这一个方法，其中timeout为long ，并且默认是秒）
 * @param key
 * @param value
 */
public static void setAttributeTimeOut(String key, Object value, long timeout) {
		//SessionService.getInstance().remove(key);//防止有重复的key,我是在代码里面实现的，这个可以放开。
		SessionService.getInstance().rightPush(key, value, timeout);

	}
}
