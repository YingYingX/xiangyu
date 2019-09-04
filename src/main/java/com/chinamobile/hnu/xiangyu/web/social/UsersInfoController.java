package com.chinamobile.hnu.xiangyu.web.social;

import com.chinamobile.hnu.xiangyu.social.pojo.Position;
import com.chinamobile.hnu.xiangyu.social.service.GetUserInfo;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;
import com.chinamobile.hnu.xiangyu.web.social.UserInfoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wangbo
 * \* Date: 2019/7/10
 * \* Time: 10:34
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@RestController
@RequestMapping("/api/social/")
public class UsersInfoController {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);
    @Autowired
    private AuthTokenService tokenService;

    @Autowired
    GetUserInfo getUserInfo;

    @RequestMapping("getDistance")
    public ResponseDto getDistance(int id){
        UserAccount userAccount = tokenService.getLoginUser();

        int id1 = userAccount.getId();

        String result = null;

        try {
            result = getUserInfo.getDistance(id, id1);
        }catch (Exception e){
            logger.info(e.toString());
            return ResultUtil.result(1, "获得距离失败"+e);
        }

        return ResultUtil.result(0, "获得距离成功", result);
    }
    @RequestMapping("updatePosition.do")
    public ResponseDto updatePosition(double longitude,double latitude){

        UserAccount userAccount = tokenService.getLoginUser();

        if (userAccount == null){
            return ResultUtil.result(3, "未登录！");
        }


        try {
            int id = userAccount.getId();

            System.out.println("id="+id);

            Position position = getUserInfo.getUser(id);

            System.out.println("position"+position == null);

            //信息表中没有该数据
            if (position == null){
                return ResultUtil.result(3, "请设置交友信息");
            }

            getUserInfo.updatePosition(id, longitude, latitude);
        }catch (Exception e){
            return ResultUtil.result(5, "服务器异常");
        }

        return ResultUtil.result(0, "更新位置成功");
    }
}