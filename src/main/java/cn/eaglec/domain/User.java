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

import cn.eaglec.dto.BaseEntity;

@Entity
@Table(name = "t_sys_user", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 编号.
	 */
	private String id;
	
	private String fullName;
	
	private String userName;
	
	private String password;
	
	private String linkPhone;
	
	private String officePhone;
	/**
	 * 授权时显示的手机号码 （后期添加手机重复校验后，该字段就会变为多余字段）
	 */
	private String authPhone;
	@Column(name = "auth_phone", length = 20)
	public String getAuthPhone() {
		return authPhone;
	}

	public void setAuthPhone(String authPhone) {
		this.authPhone = authPhone;
	}

	private String email;
	
	//private User superiors;
	//子用户
	//private Set<User> childrenSet;
	
	
	//private Company company;
	
	private String companyId;
	
	private String companyName;
	
	/* 获取网站关联的机构网址 */
	private String customerSiteLink;
	
	//private Set<Role> roleSet;
	
	private Date updateDatetime;
	
	private Date createDatetime;
	
	//private User creator;
	private String creatorId;
	
	//private UserIndividuation individuation;
	private String userIndividuationId;
	
	
	//用户父ID
	private String parentId;
	
	//层代码(备用)
	private String ownParentIds;
	
	//是否为机构管理员  1则是  其他 不是
	private Integer isOrgAdmin;
	
	/**
	 * 图片路径(虚拟目录  用于展示)
	 */
	private String imgPath;
	/**
	 * 图片实际目录
	 */
	private String imgRealPath;
	
	/**
	 * 用户头像
	 */
	private String headerImg;
	
	/**
	 * 子用户个数
	 */
	private Integer childCount;
	
	/**
	 * 用户对应角色ID(多个)
	 */
	private String roleIdsStr;
	
	
	/**
	 * 用户对应角色ID(多个)
	 */
	private String departmentId;
	
	//部门类型 1表示私有部门 2表示通用部门
	private String departType;

	// 部门名称
	private String departName;
	
	/**
	 * 工程人员的关联机构ID
	 * @return
	 */
	private String ownedCompanyIds;
	
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
	@Column(name = "individuation_id", length = 36)
	public String getUserIndividuationId() {
		return userIndividuationId;
	}

	public void setUserIndividuationId(String userIndividuationId) {
		this.userIndividuationId = userIndividuationId;
	}
	

	/*@ManyToOne()
    @JoinColumn(name="creator")
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}*/
	@Transient
	public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    @Transient
    public String getCustomerSiteLink() {
		return customerSiteLink;
	}

	public void setCustomerSiteLink(String customerSiteLink) {
		this.customerSiteLink = customerSiteLink;
	}

	@Column(name = "creator_id", length = 36)
	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 7)
	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", length = 7)
	public Date getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	@Column(name = "full_Name",length = 50)
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "user_name", nullable = false, length = 50)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "password",nullable = false, length = 100)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name = "link_phone", length = 20)
	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	
	@Column(name = "office_phone", length = 20)
	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	@Column(name = "email", length = 256)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	
	
}
