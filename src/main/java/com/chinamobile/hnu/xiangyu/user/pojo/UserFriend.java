package com.chinamobile.hnu.xiangyu.user.pojo;

import java.util.Date;

public class UserFriend extends UserFriendKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_friend.alias_name
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    private String aliasName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_friend.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    private Date gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_friend.gmt_modified
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    private Date gmtModified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_friend.alias_name
     *
     * @return the value of user_friend.alias_name
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public String getAliasName() {
        return aliasName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_friend.alias_name
     *
     * @param aliasName the value for user_friend.alias_name
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setAliasName(String aliasName) {
        this.aliasName = aliasName == null ? null : aliasName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_friend.gmt_create
     *
     * @return the value of user_friend.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_friend.gmt_create
     *
     * @param gmtCreate the value for user_friend.gmt_create
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_friend.gmt_modified
     *
     * @return the value of user_friend.gmt_modified
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_friend.gmt_modified
     *
     * @param gmtModified the value for user_friend.gmt_modified
     *
     * @mbg.generated Thu May 17 23:34:42 CST 2018
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}