package com.chinamobile.hnu.xiangyu.social.dao;

import com.chinamobile.hnu.xiangyu.social.pojo.Position;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import org.apache.ibatis.annotations.Param;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wangbo
 * \* Date: 2019/7/10
 * \* Time: 10:02
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public interface UserInfosMapper {

    public Position getUser(int id);

    public Position getPosition(int id);

//    public void setPosition(@Param("id") int id,@Param("longitude") int longitude,@Param("latitude") int latitude);

    public int updatePosition(@Param("id") int id,@Param("longitude") double longitude,@Param("latitude") double latitude);
}