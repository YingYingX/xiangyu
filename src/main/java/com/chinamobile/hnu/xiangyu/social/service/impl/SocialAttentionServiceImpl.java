package com.chinamobile.hnu.xiangyu.social.service.impl;

import com.chinamobile.hnu.xiangyu.social.dao.SocialAttentionMapper;
import com.chinamobile.hnu.xiangyu.social.pojo.SocialAttention;
import com.chinamobile.hnu.xiangyu.social.pojo.SocialFriendKey;
import com.chinamobile.hnu.xiangyu.social.service.SocialAttentionService;
import com.chinamobile.hnu.xiangyu.social.vo.SocialAttentionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialAttentionServiceImpl implements SocialAttentionService {

    @Autowired
    SocialAttentionMapper socialAttentionMapper;

    public SocialAttentionVo findById(Integer id) {
        System.out.println("id="+id+"/n");
        return socialAttentionMapper.selectById(id);

    }

    public Boolean attention(Integer follower,Integer concerned){

        return socialAttentionMapper.attention(follower,concerned);
    }

    public Boolean unAttention(Integer unfollower, Integer unconcerned){

        return socialAttentionMapper.unAttention(unfollower,unconcerned);
    }

    public SocialFriendKey stateOnFollow(Integer u_id, Integer f_id){

        return socialAttentionMapper.stateOnFollow(u_id,f_id);
    }

    public SocialAttentionVo appendUserInfo(SocialAttentionVo socialAttentionVo){

        return socialAttentionMapper.appendUserInfo(socialAttentionVo);
    }
}
