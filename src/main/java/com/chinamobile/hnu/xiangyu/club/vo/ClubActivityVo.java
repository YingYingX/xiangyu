/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.vo;

import java.util.Date;
import java.util.List;

import com.chinamobile.hnu.xiangyu.club.pojo.Photo;
import com.chinamobile.hnu.xiangyu.util.Utils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author The Old Man and the Sea
 *
 * 2018骞�6鏈�1鏃�
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubActivityVo {

	
    private Long id;
    
    private Integer clubId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date gmtCreate;

    private Date gmtModified;

    private Integer presentor;
    
    private String presentorName;
    
    private String header;

    private String title;

    private String content;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date applyDeadline;

    private Integer limitMember;

    private String addressName;

    private Byte status;
    
    private Integer likeAmount;
    
    private Integer commentAmount;
    
    private List<Photo> photos;
    
    private List<LikeVo> likes;
    
    private byte isLike;
    
    private String time;
    
    private ClubVo clubInfo;
    
    private String college;
    
    
	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public ClubVo getClubInfo() {
		return clubInfo;
	}

	public void setClubInfo(ClubVo clubInfo) {
		this.clubInfo = clubInfo;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the likeAmount
	 */
	public Integer getLikeAmount() {
		return likeAmount;
	}

	/**
	 * @param likeAmount the likeAmount to set
	 */
	public void setLikeAmount(Integer likeAmount) {
		this.likeAmount = likeAmount;
	}

	/**
	 * @return the commentAmount
	 */
	public Integer getCommentAmount() {
		return commentAmount;
	}

	/**
	 * @param commentAmount the commentAmount to set
	 */
	public void setCommentAmount(Integer commentAmount) {
		this.commentAmount = commentAmount;
	}

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
		this.time = Utils.formatAgo(gmtCreate);
	}

	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}

	/**
	 * @param gmtModified the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	/**
	 * @return the presentor
	 */
	public Integer getPresentor() {
		return presentor;
	}

	/**
	 * @param presentor the presentor to set
	 */
	public void setPresentor(Integer presentor) {
		this.presentor = presentor;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the applyDeadline
	 */
	public Date getApplyDeadline() {
		return applyDeadline;
	}

	/**
	 * @param applyDeadline the applyDeadline to set
	 */
	public void setApplyDeadline(Date applyDeadline) {
		this.applyDeadline = applyDeadline;
	}

	/**
	 * @return the limitMember
	 */
	public Integer getLimitMember() {
		return limitMember;
	}

	/**
	 * @param limitMember the limitMember to set
	 */
	public void setLimitMember(Integer limitMember) {
		this.limitMember = limitMember;
	}

	/**
	 * @return the addressName
	 */
	public String getAddressName() {
		return addressName;
	}

	/**
	 * @param addressName the addressName to set
	 */
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	/**
	 * @return the status
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * @return the photos
	 */
	public List<Photo> getPhotos() {
		return photos;
	}

	/**
	 * @param photos the photos to set
	 */
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	/**
	 * @return the likes
	 */
	public List<LikeVo> getLikes() {
		return likes;
	}

	/**
	 * @param likes the likes to set
	 */
	public void setLikes(List<LikeVo> likes) {
		this.likes = likes;
	}

	/**
	 * @return the isLike
	 */
	public byte getIsLike() {
		return isLike;
	}

	/**
	 * @param isLike the isLike to set
	 */
	public void setIsLike(byte isLike) {
		this.isLike = isLike;
	}

}
