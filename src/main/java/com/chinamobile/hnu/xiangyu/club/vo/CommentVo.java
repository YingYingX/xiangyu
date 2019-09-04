/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.vo;

import java.util.Date;
import java.util.List;

import com.chinamobile.hnu.xiangyu.util.Utils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author The Old Man and the Sea
 *
 * 2018年5月30日
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentVo {
	
	 	private Long id;

	    private Long bzId;

	    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
	    private Date gmtCreate;

	    private Date gmtModified;

	    private Integer presentor;
	    
	    private String presentorName;
	    
	    private String header;

	    private String content;

	    private Byte status;
	    private List<LikeVo> likes;
	    
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
		 * @return the bzId
		 */
		public Long getBzId() {
			return bzId;
		}

		/**
		 * @param bzId the bzId to set
		 */
		public void setBzId(Long bzId) {
			this.bzId = bzId;
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
	    

}
