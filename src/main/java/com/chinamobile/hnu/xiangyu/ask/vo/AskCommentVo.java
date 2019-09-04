/**  

 * <p>Title: AskCommentVo.java</p>  

 * <p>Description: </p>  

 * <p>Copyright: Copyright douziit(c) 2017</p>  

 * @author lh  

 * @date 2018年6月6日  

 * @version 1.0  

 */
package com.chinamobile.hnu.xiangyu.ask.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * <p>
 * Title: AskCommentVo.java
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author lh
 * 
 * @date 2018年6月6日
 * 
 * @version 1.0
 */
public class AskCommentVo {

	private Long id;

	private Long askId;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Date gmtCreate;

	private Date gmtModified;

	private Integer presentor;

	private String content;

	private Integer likeAmount;

	private Integer commentAmount;

	private Byte status;

	private Byte anonymity;

	private String header;

	private String nn;

	private Integer uid;

	private String time;

	// 是否顶，1-顶 null-未顶
	private Integer isTop;

	private Integer roleId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAskId() {
		return askId;
	}

	public void setAskId(Long askId) {
		this.askId = askId;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Integer getPresentor() {
		return presentor;
	}

	public void setPresentor(Integer presentor) {
		this.presentor = presentor;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getLikeAmount() {
		return likeAmount;
	}

	public void setLikeAmount(Integer likeAmount) {
		this.likeAmount = likeAmount;
	}

	public Integer getCommentAmount() {
		return commentAmount;
	}

	public void setCommentAmount(Integer commentAmount) {
		this.commentAmount = commentAmount;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getAnonymity() {
		return anonymity;
	}

	public void setAnonymity(Byte anonymity) {
		this.anonymity = anonymity;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getNn() {
		return nn;
	}

	public void setNn(String nn) {
		this.nn = nn;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
