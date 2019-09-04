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
public class UserCertificationInfoVo {

	private Integer userId;

	private String idcardNumber;

	private String idcardPositive;

	private String idcardNegative;

	private String name;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date idcardValidityFrom;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date idcardValidityTo;

	private Byte status;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date gmtCreate;

	private Integer sex;

	private String header;

	private String telephone;

	private String experience;

	private String college;

	private Integer grade;

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getIdcardNumber() {
		return idcardNumber;
	}

	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}

	public String getIdcardPositive() {
		return idcardPositive;
	}

	public void setIdcardPositive(String idcardPositive) {
		this.idcardPositive = idcardPositive;
	}

	public String getIdcardNegative() {
		return idcardNegative;
	}

	public void setIdcardNegative(String idcardNegative) {
		this.idcardNegative = idcardNegative;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getIdcardValidityFrom() {
		return idcardValidityFrom;
	}

	public void setIdcardValidityFrom(Date idcardValidityFrom) {
		this.idcardValidityFrom = idcardValidityFrom;
	}

	public Date getIdcardValidityTo() {
		return idcardValidityTo;
	}

	public void setIdcardValidityTo(Date idcardValidityTo) {
		this.idcardValidityTo = idcardValidityTo;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

}
