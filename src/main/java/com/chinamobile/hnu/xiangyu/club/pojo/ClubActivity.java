package com.chinamobile.hnu.xiangyu.club.pojo;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class ClubActivity {
	
	public interface Add{}
	public interface Update{}
	
	@NotNull(message="活动id不能为空",groups={Update.class})
    private Long id;
	
	@NotNull(message="团队id不能为空",groups={Add.class})
	private Integer clubId;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer presentor;

    @NotBlank(message="活动标题不能为空",groups={Add.class,Update.class})
    @Length(max=32,message="活动标题字符过长",groups={Add.class,Update.class})
    private String title;

    @NotBlank(message="活动内容不能为空",groups={Add.class,Update.class})
    @Length(max=512,message="活动内容字符过长",groups={Add.class,Update.class})
    private String content;

    @NotNull(message="活动截止报名时间不能为空",groups={Add.class,Update.class})
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date applyDeadline;

    @Max(value=99999,message="人数太多了吧",groups={Add.class,Update.class})
    private Integer limitMember;

    @NotBlank(message="活动地址不能为空",groups={Add.class,Update.class})
    @Length(max=128,message="活动地址字符过长",groups={Add.class,Update.class})
    private String addressName;

    private Byte status;
    
    private Integer likeAmount;
    
    private Integer commentAmount;
    
    private List<Photo> photos;
    

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
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_activity.id
     *
     * @return the value of club_activity.id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_activity.id
     *
     * @param id the value for club_activity.id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_activity.gmt_create
     *
     * @return the value of club_activity.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_activity.gmt_create
     *
     * @param gmtCreate the value for club_activity.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_activity.gmt_modified
     *
     * @return the value of club_activity.gmt_modified
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_activity.gmt_modified
     *
     * @param gmtModified the value for club_activity.gmt_modified
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_activity.presentor
     *
     * @return the value of club_activity.presentor
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Integer getPresentor() {
        return presentor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_activity.presentor
     *
     * @param presentor the value for club_activity.presentor
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setPresentor(Integer presentor) {
        this.presentor = presentor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_activity.title
     *
     * @return the value of club_activity.title
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_activity.title
     *
     * @param title the value for club_activity.title
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_activity.content
     *
     * @return the value of club_activity.content
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_activity.content
     *
     * @param content the value for club_activity.content
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_activity.apply_deadline
     *
     * @return the value of club_activity.apply_deadline
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Date getApplyDeadline() {
        return applyDeadline;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_activity.apply_deadline
     *
     * @param applyDeadline the value for club_activity.apply_deadline
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setApplyDeadline(Date applyDeadline) {
        this.applyDeadline = applyDeadline;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_activity.limit_member
     *
     * @return the value of club_activity.limit_member
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Integer getLimitMember() {
        return limitMember;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_activity.limit_member
     *
     * @param limitMember the value for club_activity.limit_member
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setLimitMember(Integer limitMember) {
        this.limitMember = limitMember;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_activity.address_name
     *
     * @return the value of club_activity.address_name
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public String getAddressName() {
        return addressName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_activity.address_name
     *
     * @param addressName the value for club_activity.address_name
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setAddressName(String addressName) {
        this.addressName = addressName == null ? null : addressName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_activity.status
     *
     * @return the value of club_activity.status
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_activity.status
     *
     * @param status the value for club_activity.status
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setStatus(Byte status) {
        this.status = status;
    }
}