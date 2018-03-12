package cn.eaglec.dto;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;


import com.alibaba.fastjson.annotation.JSONField;

public class BaseEntity {


	private static final long serialVersionUID = 1L;
	/**
	 * 编号.
	 */
	private java.lang.String id;

	
	/**
	 * 创建人
	 */
	private String baseCreater;
	
	
	/**
	 * 创建时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date baseCreateTime;
	
	
	
	/**
	 * 最后更新人
	 */
	private String baseLastUpdatePerson;
	/**
	 * 最后更新时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date baseLastUpdateTime;
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		if (!StringUtils.isBlank(this.id)) {
			return this.id;
		}
		return UUID.randomUUID().toString();
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	@Column(name = "base_creater", length = 36)
	public String getBaseCreater() {
		return baseCreater;
	}

	public void setBaseCreater(String baseCreater) {
		this.baseCreater = baseCreater;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "base_create_time", length = 7)
	public Date getBaseCreateTime() {
		return baseCreateTime;
	}

	public void setBaseCreateTime(Date baseCreateTime) {
		if(baseCreateTime == null){
			this.baseCreateTime = new Date();
		}else{
			this.baseCreateTime = baseCreateTime;
		}
	}
	@Column(name = "base_last_update_person", length = 36)
	public String getBaseLastUpdatePerson() {
		return baseLastUpdatePerson;
	}

	public void setBaseLastUpdatePerson(String baseLastUpdatePerson) {
		this.baseLastUpdatePerson = baseLastUpdatePerson;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "base_last_update_time", length = 7)
	public Date getBaseLastUpdateTime() {
		return baseLastUpdateTime;
	}

	public void setBaseLastUpdateTime(Date baseLastUpdateTime) {
		if(baseLastUpdateTime == null){
			this.baseLastUpdateTime = new Date();
		}else{
			this.baseLastUpdateTime = baseLastUpdateTime;
		}
	}


}
