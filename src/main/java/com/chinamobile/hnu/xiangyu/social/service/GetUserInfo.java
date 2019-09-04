package com.chinamobile.hnu.xiangyu.social.service;

import com.chinamobile.hnu.xiangyu.social.pojo.Position;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import org.apache.ibatis.annotations.Param;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wangbo
 * \* Date: 2019/7/10
 * \* Time: 10:23
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public interface GetUserInfo {

    /**
     *
     * @param id1 我的id
     * @param id2 对方id
     * @return
     */
    public String getDistance(int id1,int id2);

    public Position getUser(int id);

    public void updatePosition(int id,double longitude,double latitude);
}