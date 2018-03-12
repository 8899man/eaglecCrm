package cn.eaglec.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 地址位置处理公有类
 * @author z2197
 *
 */
public class LocationUtil {
	
	private static final Logger logger = Logger
			.getLogger(LocationUtil.class);
	
	/**
	 *  通过百度经纬度 获取省市区
	 */
	public static String getAddressByLngAndLat(String lng, String lat){
		try{
			String ak = PropertiesUtils.getProperties().getProperty("ak");
			String formattedAddress = "";
			//经纬度不为空 才做相应处理
			if(lng!="" && lng!=null && lat!="" && lat!=null){
				StringBuffer sb = new StringBuffer();
				// 请求网址
				String https = PropertiesUtils.getProperties().getProperty(
						"showLocation_http");
				sb.append(https);
				// 秘钥AK
				String akUrl = "&ak=" + ak;
				sb.append(akUrl);
				sb.append("&location=" +lat +","+lng);//反查经纬度顺序变成逆向
				logger.info("通过经纬度反查所在详细地址的http："+sb.toString());
				URL u = null;
				InputStream in = null;
				ByteArrayOutputStream out = null;
				try {
					u = new URL(sb.toString());
					in = u.openStream();
					out = new ByteArrayOutputStream();
					byte buf[] = new byte[1024];
					int read = 0;
					while ((read = in.read(buf)) > 0) {
						out.write(buf, 0, read);
					}
					byte b[] = out.toByteArray();
					String message = new String(b, "utf-8");	
					Document document=DocumentHelper.parseText(message);
					Element rootElm = document.getRootElement();
					String status = rootElm.elementTextTrim("status");
					logger.info("获取到的省市区的状态(0表示正常)："+status);
					//状态为0 表示正常
					if("0".equals(status)){
						Iterator  result = rootElm.elementIterator("result");
						while (result.hasNext()) {  
							Element recordEle1 = (Element) result.next();  
							formattedAddress = recordEle1.elementTextTrim("formatted_address");
						}
					}else{
						logger.info("不能获得省市区，状态为："+status);
					}
				} catch (MalformedURLException e) {
				    logger.error("MalformedURLException 获取详细地址异常：", e);
				} catch (IOException e) {
				    logger.error("IOException 获取详细地址异常：", e);
				} catch (DocumentException e) {
				    logger.error("DocumentException 获取详细地址异常：", e);
				} finally {
					IOUtils.closeQuietly(in);  
					IOUtils.closeQuietly(out); 
				}
			}
			logger.info("获取到的详细地址："+formattedAddress);
			return formattedAddress;
		}catch(Exception e){
			logger.error("获取详细地址异常：", e);
			return "";
		}
		
	}
	/**
	 *  通过百度经纬度 获取省市区
	 */
	public static String[] getLocationByLngAndLat(String lng, String lat){
		try{
			String ak = PropertiesUtils.getProperties().getProperty("ak");
			String[] strs = new String[3];;
			String province = null;
			String city = null;
			String district = null;
			//经纬度不为空 才做相应处理
			if(lng!="" && lng!=null && lat!="" && lat!=null){
				StringBuffer sb = new StringBuffer();
				// 请求网址
				String https = PropertiesUtils.getProperties().getProperty(
						"showLocation_http");
				sb.append(https);
				// 秘钥AK
				String akUrl = "&ak=" + ak;
				sb.append(akUrl);
				sb.append("&location=" +lat +","+lng);//反查经纬度顺序变成逆向
				logger.info("通过经纬度反查所在省市区的http："+sb.toString());
				URL u = null;
				InputStream in = null;
				ByteArrayOutputStream out = null;
				try {
					u = new URL(sb.toString());
					in = u.openStream();
					out = new ByteArrayOutputStream();
					byte buf[] = new byte[1024];
					int read = 0;
					while ((read = in.read(buf)) > 0) {
						out.write(buf, 0, read);
					}
					byte b[] = out.toByteArray();
					String message = new String(b, "utf-8");	
					Document document=DocumentHelper.parseText(message);
					Element rootElm = document.getRootElement();
					String status = rootElm.elementTextTrim("status");
					logger.info("获取到的省市区的状态(0表示正常)："+status);
					//状态为0 表示正常
					if("0".equals(status)){
						Iterator  result = rootElm.elementIterator("result");
						while (result.hasNext()) {  
							Element recordEle1 = (Element) result.next();  
							Iterator addressComponent = recordEle1.elementIterator("addressComponent"); 
							while (addressComponent.hasNext()) {  
								 Element recordEle = (Element) addressComponent.next();
								 String country = recordEle.elementTextTrim("country");
								 province = recordEle.elementTextTrim("province");
								 city = recordEle.elementTextTrim("city");
								 district = recordEle.elementTextTrim("district");
							}
						}
					}else{
						logger.info("不能获得省市区，状态为："+status);
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (DocumentException e) {
					e.printStackTrace();
				} finally{
					IOUtils.closeQuietly(in);
					IOUtils.closeQuietly(out);
				}
			}
			logger.info("获取到的省市区："+province+city+district);
			strs[0] = province;
			strs[1] = city;
			strs[2] = district;
			return strs;
		}catch(Exception e){
			logger.error("获取省市区异常：", e);
			return null;
		}
		
	}

}
