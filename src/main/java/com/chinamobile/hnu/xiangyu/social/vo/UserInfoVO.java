package com.chinamobile.hnu.xiangyu.social.vo;

import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wangbo
 * \* Date: 2019/7/8
 * \* Time: 16:38
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class UserInfoVO {

    int user_id;

    String telphone;

    Date birthday;

    String descroption;

    double longitude;

    double latitude;

    public UserInfoVO() {
    }

    public UserInfoVO(int user_id, String telphone, Date birthday, String descroption, double longitude, double latitude) {
        this.user_id = user_id;
        this.telphone = telphone;
        this.birthday = birthday;
        this.descroption = descroption;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDescroption() {
        return descroption;
    }

    public void setDescroption(String descroption) {
        this.descroption = descroption;
    }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    @Override
    public String toString() {
        return "UserInfoVO{" +
                "user_id=" + user_id +
                ", telphone='" + telphone + '\'' +
                ", birthday=" + birthday +
                ", descroption='" + descroption + '\'' +
                ", longitude=" + longitude + '\''+
                ", latitude=" + latitude +
                '}';
    }
}