package com.chinamobile.hnu.xiangyu.social.service;

import com.chinamobile.hnu.xiangyu.social.pojo.SocialFriendKey;
import com.chinamobile.hnu.xiangyu.social.vo.SocialAttentionVo;
import org.springframework.beans.factory.annotation.Autowired;

public interface SocialAttentionService {

    SocialAttentionVo findById(Integer id);

    Boolean attention(Integer follower,Integer concerned);

    Boolean unAttention(Integer unfollower, Integer unconcerned);

    SocialFriendKey stateOnFollow(Integer u_id, Integer f_id);

    SocialAttentionVo appendUserInfo(SocialAttentionVo socialAttentionVo);

}
