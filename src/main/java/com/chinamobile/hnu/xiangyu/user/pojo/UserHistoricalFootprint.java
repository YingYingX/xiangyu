package com.chinamobile.hnu.xiangyu.user.pojo;

import java.util.Date;

public class UserHistoricalFootprint {
	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user_historical_footprint.id
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	private Long id;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user_historical_footprint.user_id
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	private Integer userId;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user_historical_footprint.across_id
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	private Integer acrossId;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user_historical_footprint.historical_footprint_id
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	private Long historicalFootprintId;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user_historical_footprint.interview_time
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018fdasd
	 */
	private Date interviewTime;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_historical_footprint.user_id
	 *
	 * @return the value of user_historical_footprint.user_id
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_historical_footprint.user_id
	 *
	 * @param userId
	 *            the value for user_historical_footprint.user_id
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_historical_footprint.across_id
	 *
	 * @return the value of user_historical_footprint.across_id
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public Integer getAcrossId() {
		return acrossId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_historical_footprint.across_id
	 *
	 * @param acrossId
	 *            the value for user_historical_footprint.across_id
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public void setAcrossId(Integer acrossId) {
		this.acrossId = acrossId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * user_historical_footprint.historical_footprint_id
	 *
	 * @return the value of user_historical_footprint.historical_footprint_id
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public Long getHistoricalFootprintId() {
		return historicalFootprintId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * user_historical_footprint.historical_footprint_id
	 *
	 * @param historicalFootprintId
	 *            the value for
	 *            user_historical_footprint.historical_footprint_id
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public void setHistoricalFootprintId(Long historicalFootprintId) {
		this.historicalFootprintId = historicalFootprintId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user_historical_footprint.interview_time
	 *
	 * @return the value of user_historical_footprint.interview_time
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public Date getInterviewTime() {
		return interviewTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user_historical_footprint.interview_time
	 *
	 * @param interviewTime
	 *            the value for user_historical_footprint.interview_time
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public void setInterviewTime(Date interviewTime) {
		this.interviewTime = interviewTime;
	}
}