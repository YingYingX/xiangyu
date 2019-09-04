/**  

 * <p>Title: ClubSpeackVo.java</p>  

 * <p>Description: </p>  

 * <p>Copyright: Copyright douziit(c) 2017</p>  

 * @author lh  

 * @date 2018年6月20日  

 * @version 1.0  

 */
package com.chinamobile.hnu.xiangyu.web.api.vo;

import java.util.Date;
import java.util.List;

import com.chinamobile.hnu.xiangyu.club.pojo.Photo;
import com.chinamobile.hnu.xiangyu.util.Utils;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * <p>
 * Title: ClubSpeackVo.java
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author lh
 * 
 * @date 2018年6月20日
 * 
 * @version 1.0
 */
public class AclubActivityInfoVo {

	private Long id;

	private Integer clubId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date gmtCreate;

	private Date gmtModified;

	private Integer presentor;

	private String presentorName;

	private String header;

	private String title;

	private String content;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date applyDeadline;

	private Integer limitMember;

	private String addressName;

	private Byte status;

	private Integer likeAmount;

	private Integer commentAmount;

	private List<Photo> photos;

	private String time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getClubId() {
		return clubId;
	}

	public void setClubId(Integer clubId) {
		this.clubId = clubId;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
		this.time = Utils.formatAgo(gmtCreate);
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

	public String getPresentorName() {
		return presentorName;
	}

	public void setPresentorName(String presentorName) {
		this.presentorName = presentorName;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getApplyDeadline() {
		return applyDeadline;
	}

	public void setApplyDeadline(Date applyDeadline) {
		this.applyDeadline = applyDeadline;
	}

	public Integer getLimitMember() {
		return limitMember;
	}

	public void setLimitMember(Integer limitMember) {
		this.limitMember = limitMember;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
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

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
