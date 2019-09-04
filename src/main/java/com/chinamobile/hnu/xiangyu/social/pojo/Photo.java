package com.chinamobile.hnu.xiangyu.social.pojo;

/**
 * Created By IntelliJ IDEA
 *
 * @author pengxj
 * @date 2019/9/3
 */
public class Photo {
    private Integer id;
    private Integer user_id;
    private String image_route;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getImage_route() {
        return image_route;
    }

    public void setImage_route(String image_route) {
        this.image_route = image_route;
    }
}
