package cn.eaglec.util.redis;

import java.util.Calendar;
import java.util.Date;

import cn.eaglec.util.DateUtil;
import cn.eaglec.util.PropertiesUtils;

/**
 * Redis的key工具类
 * @author l2211
 *
 */
public class RedisKeyUtil {
	/**
	 * key值分割符号
	 */
	public static final String KEY_DELIMITER = ":";
	
	public static final String PATTERN_ALL_REX = "*";
	
	/**
	 * 业务系统
	 */
	public static final String SYSTEM = PropertiesUtils.getProperty("business", "airc");
	
	/**
	 * 控制器协议
	 */
	public static final String KEY_CTRL_TYPE = "ctrlType";
	
	/**
	 * 历史数据
	 */
	public static final String KEY_HISTORY_DATA = "historyRunData";
	
	/**
	 * 时间数目
	 */
	public static final String KEY_CTRL_EVENT_COUNT ="ctrlEventCount";
	
	/**
	 * 事件信息
	 */
	public static final String KEY_CTRL_EVENT = "ctrlEvent";
	
	/**
	 * 数据统计缓存
	 */
	public static final String KEY_DEVICE_STATISTIC = "deviceStatistic";
	
	/**airc:ctrlType:空压机控制器WL210D+分体机:3766e65f-462a-4f32-971b-37b7df8a0e31
	 * 生产控制器协议的redis key， 格式：  airc:ctrlType:{$ctrlTypeName}:{$id}
	 * @param ctrlTypeName 协议名称
	 * @param id 协议id
	 * @return
	 */
	public static String geneRedisCtrlTypeKey(String ctrlTypeName, String id){
		StringBuilder sb = new StringBuilder();
		return sb.append(SYSTEM)
				.append(KEY_DELIMITER)
				.append(KEY_CTRL_TYPE)
				.append(KEY_DELIMITER)
				.append(ctrlTypeName)
				.append(KEY_DELIMITER)
				.append(id)
				.toString();
	}

	/**
	 * 生成最新的控制器协议redis key
	 * @param ctrlTypeName
	 * @return
	 */
	public static String geneRedisNewestCtrlTypeKey(String ctrlTypeName){
		return geneRedisCtrlTypeKey(ctrlTypeName, "-1");
	}
	
	
   /**
    * 生成历史数据的 redis key, 格式 airc:historyRunData:{$deviceId}:{$updateDay}
    * @param regCode
    * @param updateDay
    * @return
    */
//	public static String geneRedisHistoryDataKey(String deviceId, String updateDay){
//		StringBuilder sb = new StringBuilder();
//		return sb.append(SYSTEM)
//				.append(KEY_DELIMITER)
//				.append(KEY_HISTORY_DATA)
//				.append(KEY_DELIMITER)
//				.append(deviceId)
//				.append(KEY_DELIMITER)
//				.append(updateDay)
//				.toString();
//	}
	
	/**
	 * 生成当天的历史数据的 rediskey = airc:historyRunData:{$deviceId}:{$当天的日期}
	 * @param devieId
	 * @return
	 */
//	public static String geneRedisRealDataKey(String deviceId) {
//		String updateDay = DateUtil.getDayStringByDate(new Date());
//		return geneRedisHistoryDataKey(deviceId, updateDay);
//	}
	
	/**
	 * 前一天的历史数据key， airc:historyRunData:{$deviceId}:{$前一天日期}
	 * @return
	 */
//	public static String geneReidsLastDayRunDataPattern(){
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(new Date());
//		calendar.add(Calendar.DAY_OF_MONTH, -1);
//		String updateDay = DateUtil.getDayStringByDate(calendar.getTime());
//		return geneRedisHistoryDataKey( "*", updateDay);
//	}
	
	/**
	 * 设备当天的故障数目 key, airc:ctrlEventCount:{$regCode}:{$updateDay}
	 * @param regCode
	 * @return
	 */
	public static String geneRedisCtrlEventCountKey(String cutomerCode, String regCode) {
		StringBuilder sb = new StringBuilder();
		String updateDay = DateUtil.getDayStringByDate(new Date());
		return sb.append(SYSTEM)
				.append(KEY_DELIMITER)
				.append(KEY_CTRL_EVENT_COUNT)
				.append(KEY_DELIMITER)
				.append(regCode)
				.append(KEY_DELIMITER)
				.append(updateDay)
				.toString();
	}
	
	/**
	 * 设备的故障列表key
	 * @param customerCode
	 * @param deviceId
	 * @return
	 */
	public static String geneRedisCtrlEventListKey(String customerCode, String deviceId){
		StringBuilder sb = new StringBuilder();
		return sb.append(SYSTEM)
				.append(KEY_DELIMITER)
				.append(KEY_CTRL_EVENT)
				.append(KEY_DELIMITER)
				.append(customerCode)
				.append(KEY_DELIMITER)
				.append(deviceId)
				.toString();
	}
	
	/**
	 * 设备的故障码与故障信息key, airc:ctrlEvent:{$cutomerCode}:{$deviceId}:{$failureCodeName}
	 * @param regCode
	 * @return
	 */
	public static String geneRedisCtrlEventKey(String customerCode, String deviceId, String failureCodeName) {
		StringBuilder sb = new StringBuilder();
		return sb.append(SYSTEM)
				.append(KEY_DELIMITER)
				.append(KEY_CTRL_EVENT)
				.append(KEY_DELIMITER)
				.append(customerCode)
				.append(KEY_DELIMITER)
				.append(deviceId)
				.append(KEY_DELIMITER)
				.append(failureCodeName)
				.toString();
	}
	
	/**
	 * 设备的故障数量key, airc:ctrlEvent:{$cutomerCode}:{$cdRegCode}:{$eventCode}:{$day}
	 * @param regCode
	 * @return
	 */
	public static String geneRedisCtrlEventDeviceOneDayKey(String customerCode, String cdRegCode, String eventCode, String day) {
		StringBuilder sb = new StringBuilder();
		return sb.append(SYSTEM)
				.append(KEY_DELIMITER)
				.append(KEY_CTRL_EVENT)
				.append(KEY_DELIMITER)
				.append(customerCode)
				.append(KEY_DELIMITER)
				.append(cdRegCode)
				.append(KEY_DELIMITER)
				.append(eventCode)
				.append(KEY_DELIMITER)
				.append(day)
				.toString();
	}
	
	
	/**
	 * 设备的统计信息key, airc:deviceStatistic:{$deviceId}
	 *  
	 * @param deviceId
	 * @return
	 */
	public static String geneRedisDeviceStatisticKey(String companyId, String deviceId) {
		StringBuilder sb = new StringBuilder();
		return sb.append(SYSTEM)
				.append(KEY_DELIMITER)
				.append(KEY_DEVICE_STATISTIC)
				.append(KEY_DELIMITER)
				.append(companyId)
				.append(KEY_DELIMITER)
				.append(deviceId)
				.toString();
	}
	
	/**
	 * 所有
	 * @return
	 */
	public static String geneRedisDeviceStatisticKeyPattern(String companyId){
		StringBuilder sb = new StringBuilder();
		return sb.append(SYSTEM)
				.append(KEY_DELIMITER)
				.append(KEY_DEVICE_STATISTIC)
				.append(KEY_DELIMITER)
				.append(companyId)
				.append(KEY_DELIMITER)
				.append(PATTERN_ALL_REX)
				.toString();
	}
	
}
