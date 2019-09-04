package com.chinamobile.hnu.xiangyu.web.social;


import com.chinamobile.hnu.xiangyu.social.pojo.SocialFriendKey;
import com.chinamobile.hnu.xiangyu.social.service.SocialAttentionService;
import com.chinamobile.hnu.xiangyu.social.vo.SocialAttentionVo;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.util.calendar.LocalGregorianCalendar;

import java.sql.SQLOutput;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/social/")
public class SocialAttentionController {

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private SocialAttentionService socialAttentionService;

    /**
     * 关注用户
     * @param concerned
     * @return
     */

    @PostMapping("follow.do")
    public ResponseDto follow( Integer concerned)
    {
        UserAccount user = authTokenService.getLoginUser();
        int id = 0;
        try{
            id = user.getId();
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.result(1,"fail",e);
        }
        try {
            socialAttentionService.attention(id,concerned);
        }catch (Exception e){
            System.out.println(e.toString());
            return ResultUtil.result(1,"fail",e);
        }

        return ResultUtil.result(0,"关注成功");
    }

    /**
     * 取消关注用户
     * @param unconcerned
     * @return
     */

    @PostMapping("unfollow.do")
    public ResponseDto unfollow(Integer unconcerned)
    {
        UserAccount userAccount = authTokenService.getLoginUser();
        int id = 0;
        try {
            id = userAccount.getId();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            socialAttentionService.unAttention(id,unconcerned);
        }catch (Exception e) {
            System.out.println(e.toString());
            return ResultUtil.result(1,"fail",e);
        }
        return ResultUtil.result(0,"取消关注成功");
    }

    /**
     * 返回数据0表示该用户非我关注的好友
     * 返回数据1表示该用户是我的关注好友
     * @param f_id
     * @return
     */
    @PostMapping("stateAttention.do")
    public ResponseDto stateAttention(Integer f_id)
    {
        UserAccount userAccount = authTokenService.getLoginUser();
        int id = 0;
        try {
            id = userAccount.getId();
        }catch (Exception e){
            e.printStackTrace();
        }
        SocialFriendKey  socialFriendKey =null;
        try {
            socialFriendKey = socialAttentionService.stateOnFollow(id,f_id);
        }catch (Exception e){
            System.out.println(e.toString());
            return ResultUtil.result(1,"fail",e);
        }
        return ResultUtil.result(0,"已关注该用户",1);
    }

    /**
     *
     * 启用交友功能的时候补充个人资料
     * 异常状态0表示补充信息错误
     */
    @PostMapping("addUserInfo.do")
    public ResponseDto addUserInfo(@RequestBody Map<String,Object> maps){
        UserAccount userAccount = authTokenService.getLoginUser();
        int id = 0;
        try {
            id = userAccount.getId();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            SocialAttentionVo socialAttentionVo = new SocialAttentionVo();
            socialAttentionVo.setId(id);
            socialAttentionVo.setBirthday((Date)maps.get("birthday"));
            socialAttentionVo.setPhone((String)maps.get("phone"));
            socialAttentionVo.setDescroption((String)maps.get("descroption"));
            socialAttentionVo.setLatitude((Double)maps.get("latitude"));
            socialAttentionVo.setLongitude((Double)maps.get("longitude"));
            return ResultUtil.result(0,"信息完善成功",0);
        }catch (Exception e){
            return ResultUtil.result(1,"补充信息异常",0);
        }
    }
}
