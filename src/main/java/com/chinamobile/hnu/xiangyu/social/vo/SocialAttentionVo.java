package com.chinamobile.hnu.xiangyu.social.vo;

import java.util.Date;

public class SocialAttentionVo {
    private Integer id;

    private String phone;

    private Date birthday;

    private String descroption;

    private Double longitude;

    private Double latitude;




    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() { return birthday; }

    public void setBirthday(Date birthday) { this.birthday = birthday; }

    public Double getLongitude() { return longitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getLatitude() { return latitude; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getDescroption() { return descroption; }

    public void setDescroption(String descroption) { this.descroption = descroption; }
}
