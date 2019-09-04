/**  

 * <p>Title: UserVo.java</p>  

 * <p>Description: </p>  

 * <p>Copyright: Copyright douziit(c) 2017</p>  

 * @author lh  

 * @date 2018年6月19日  

 * @version 1.0  

 */
package com.chinamobile.hnu.xiangyu.social.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * <p>
 * Title: UserVo.java
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author lh
 * 
 * @date 2018年6月19日
 * 
 * @version 1.0
 */
public class SocialVo {

	private Integer id;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Date gmtCreate;

	private String nickname;

	private String realname;

	private String telephone;

	private String constellation;

	private Integer age;

	private Integer fans;

	private Integer roleId;
	private Integer concerned;
	private Date birthday;
	private String descroption;
	private Double longitude;
	private Double latitude;
	private String head;
	private Integer sex;
	private String college;
	/**
	 * 是否禁用此用户，0-不禁用；1-禁用
	 */
	private Byte enable;

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public Integer getConcerned() {
		return concerned;
	}

	public void setConcerned(Integer concerned) {
		this.concerned = concerned;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getDescroption() {
		return descroption;
	}

	public void setDescroption(String descroption) {
		this.descroption = descroption;
	}

	public Double getLongitude() { return longitude; }

	public void setLongitude(Double longitude) { this.longitude = longitude; }

	public Double getLatitude() { return latitude; }

	public void setLatitude(Double latitude) { this.latitude = latitude; }

	public Byte getEnable() {
		return enable;
	}

	public void setEnable(Byte enable) {
		this.enable = enable;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Integer getSex() { return sex; }

	public void setSex(Integer sex) { this.sex = sex; }

    public String getCollege() { return college; }

    public void setCollege(String college) { this.college = college; }
}
