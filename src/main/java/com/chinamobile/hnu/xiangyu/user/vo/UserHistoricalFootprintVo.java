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
public class UserHistoricalFootprintVo {

	private Integer id;

	private Integer userId;

	private Integer acrossId;

	private Long historicalFootprintId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date interviewTime;

	private Object obj;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the acrossId
	 */
	public Integer getAcrossId() {
		return acrossId;
	}

	/**
	 * @param acrossId
	 *            the acrossId to set
	 */
	public void setAcrossId(Integer acrossId) {
		this.acrossId = acrossId;
	}

	/**
	 * @return the historicalFootprintId
	 */
	public Long getHistoricalFootprintId() {
		return historicalFootprintId;
	}

	/**
	 * @param historicalFootprintId
	 *            the historicalFootprintId to set
	 */
	public void setHistoricalFootprintId(Long historicalFootprintId) {
		this.historicalFootprintId = historicalFootprintId;
	}

	/**
	 * @return the interviewTime
	 */
	public Date getInterviewTime() {
		return interviewTime;
	}

	/**
	 * @param interviewTime
	 *            the interviewTime to set
	 */
	public void setInterviewTime(Date interviewTime) {
		this.interviewTime = interviewTime;
	}

	/**
	 * @return the obj
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * @param obj
	 *            the obj to set
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

}
