package cn.eaglec.dto;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * JSON模型.
 * 
 * 用户后台向前台返回的JSON对象
 * 
 * 
 */
public class Json implements java.io.Serializable {

	/**
	 * 操作结果 1.成功 0.失败
	 */
	private int success = 0;
	
	private String errorCode = null;
	
	private String msg = "操作失败";
	
	private Map<String,String> mapParams = new LinkedHashMap<String, String>();
	
	private JSONObject JsonParams = new JSONObject();
	
	//国际化语言key
	private String msgKey = "";
	
	private Object obj = null;
	
	//空压机操作日志信息
	private String kyjLogInfo = "";
	
	//空压机登录IP信息
	private String ipLogInfo = "";
	
	private String userId;
	
	private String userName;

	public int isSuccess() {
		return success;
	}
	
	public int getSuccess() {
		return success;
	}
	
	public void setSuccess(int success) {
		this.success = success;
	}

	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public Map<String, String> getMapParams() {
		return mapParams;
	}

	public void setMapParams(Map<String, String> mapParams) {
		this.mapParams = mapParams;
	}

	public JSONObject getJsonParams() {
		return JsonParams;
	}

	public void setJsonParams(JSONObject jsonParams) {
		JsonParams = jsonParams;
	}

	public String getKyjLogInfo() {
		return kyjLogInfo;
	}

	public void setKyjLogInfo(String kyjLogInfo) {
		this.kyjLogInfo = kyjLogInfo;
	}

	public String getIpLogInfo() {
		return ipLogInfo;
	}

	public void setIpLogInfo(String ipLogInfo) {
		this.ipLogInfo = ipLogInfo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
