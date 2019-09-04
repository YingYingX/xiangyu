package com.chinamobile.hnu.xiangyu.social.dao;

import com.chinamobile.hnu.xiangyu.social.vo.UserInfoVO;
import org.apache.ibatis.annotations.Select;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wangbo
 * \* Date: 2019/7/8
 * \* Time: 16:26
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public interface UserInfoMapper {

//    @Select("select * from social_user_info where user_id = #{id}")
    public UserInfoVO selectInfo(int id);

}