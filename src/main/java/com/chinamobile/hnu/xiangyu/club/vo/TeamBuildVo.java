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
 * 2018年5月31日
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamBuildVo {
	
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

	    private Byte recommend;

	    private Integer likeAmount;

	    private Integer commentAmount;

	    private Byte status;

	    private List<Photo> photos;
	    
	    private List<LikeVo> likes;
	    
	    private byte isLike;
	    
	    private String time;

	    
	    
		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
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
		 * @return the recommend
		 */
		public Byte getRecommend() {
			return recommend;
		}

		/**
		 * @param recommend the recommend to set
		 */
		public void setRecommend(Byte recommend) {
			this.recommend = recommend;
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
	    
	    
	    
	    
	    
}
