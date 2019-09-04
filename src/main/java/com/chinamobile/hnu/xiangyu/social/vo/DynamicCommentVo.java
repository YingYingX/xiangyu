package com.chinamobile.hnu.xiangyu.social.vo;

import java.util.Date;

/**
 * Created By IntelliJ IDEA
 *
 * @author pengxj
 * @date 2019/9/2
 */
public class DynamicCommentVo {
    private Integer id;
    private Integer dynamic_id;
    private Date gmt_create;
    private Integer presentor;
    private String content;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Integer getDynamic_id() { return dynamic_id; }

    public void setDynamic_id(Integer dynamic_id) { this.dynamic_id = dynamic_id; }

    public Date getGmt_create() { return gmt_create; }

    public void setGmt_create(Date gmt_create) { this.gmt_create = gmt_create; }

    public Integer getPresentor() { return presentor; }

    public void setPresentor(Integer presentor) { this.presentor = presentor; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }
}
