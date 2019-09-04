package com.chinamobile.hnu.xiangyu.auth.pojo;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-15 14:52:48
 */
@Table(name = "auth_user_role")
public class AuthUserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * roleId
	 */
	@Id
	private Integer roleId;
	/**
	 * userId
	 */
	@Id
	private Integer userId;

	// columns END 数据库字段结束

	// get and set
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public String toString() {
		return new StringBuffer().append("roleId=").append(getRoleId())
				.append(",").append("userId=").append(getUserId()).append(",")
				.toString();
	}

}
