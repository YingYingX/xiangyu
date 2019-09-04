package com.chinamobile.hnu.xiangyu.club.pojo;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class ClubActivityComment {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column club_activity_comment.id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column club_activity_comment.activity_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    @NotNull(message="活动id不能为空")
    private Long activityId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column club_activity_comment.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    private Date gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column club_activity_comment.gmt_modified
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    private Date gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column club_activity_comment.presentor
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    private Integer presentor;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column club_activity_comment.content
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    @NotBlank(message="评论内容不能为空")
    @Length(max=128,message="评论内容字符过长")
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column club_activity_comment.status
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    private Byte status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_activity_comment.id
     *
     * @return the value of club_activity_comment.id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_activity_comment.id
     *
     * @param id the value for club_activity_comment.id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_activity_comment.activity_id
     *
     * @return the value of club_activity_comment.activity_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Long getActivityId() {
        return activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_activity_comment.activity_id
     *
     * @param activityId the value for club_activity_comment.activity_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_activity_comment.gmt_create
     *
     * @return the value of club_activity_comment.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_activity_comment.gmt_create
     *
     * @param gmtCreate the value for club_activity_comment.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_activity_comment.gmt_modified
     *
     * @return the value of club_activity_comment.gmt_modified
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_activity_comment.gmt_modified
     *
     * @param gmtModified the value for club_activity_comment.gmt_modified
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_activity_comment.presentor
     *
     * @return the value of club_activity_comment.presentor
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Integer getPresentor() {
        return presentor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_activity_comment.presentor
     *
     * @param presentor the value for club_activity_comment.presentor
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setPresentor(Integer presentor) {
        this.presentor = presentor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_activity_comment.content
     *
     * @return the value of club_activity_comment.content
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_activity_comment.content
     *
     * @param content the value for club_activity_comment.content
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column club_activity_comment.status
     *
     * @return the value of club_activity_comment.status
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column club_activity_comment.status
     *
     * @param status the value for club_activity_comment.status
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setStatus(Byte status) {
        this.status = status;
    }
}