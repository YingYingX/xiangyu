/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author The Old Man and the Sea
 *
 * 2018年6月6日
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubReleaseVo {

	private Long id;
	private Long bzid;
	private Byte type;
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
