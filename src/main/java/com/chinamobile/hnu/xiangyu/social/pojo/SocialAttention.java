package com.chinamobile.hnu.xiangyu.social.pojo;

import com.alibaba.druid.sql.visitor.functions.Char;

import java.util.Date;

public class SocialAttention {
    private Integer id;

    private Char phone;

    private Date birthday;

    private String descroption;

    private Double longitude;

    private Double latitude;

    public void setPhone(Char phone) { this.phone = phone; }

    public Char getPhone() { return phone; }

    public Double getLongitude() { return longitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getLatitude() { return latitude; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getDescroption() { return descroption; }

    public void setDescroption(String descroption) { this.descroption = descroption; }
}
