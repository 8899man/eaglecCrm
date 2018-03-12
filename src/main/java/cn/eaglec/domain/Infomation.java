package cn.eaglec.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "t_sys_info", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Infomation implements Serializable {


	private static final long serialVersionUID = 1L;
	
	/**
	 * 编号.
	 */
	private String id;
	
	/**
	 * 投诉人姓名
	 */
	private String userName;
	
	/**
	 * 投诉信息
	 */
	private String infomation;
	
	/**
	 * 投诉时间
	 */
	private String date;
	
	/**
	 * 客户所在的公司
	 */
	private String company;
	
	/**
	 * 客户的联系方式
	 */
	private String tel;
	
	/**
	 * 客户的地址
	 */
	private String address;
	
	@Column(name = "info",length = 50)
	public String getInfomation() {
		return infomation;
	}

	public void setInfomation(String infomation) {
		this.infomation = infomation;
	}
	@Column(name = "date",length = 50)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	@Column(name = "company",length = 50)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	@Column(name = "tel",length = 50)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	
	@Column(name = "address",length = 50)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
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


}
