package com.chinamobile.hnu.xiangyu.auth.pojo;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-15 14:52:47
 */
@Table(name = "auth_role")
public class AuthRole implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * roleId
	 */
	@Id
	private Integer roleId;
	/**
	 * roleName
	 */
	private String roleName;

	// columns END 数据库字段结束

	// get and set
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public String toString() {
		return new StringBuffer().append("roleId=").append(getRoleId())
				.append(",").append("roleName=").append(getRoleName())
				.append(",").toString();
	}

}
