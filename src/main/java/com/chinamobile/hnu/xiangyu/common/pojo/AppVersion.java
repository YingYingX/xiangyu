package com.chinamobile.hnu.xiangyu.common.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-09-21 09:35:44
 */
@Table(name = "app_version")
public class AppVersion implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 版本号
	 */
	@NotBlank
	private String vercode;
	/**
	 * 版本名称
	 */
	@NotBlank
	private String vername;
	/**
	 * 版本描述
	 */
	@NotBlank
	private String verdesc;
	/**
	 * 文件大小
	 */
	@NotNull
	private Integer appsize;
	/**
	 * 文件下载地址
	 */
	@NotBlank
	private String apppath;
	/**
	 * 更新时间
	 */
	private Date uptime;
	/**
	 * APP类型；1.安卓；2.ios
	 */
	private Integer apptype;

	// columns END 数据库字段结束

	// get and set
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setVercode(String vercode) {
		this.vercode = vercode;
	}

	public String getVercode() {
		return this.vercode;
	}

	public void setVername(String vername) {
		this.vername = vername;
	}

	public String getVername() {
		return this.vername;
	}

	public void setVerdesc(String verdesc) {
		this.verdesc = verdesc;
	}

	public String getVerdesc() {
		return this.verdesc;
	}

	public void setAppsize(Integer appsize) {
		this.appsize = appsize;
	}

	public Integer getAppsize() {
		return this.appsize;
	}

	public void setApppath(String apppath) {
		this.apppath = apppath;
	}

	public String getApppath() {
		return this.apppath;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	public Date getUptime() {
		return this.uptime;
	}

	public Integer getApptype() {
		return apptype;
	}

	public void setApptype(Integer apptype) {
		this.apptype = apptype;
	}

	public String toString() {
		return new StringBuffer().append("id=").append(getId()).append(",")
				.append("vercode=").append(getVercode()).append(",")
				.append("vername=").append(getVername()).append(",")
				.append("verdesc=").append(getVerdesc()).append(",")
				.append("appsize=").append(getAppsize()).append(",")
				.append("apppath=").append(getApppath()).append(",")
				.append("uptime=").append(getUptime()).append(",")
				.append("apptype=").append(getApptype()).append(",").toString();
	}

}
