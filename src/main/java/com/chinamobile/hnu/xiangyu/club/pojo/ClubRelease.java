/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.pojo;

import java.util.Date;

/**
 * @author The Old Man and the Sea
 *
 * 2018年6月6日
 */
public class ClubRelease {

	private Long id;
	private Long bzid;
	private Byte type;
	private Date gmtCreate;
	private Integer clubId;
	
	/**
	 * @return the clubId
	 */
	public Integer getClubId() {
		return clubId;
	}
	/**
	 * @param clubId the clubId to set
	 */
	public void setClubId(Integer clubId) {
		this.clubId = clubId;
	}
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
	 * @return the bzid
	 */
	public Long getBzid() {
		return bzid;
	}
	/**
	 * @param bzid the bzid to set
	 */
	public void setBzid(Long bzid) {
		this.bzid = bzid;
	}
	/**
	 * @return the type
	 */
	public Byte getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Byte type) {
		this.type = type;
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
	
	
	
}
