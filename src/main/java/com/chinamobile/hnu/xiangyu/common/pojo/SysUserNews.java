package com.chinamobile.hnu.xiangyu.common.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-27 11:05:20
 */
@Table(name = "sys_user_news")
public class SysUserNews implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 消息ID
	 */
	@Id
	private Integer id;
	/**
	 * 推送人用户ID
	 */
	private Integer replyId;
	/**
	 * 接收人用户ID
	 */
	private Integer receiveId;
	/**
	 * 具体问题ID
	 */
	private Long bizId;
	/**
	 * 推送消息内容
	 */
	private String content;
	/**
	 * 消息创建时间
	 */
	private Date gmtCreate;
	/**
	 * 最后修改时间
	 */
	private Date gmtModified;
	/**
	 * 消息类别：1-团队消息；2-团言消息；3-问题消息；
	 */
	private Integer category;
	/**
	 * 消息状态：0-未读；1-已读；2-已同意；3-已拒绝；9-已删除；默认-0
	 */
	private Integer status;

	// columns END 数据库字段结束

	// get and set
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	public Integer getReplyId() {
		return this.replyId;
	}

	public void setReceiveId(Integer receiveId) {
		this.receiveId = receiveId;
	}

	public Integer getReceiveId() {
		return this.receiveId;
	}

	public void setBizId(Long bizId) {
		this.bizId = bizId;
	}

	public Long getBizId() {
		return this.bizId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getGmtModified() {
		return this.gmtModified;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getCategory() {
		return this.category;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String toString() {
		return new StringBuffer().append("id=").append(getId()).append(",")
				.append("replyId=").append(getReplyId()).append(",")
				.append("receiveId=").append(getReceiveId()).append(",")
				.append("bizId=").append(getBizId()).append(",")
				.append("content=").append(getContent()).append(",")
				.append("gmtCreate=").append(getGmtCreate()).append(",")
				.append("gmtModified=").append(getGmtModified()).append(",")
				.append("category=").append(getCategory()).append(",")
				.append("status=").append(getStatus()).append(",").toString();
	}

}
