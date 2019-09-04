/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author The Old Man and the Sea
 *
 * 2018年5月31日
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikeVo {
	
	 private Integer uid;
	 private String presentorName;
	 private String header;
	 private Long bzId;
	 private String nickname;
	 private Integer role;
	 private Integer grade;
	 
	 
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * @return the bzId
	 */
	public Long getBzId() {
		return bzId;
	}

	/**
	 * @param bzId the bzId to set
	 */
	public void setBzId(Long bzId) {
		this.bzId = bzId;
	}

	/**
	 * @return the uid
	 */
	public Integer getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}

	/**
	 * @return the presentorName
	 */
	public String getPresentorName() {
		return presentorName;
	}

	/**
	 * @param presentorName the presentorName to set
	 */
	public void setPresentorName(String presentorName) {
		this.presentorName = presentorName;
	}
	 
	 

}
