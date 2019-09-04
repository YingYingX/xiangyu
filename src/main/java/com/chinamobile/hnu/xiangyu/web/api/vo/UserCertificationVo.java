/**  

 * <p>Title: UserCertificationVo.java</p>  

 * <p>Description: </p>  

 * <p>Copyright: Copyright douziit(c) 2017</p>  

 * @author lh  

 * @date 2018年6月22日  

 * @version 1.0  

 */
package com.chinamobile.hnu.xiangyu.web.api.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * <p>
 * Title: UserCertificationVo.java
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author lh
 * 
 * @date 2018年6月22日
 * 
 * @version 1.0
 */
public class UserCertificationVo {

	private Integer userId;

	private String name;

	private String phone;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date gmtCreate;

	private Integer status;

	private String college;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

}
