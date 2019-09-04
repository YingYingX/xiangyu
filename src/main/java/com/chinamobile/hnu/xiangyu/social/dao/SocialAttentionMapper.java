package com.chinamobile.hnu.xiangyu.social.dao;

import com.chinamobile.hnu.xiangyu.social.pojo.SocialFriendKey;
import com.chinamobile.hnu.xiangyu.social.vo.SocialAttentionVo;
import com.chinamobile.hnu.xiangyu.social.vo.SocialVo;
import org.apache.ibatis.annotations.Param;

public interface SocialAttentionMapper {
    /**
     *查询用户关注的好友列表
     * @param id 当前用户ID
     * @return
     */
    SocialAttentionVo selectById(Integer id);

    /**
     * 关注用户
     * @param follower 我的ID
     * @param concerned 被关注方ID
     */
    Boolean attention(@Param("follower") Integer follower, @Param("concerned") Integer concerned);

    /**
     * 取消关注
     * @param unfollower    我的ID
     * @param unconcerned   取消关注的好友ID
     */
    Boolean unAttention(@Param("unfollower") Integer unfollower, @Param("unconcerned") Integer unconcerned);

    /**
     * 查询是否关注状态
     * @param u_id 当前登录用户ID
     * @param f_id 查看的好友ID
     */
    SocialFriendKey stateOnFollow(@Param("u_id") Integer u_id, @Param("f_id") Integer f_id);

    /**
     * 交友补充资料
     *
     */
    SocialAttentionVo appendUserInfo(SocialAttentionVo socialAttentionVo);

}
