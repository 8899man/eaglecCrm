package cn.eaglec.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 序列化
 * @param <T>
 */
public class BaseResult {

	private static final Logger logger = Logger.getLogger(BaseResult.class);
	//不打印日志的方法
	private static final String[] NO_LOG_INFO_METHOD = {"get","find","list"};
	
	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param includesProperties
	 *            需要转换的属性
	 * @param excludesProperties
	 *            不需要转换的属性
	 */
	public static void writeJsonByFilter(HttpServletRequest request,HttpServletResponse response,
			Object object, String[] includesProperties,	String[] excludesProperties) {
		try {
			FastjsonFilter filter = new FastjsonFilter();// excludes优先于includes
			if (excludesProperties != null && excludesProperties.length > 0) {
				filter.getExcludes().addAll(
						Arrays.<String> asList(excludesProperties));
			}
			if (includesProperties != null && includesProperties.length > 0) {
				filter.getIncludes().addAll(
						Arrays.<String> asList(includesProperties));
			}
			logger.debug("对象转JSON：要排除的属性[" + Arrays.toString(excludesProperties) + "]要包含的属性["
					+ Arrays.toString(includesProperties) + "]");
			String json;
			String User_Agent = request.getHeader("User-Agent");
			if (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 6") > -1) {
				// 使用SerializerFeature.BrowserCompatible特性会把所有的中文都会序列化为\\uXXXX这种格式，字节数会多一些，但是能兼容IE6
				json = JSON.toJSONString(object, filter,
						SerializerFeature.WriteDateUseDateFormat,
						SerializerFeature.DisableCircularReferenceDetect,
						SerializerFeature.BrowserCompatible);
			} else {
				// 使用SerializerFeature.WriteDateUseDateFormat特性来序列化日期格式的类型为yyyy-MM-dd
				// hh24:mi:ss
				// 使用SerializerFeature.DisableCircularReferenceDetect特性关闭引用检测和生成
				json = JSON.toJSONString(object, filter,
						SerializerFeature.WriteDateUseDateFormat,
						SerializerFeature.DisableCircularReferenceDetect);
			}
			
			String path = request.getServletPath();
			StringBuffer params = new StringBuffer();
			Map<String,String[]> paramsMap = request.getParameterMap();
			
			Set<String> keySet = paramsMap.keySet();
	        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
	            String key = it.next();
	            params.append(key+"=");
	            String[] values = paramsMap.get(key);
	            for(int i=0;i<values.length;i++){
	            	params.append(values[i]);
	            	if(i!=values.length-1){
	            		params.append(",");
	            	}
	            	
	            }
	            params.append(",");
	        }
	        
	        logger.info("请求："+path+" 参数："+params.toString());
        	logger.info("响应：" + json);
        	
	        /*if(isLogger(path)){
	        	logger.info("请求："+path+" 参数："+params.toString());
	        	logger.info("响应：" + json);
	        }else{
	        	if(logger.isDebugEnabled()){
	        		logger.debug("请求："+path+" 参数："+params.toString());
		        	logger.debug("响应：" + json);
	        	}
	        }*/
	        
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean isLogger(String path){
		for(String s : NO_LOG_INFO_METHOD){
			if(path.indexOf(s) != -1){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param includesProperties
	 *            需要转换的属性
	 * @param excludesProperties
	 *            不需要转换的属性
	 */
	public static void writeJsonByFilter(HttpServletRequest request,HttpServletResponse response,
			Object object, String[] includesProperties,	String[] excludesProperties,boolean isLogPrintOut) {
		try {
			FastjsonFilter filter = new FastjsonFilter();// excludes优先于includes
			if (excludesProperties != null && excludesProperties.length > 0) {
				filter.getExcludes().addAll(
						Arrays.<String> asList(excludesProperties));
			}
			if (includesProperties != null && includesProperties.length > 0) {
				filter.getIncludes().addAll(
						Arrays.<String> asList(includesProperties));
			}
			logger.debug("对象转JSON：要排除的属性[" + Arrays.toString(excludesProperties) + "]要包含的属性["
					+ Arrays.toString(includesProperties) + "]");
			String json;
			String User_Agent = request.getHeader("User-Agent");
			if (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 6") > -1) {
				// 使用SerializerFeature.BrowserCompatible特性会把所有的中文都会序列化为\\uXXXX这种格式，字节数会多一些，但是能兼容IE6
				json = JSON.toJSONString(object, filter,
						SerializerFeature.WriteDateUseDateFormat,
						SerializerFeature.DisableCircularReferenceDetect,
						SerializerFeature.BrowserCompatible);
			} else {
				// 使用SerializerFeature.WriteDateUseDateFormat特性来序列化日期格式的类型为yyyy-MM-dd
				// hh24:mi:ss
				// 使用SerializerFeature.DisableCircularReferenceDetect特性关闭引用检测和生成
				json = JSON.toJSONString(object, filter,
						SerializerFeature.WriteDateUseDateFormat,
						SerializerFeature.DisableCircularReferenceDetect);
			}
			
			String path = request.getServletPath();
			StringBuffer params = new StringBuffer();
			Map<String,String[]> paramsMap = request.getParameterMap();
			
			Set<String> keySet = paramsMap.keySet();
	        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
	            String key = it.next();
	            params.append(key+"=");
	            String[] values = paramsMap.get(key);
	            for(int i=0;i<values.length;i++){
	            	params.append(values[i]);
	            	if(i!=values.length-1){
	            		params.append(",");
	            	}
	            	
	            }
	            params.append(",");
	        }
	        if(isLogPrintOut){
	        	logger.info("请求："+path+" 参数："+params.toString());
		        logger.info("响应：" + json);
	        }else{
	        	logger.debug("请求："+path+" 参数："+params.toString());
		        logger.debug("响应：" + json);
	        }
        	
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台(打印日志)
	 * 
	 * @param object
	 * @throws IOException
	 */
	public static void writeJson(HttpServletRequest request,HttpServletResponse response,Object object) {
		writeJsonByFilter(request,response,object, null, null);
	}
	
	/**
	 * 将对象转换成JSON字符串，并响应回前台()
	 * 
	 * @param object
	 * @isLogPrintOut 打印日志 不打印
	 * @throws IOException
	 */
	public static void writeJson(HttpServletRequest request,HttpServletResponse response,Object object,boolean isLogPrintOut) {
		writeJsonByFilter(request,response,object, null, null,isLogPrintOut);
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param includesProperties
	 *            需要转换的属性
	 */
	public static void writeJsonByIncludesProperties(HttpServletRequest request,HttpServletResponse response,Object object,String[] includesProperties) {
		writeJsonByFilter(request,response,object, includesProperties, null);
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param excludesProperties
	 *            不需要转换的属性
	 */
	public static void writeJsonByExcludesProperties(HttpServletRequest request,HttpServletResponse response,Object object,String[] excludesProperties) {
		writeJsonByFilter(request,response,object, null, excludesProperties);
	}

	
	/**
	 * 下载文件
	 * @throws IOException
	 */
	public static void download(HttpServletRequest request,HttpServletResponse response,String downloadPath) throws IOException {
		FileInputStream fis = null;
		OutputStream out = null;
		
		try{
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
			ServletContext servletContext = webApplicationContext.getServletContext();
			
			String folder = servletContext.getRealPath("/");
			
			logger.info(folder);
			
			String filePath = folder + File.separator + downloadPath;

			logger.info("filePath:"+filePath);
	
			// 创建一个File
			File file = new File(filePath);
			// 生成文件名
			String fileName = file.getName();
			
			response.reset();
			response.setContentType("application/x-msdownload");
			String value = "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			response.setHeader("Content-disposition", value);
			
//			response.setContentType("application/x-msdownload charset=utf-8");
//			response.setHeader("Content-disposition", "attachment; filename="+ URLEncoder.encode(file.getName(),"utf-8"));
			// 利用输出输入流导出文件
			int len = 0;
			byte[] buf = new byte[1024];
			fis = new FileInputStream(file);
			out = response.getOutputStream();
			while ((len = fis.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.flush();

		}catch(Exception e){
			logger.error("下载模板错误",e);
		}finally{
			if(fis!=null){
				fis.close();
			}
			if(out!=null){
				out.close();
			}
		}
	}

}
