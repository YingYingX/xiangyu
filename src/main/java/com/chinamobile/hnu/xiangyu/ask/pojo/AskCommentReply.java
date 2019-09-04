package com.chinamobile.hnu.xiangyu.ask.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AskCommentReply {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ask_comment_reply.id
	 * 
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ask_comment_reply.gmt_create
	 * 
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date gmtCreate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ask_comment_reply.gmt_modified
	 * 
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	private Date gmtModified;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ask_comment_reply.presentor
	 * 
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	private Integer presentor;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ask_comment_reply.comment_id
	 * 
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	private Long commentId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ask_comment_reply.content
	 * 
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	private String content;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ask_comment_reply.status
	 * 
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	private Byte status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ask_comment_reply.anonymity
	 * 
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	private Byte anonymity;

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ask_comment_reply.id
	 * 
	 * @return the value of ask_comment_reply.id
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ask_comment_reply.id
	 * 
	 * @param id
	 *            the value for ask_comment_reply.id
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ask_comment_reply.gmt_create
	 * 
	 * @return the value of ask_comment_reply.gmt_create
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ask_comment_reply.gmt_create
	 * 
	 * @param gmtCreate
	 *            the value for ask_comment_reply.gmt_create
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ask_comment_reply.gmt_modified
	 * 
	 * @return the value of ask_comment_reply.gmt_modified
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public Date getGmtModified() {
		return gmtModified;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ask_comment_reply.gmt_modified
	 * 
	 * @param gmtModified
	 *            the value for ask_comment_reply.gmt_modified
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ask_comment_reply.presentor
	 * 
	 * @return the value of ask_comment_reply.presentor
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public Integer getPresentor() {
		return presentor;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ask_comment_reply.presentor
	 * 
	 * @param presentor
	 *            the value for ask_comment_reply.presentor
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public void setPresentor(Integer presentor) {
		this.presentor = presentor;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ask_comment_reply.comment_id
	 * 
	 * @return the value of ask_comment_reply.comment_id
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public Long getCommentId() {
		return commentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ask_comment_reply.comment_id
	 * 
	 * @param commentId
	 *            the value for ask_comment_reply.comment_id
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ask_comment_reply.content
	 * 
	 * @return the value of ask_comment_reply.content
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public String getContent() {
		return content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ask_comment_reply.content
	 * 
	 * @param content
	 *            the value for ask_comment_reply.content
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ask_comment_reply.status
	 * 
	 * @return the value of ask_comment_reply.status
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ask_comment_reply.status
	 * 
	 * @param status
	 *            the value for ask_comment_reply.status
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ask_comment_reply.anonymity
	 * 
	 * @return the value of ask_comment_reply.anonymity
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public Byte getAnonymity() {
		return anonymity;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ask_comment_reply.anonymity
	 * 
	 * @param anonymity
	 *            the value for ask_comment_reply.anonymity
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	public void setAnonymity(Byte anonymity) {
		this.anonymity = anonymity;
	}
}