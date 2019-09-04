/**  

 * <p>Title: AskInfoVo.java</p>  

 * <p>Description: </p>  

 * <p>Copyright: Copyright douziit(c) 2017</p>  

 * @author lh  

 * @date 2018年6月6日  

 * @version 1.0  

 */
package com.chinamobile.hnu.xiangyu.ask.vo;

import java.util.Date;
import java.util.List;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskLabel;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskPhoto;
import com.chinamobile.hnu.xiangyu.club.vo.LikeVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * <p>
 * Title: AskInfoVo.java
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AskInfoVo {
	private Long id;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	private Date gmtCreate;

	private Date gmtModified;
	
	private String name;

	private String title;

	private Integer presentor;

	private String content;

	private Integer likeAmount;

	private Integer commentAmount;

	private Integer visitAmount;

	private Integer yesterdayVisitAmount;

	private Byte anonymity;

	private Integer specialId;

	private Byte status;

	private String header;

	private String nn;

	private Integer uid;

	private String time;
	private List<LikeVo> likes;
	
	
	

	/**
	 * 1-收藏 ，null-未收藏
	 */
	private Integer isCollect;
	/**
	 * 1-点赞过，null-未点赞
	 */
	private Integer userLike;

	// 无用
	private String ans;

	private List<AskLabel> labels;

	private List<AskPhoto> photos;

	private List<AskCommentVo> commentVos;

	private Integer roleId;

	// private List<String> labels;
	//
	// private String label;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getIsCollect() {
		return isCollect;
	}

	public List<LikeVo> getLikes() {
		return likes;
	}

	public void setLikes(List<LikeVo> likes) {
		this.likes = likes;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	public void setIsCollect(Integer isCollect) {
		this.isCollect = isCollect;
	}

	public Integer getUserLike() {
		return userLike;
	}

	public void setUserLike(Integer userLike) {
		this.userLike = userLike;
	}

	public List<AskLabel> getLabels() {
		return labels;
	}

	public void setLabels(List<AskLabel> labels) {
		this.labels = labels;
	}

	public List<AskCommentVo> getCommentVos() {
		return commentVos;
	}

	public void setCommentVos(List<AskCommentVo> commentVos) {
		this.commentVos = commentVos;
	}

	public List<AskPhoto> getPhotos() {
		return photos;
	}

	public void setPhotos(List<AskPhoto> photos) {
		this.photos = photos;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getVisitAmount() {
		return visitAmount;
	}

	public void setVisitAmount(Integer visitAmount) {
		this.visitAmount = visitAmount;
	}

	public Integer getYesterdayVisitAmount() {
		return yesterdayVisitAmount;
	}

	public void setYesterdayVisitAmount(Integer yesterdayVisitAmount) {
		this.yesterdayVisitAmount = yesterdayVisitAmount;
	}

	public Byte getAnonymity() {
		return anonymity;
	}

	public void setAnonymity(Byte anonymity) {
		this.anonymity = anonymity;
	}

	public Integer getSpecialId() {
		return specialId;
	}

	public void setSpecialId(Integer specialId) {
		this.specialId = specialId;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

}
