/**
 * 
 */
package com.chinamobile.hnu.xiangyu.user.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author The Old Man and the Sea
 *
 *         2018年6月8日
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFavoriteVo {

	private Long id;

	private Integer userId;

	private Byte category;

	private Long bizId;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date gmtCreate;
	
	private Object obj;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the category
	 */
	public Byte getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Byte category) {
		this.category = category;
	}

	/**
	 * @return the bizId
	 */
	public Long getBizId() {
		return bizId;
	}

	/**
	 * @param bizId the bizId to set
	 */
	public void setBizId(Long bizId) {
		this.bizId = bizId;
	}

	/**
	 * @return the gmtCreate
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/**
	 * @param gmtCreate the gmtCreate to set
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/**
	 * @return the obj
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * @param obj the obj to set
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
}
