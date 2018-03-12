package cn.eaglec.util.redis;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import cn.eaglec.util.PropertiesUtils;

public class SessionService {

	/*** 创建全局的唯一redisTemplate实例. */
	@SuppressWarnings("unchecked")
	public static RedisTemplate redisTemplate = (RedisTemplate) SpringUtil
			.getBean("redisTemplate");
	/** 本类采用单实例模式. */
	private static SessionService instance = null;
	/** Web用户session数据超时时间(分钟). */
	private final long webSessionExpiryTime = Long.parseLong(PropertiesUtils.getInstenc().getProperty("redis.timeOut","30"));

	private static ValueOperations<String, Object> valueOper;
	
	private static ListOperations<String, Object> listOper;

	static {
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		valueOper = redisTemplate.opsForValue();
		listOper = redisTemplate.opsForList();
	}

	public static void setValueSerializer(
			JacksonJsonRedisSerializer<Object> jacksonJsonRedisSerializer) {
		redisTemplate.setValueSerializer(jacksonJsonRedisSerializer);
	}

	/**
	 * 取得超时时间.
	 * 
	 * @return long
	 */
	public long getWebSessionTimeoutSecends() {
		long time = webSessionExpiryTime * 60;
		return time;
	}

	/**
	 * 单例模式创建SessionService对象.
	 * 
	 * @return SessionService
	 */
	public static SessionService getInstance() {
		if (instance == null) {
			instance = new SessionService();
		}
		return instance;
	}

	/**
	 * 构造函数初始化
	 */
	protected SessionService() {

	}

	/**
	 * 判断Redis中是否存在该关键字
	 * 
	 * @param key
	 *            关键字
	 * @return
	 */
	public boolean sessionExists(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * get:获取关键字对应的值域. <br/>
	 * 
	 * @author z1979
	 * @param key
	 *            关键字
	 * @return 关键字对应的值域
	 * @since JDK 1.7
	 */
	public Object get(String key) {
		Object value = null;
		if (sessionExists(key)) {
			value = valueOper.get(key);
			valueOper.set(key, value);
		}
		return value;
	}
	
	/**
	 * get:获取关键字对应的值域. <br/>
	 * 
	 * @author z2197
	 * @param key
	 * @param isReset 是否重新设置
	 *            关键字
	 * @return 关键字对应的值域
	 * @since JDK 1.7
	 */
	public Object get(String key,boolean isReset) {
		Object value = null;
		if (sessionExists(key)) {
			value = valueOper.get(key);
			if(isReset){
				valueOper.set(key, value);
			}
		}
		return value;
	}

	/**
	 * get:获取关键字对应的值域，同时修改关键字的超时时间. <br/>
	 * 
	 * @author z1979
	 * @param key
	 *            关键字
	 * @param timeoutSecends
	 *            数据超时时间（秒）
	 * @return 关键字对应的值域
	 * @since JDK 1.7
	 */
	public Object get(String key, long timeoutSecends) {
		Object value = null;
		if (sessionExists(key)) {
			value = valueOper.get(key);
			valueOper.set(key, value, timeoutSecends, TimeUnit.SECONDS);
		}
		return value;
	}

	/**
	 * saveTimeoutTime:保存关键字及对应的值，不指定超时时间. <br/>
	 * 
	 * @param key
	 *            关键字
	 * @param value
	 *            值域
	 */
	public void save(String key, Object value) {

		valueOper.set(key, value);
	}

	/**
	 * saveTimeoutTime:保存关键字及对应的值，同时设置关键字超时时间. <br/>
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
	public void saveTimeoutTime(String key, Object value, long timeoutSecends) {

		valueOper.set(key, value, timeoutSecends, TimeUnit.SECONDS);
	}

	/**
	 * remove:删除关键字. <br/>
	 * 
	 * @author z1979
	 * @param key
	 *            关键字
	 * @since JDK 1.7
	 */
	public void remove(String key) {

		redisTemplate.delete(key);
	}

	/**
	 * updateExpiryDate:更新数据超时时间. <br/>
	 * 
	 * @author z1979
	 * @param key
	 *            关键字
	 * @param timeoutSeconds
	 *            数据超时时间（秒）
	 * @since JDK 1.7
	 */
	public void updateExpiryDate(String key, long timeoutSeconds) {
		redisTemplate.expire(key, timeoutSeconds, TimeUnit.SECONDS);
	}

	/**
	 * 通过pattern获取对象集合
	 * @param pattern 表达式
	 * @return
	 */
	public Set<Object> find(String pattern) {
		return redisTemplate.keys(pattern);
	}
	
	/**
	 * 批量获取key集合对应的值列表
	 * @param keys
	 * @return
	 */
	public List<Object> mulitGet(Set<String> keys) {
		return valueOper.multiGet(keys);
	}
	
	/**
	 * 添加value到key对应的列表
	 * @param key
	 * @param value
	 */
	public void rightPush(String key, Object value) {
		listOper.rightPush(key, value);
		redisTemplate.expire(key, 1L, TimeUnit.DAYS);
	}
	
	/**
	 * 添加value到key对应的列表(设置超时时间)
	 * @param key
	 * @param value
	 */
	public void rightPush(String key, Object value, long timeout) {
		listOper.rightPush(key, value);
		redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
	}
	
	/**
	 * 获取key对应的列表的长度
	 * @param key
	 * @return
	 */
	public long getListSize(String key){
		return listOper.size(key);
	}
	
	public List<?> rang(String key, int start, int end) {
		return listOper.range(key, start, end);
	}
	
	/**
	 * key对应列表中索引对应的值
	 * @param key
	 * @param index
	 * @return
	 */
	public Object index(String key, int index){
		return listOper.index(key, index);
	}
	
	/**
	 * 发布
	 * @param channel  频道
	 * @param message  消息
	 */
	public void convertAndSend(String channel, Object message){
		redisTemplate.convertAndSend(channel, message);
	}
	
	/**
	 * 删除不在指定区间之内的元素。
	 * @param key
	 * @param start
	 * @param end
	 */
	public void trim(String key, int start, int end){
		listOper.trim(key, start, end);
	}

}
