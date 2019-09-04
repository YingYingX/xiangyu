package com.chinamobile.hnu.xiangyu.auth.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-15 14:52:47
 */
@Table(name = "auth_resources")
public class AuthResources implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * id
	 */
	@Id
	private Integer id;
	/**
	 * resourceName
	 */
	private String resourceName;
	/**
	 * resourceDesc
	 */
	private String resourceDesc;
	/**
	 * resourceIco
	 */
	private String resourceIco;
	/**
	 * parentId
	 */
	private Integer parentId;
	/**
	 * resourceUrl
	 */
	private String resourceUrl;
	/**
	 * sortNum
	 */
	private Integer sortNum;
	/**
	 * createTime
	 */
	private Date createTime;
	/**
	 * status
	 */
	private Integer status;

	// columns END 数据库字段结束

	// get and set
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	public String getResourceDesc() {
		return this.resourceDesc;
	}

	public void setResourceIco(String resourceIco) {
		this.resourceIco = resourceIco;
	}

	public String getResourceIco() {
		return this.resourceIco;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getResourceUrl() {
		return this.resourceUrl;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public Integer getSortNum() {
		return this.sortNum;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String toString() {
		return new StringBuffer().append("id=").append(getId()).append(",")
				.append("resourceName=").append(getResourceName()).append(",")
				.append("resourceDesc=").append(getResourceDesc()).append(",")
				.append("resourceIco=").append(getResourceIco()).append(",")
				.append("parentId=").append(getParentId()).append(",")
				.append("resourceUrl=").append(getResourceUrl()).append(",")
				.append("sortNum=").append(getSortNum()).append(",")
				.append("createTime=").append(getCreateTime()).append(",")
				.append("status=").append(getStatus()).append(",").toString();
	}

}
