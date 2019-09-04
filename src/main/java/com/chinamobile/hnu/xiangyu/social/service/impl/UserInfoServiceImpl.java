package com.chinamobile.hnu.xiangyu.social.service.impl;

import com.chinamobile.hnu.xiangyu.social.dao.UserInfoMapper;
import com.chinamobile.hnu.xiangyu.social.service.UserInfoService;
import com.chinamobile.hnu.xiangyu.social.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wangbo
 * \* Date: 2019/7/8
 * \* Time: 16:42
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public UserInfoVO getUserInfo(int id) {
        UserInfoVO userInfoVO = null;
        try {
            userInfoVO = userInfoMapper.selectInfo(id);
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("userInfo:" + userInfoVO);
        return userInfoVO;
    }


}