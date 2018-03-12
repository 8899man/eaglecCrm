package cn.eaglec.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class WebUtils {
	private static final Logger logger = Logger.getLogger(WebUtils.class);
	/** 
     * 发送HttpPost请求 
     *  
     * @param strURL 
     *            服务地址 
     * @param postData  json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号 
     * @return 成功:返回json字符串
     */  
	public static String doPost(String strURL, String postData)
	{
		HttpURLConnection connection = null;
		DataOutputStream out = null;
		BufferedReader reader = null;
		
		try {
			URL postUrl = new URL(strURL);
			
			 // 打开连接
	        connection = (HttpURLConnection) postUrl .openConnection();
	        
	        // 设置是否向connection输出，因为这个是post请求，参数要放在 http正文内，因此需要设为true
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setRequestMethod("POST");
	        connection.setInstanceFollowRedirects(true);
	        
	        // Post 请求不能使用缓存
	        connection.setUseCaches(false);
	        
	        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        connection.connect();
	        
	        out = new DataOutputStream(connection.getOutputStream());
	        
	        String content = postData;
	        out.writeBytes(content);
	        out.flush();
	        //out.close(); // flush and close
	        
	        reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));//设置编码,否则中文乱码
	        String line="";
	        StringBuilder sb = new StringBuilder();
	        
	        while ((line = reader.readLine()) != null){
	           // System.out.println(line);
	        	sb.append(line);
	        }
	        //reader.close();
	        
	        //connection.disconnect();
	        
			return sb.toString();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally
		{
			if (out != null) {
				try {
					out.close();
				} catch (Exception ex1) {

				}
			}
			
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception ex2) {

				}
			}
			
			if (connection != null) {
				try {
					connection.disconnect();
				} catch (Exception ex3) {

				}
			}
		}
		return "error";
	}
	public static void main(String[] args) {
		String strUrl = "http://localhost:15080/sys/sys/test"; 
		String postData = "{\"errorCode\":\"0\",\"id\":\"12345\"}";
		doPost(strUrl, postData);
		String msg = "{\"errorCode\":\"0\"}";
		JSONObject obj = JSON.parseObject(msg);
		System.out.println(obj.get("errorCode"));
	}
}
