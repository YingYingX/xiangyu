/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.vo;

import java.util.Date;

import com.chinamobile.hnu.xiangyu.util.Utils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author The Old Man and the Sea
 *
 * 2018年6月5日
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubNoticeVo {

	
    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date gmtCreate;

    private Date gmtModified;

    private Integer presentor;
    
    private String presentorName;

    private Integer clubId;
    
    private String title;

    private String content;

    private Byte status;

    private String time;
    
    
    
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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
