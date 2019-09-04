/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.vo;

import java.util.Date;
import java.util.List;

import com.chinamobile.hnu.xiangyu.club.pojo.ClubVoteQuestion;
import com.chinamobile.hnu.xiangyu.util.Utils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author The Old Man and the Sea
 *
 * 2018年6月5日
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubVoteVo {
	
	    private Long id;

	    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	    private Date gmtCreate;

	    private Date gmtModified;

	    private Integer clubId;

	    private Integer presentor;
	    
	    private String presentorName;
	    
	    private String header;

	    private String name;

	    private String introduction;

	    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
	    private Date deadline;

	    private Byte status;
	    
	    private String content;
	    
	    private byte isVote;
	    
	    private List<ClubVoteQuestion> questions;
	    
	    private String time;
	    

	    
		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		/**
		 * @return the isVote
		 */
		public byte getIsVote() {
			return isVote;
		}

		/**
		 * @param isVote the isVote to set
		 */
		public void setIsVote(byte isVote) {
			this.isVote = isVote;
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
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the introduction
		 */
		public String getIntroduction() {
			return introduction;
		}

		/**
		 * @param introduction the introduction to set
		 */
		public void setIntroduction(String introduction) {
			this.introduction = introduction;
		}

		/**
		 * @return the deadline
		 */
		public Date getDeadline() {
			return deadline;
		}

		/**
		 * @param deadline the deadline to set
		 */
		public void setDeadline(Date deadline) {
			this.deadline = deadline;
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
		 * @return the questions
		 */
		public List<ClubVoteQuestion> getQuestions() {
			return questions;
		}

		/**
		 * @param questions the questions to set
		 */
		public void setQuestions(List<ClubVoteQuestion> questions) {
			this.questions = questions;
		}
	    
	    
}
