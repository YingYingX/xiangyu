package com.chinamobile.hnu.xiangyu.user.pojo;

import java.util.Date;

public class UserFavorite {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_favorite.id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_favorite.user_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_favorite.category
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    private Byte category;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_favorite.biz_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    private Long bizId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_favorite.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    private Date gmtCreate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_favorite.id
     *
     * @return the value of user_favorite.id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_favorite.id
     *
     * @param id the value for user_favorite.id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_favorite.user_id
     *
     * @return the value of user_favorite.user_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_favorite.user_id
     *
     * @param userId the value for user_favorite.user_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_favorite.category
     *
     * @return the value of user_favorite.category
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Byte getCategory() {
        return category;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_favorite.category
     *
     * @param category the value for user_favorite.category
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setCategory(Byte category) {
        this.category = category;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_favorite.biz_id
     *
     * @return the value of user_favorite.biz_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Long getBizId() {
        return bizId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_favorite.biz_id
     *
     * @param bizId the value for user_favorite.biz_id
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_favorite.gmt_create
     *
     * @return the value of user_favorite.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_favorite.gmt_create
     *
     * @param gmtCreate the value for user_favorite.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}