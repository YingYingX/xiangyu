package com.chinamobile.hnu.xiangyu.social.service.impl;

import com.chinamobile.hnu.xiangyu.social.dao.UserInfosMapper;
import com.chinamobile.hnu.xiangyu.social.pojo.Position;
import com.chinamobile.hnu.xiangyu.social.service.GetUserInfo;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wangbo
 * \* Date: 2019/7/10
 * \* Time: 10:24
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class GetUserInfoImpl implements GetUserInfo {

    private static double EARTH_RADIUS = 6378.137;

    @Autowired
    UserInfosMapper userInfosMapper;

    /**
     *
     * @param id1 我的id
     * @param id2 对方id
     * @return
     */
    @Override
    public String getDistance(int id1,int id2) {

        Position position1 = userInfosMapper.getPosition(id1);

        Position position2 = userInfosMapper.getPosition(id2);

        double radLat1 = rad(position1.getLongitude());
        double radLat2 = rad(position2.getLongitude());
        double difference = radLat1 - radLat2;
        double mdifference = rad(position1.getLatitude()) - rad(position1.getLatitude());
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(mdifference / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000) / 10000;
        String distanceStr = distance+"";
        distanceStr = distanceStr.
                substring(0, distanceStr.indexOf("."));

        return distanceStr;

    }

    @Override
    public Position getUser(int id) {
        Position position = userInfosMapper.getUser(id);
        return position;
    }

    @Override
    public void updatePosition(int id, double longitude, double latitude) {
        userInfosMapper.updatePosition(id, longitude, latitude);
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

}