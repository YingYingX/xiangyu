package com.chinamobile.hnu.xiangyu.web.social;

import com.chinamobile.hnu.xiangyu.social.service.UserInfoService;
import com.chinamobile.hnu.xiangyu.social.vo.UserInfoVO;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wangbo
 * \* Date: 2019/7/8
 * \* Time: 16:45
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@RestController
@RequestMapping("/api/social/")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @RequestMapping(value = "getUserInfo.do",method =  RequestMethod.POST)
    public ResponseDto getUserInfo(int id){
        UserInfoVO userInfoVO = userInfoService.getUserInfo(id);
        System.out.println("Controller"+userInfoVO);
        return ResultUtil.result(1, userInfoVO.toString());
    }
}