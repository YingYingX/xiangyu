/**
 * 
 */
package com.chinamobile.hnu.xiangyu.web.club;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinamobile.hnu.xiangyu.club.pojo.ClubInfo;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubMember;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubMember.Nick;
import com.chinamobile.hnu.xiangyu.club.service.ClubBaseService;
import com.chinamobile.hnu.xiangyu.club.service.ClubMemberService;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.user.service.UserService;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * @author The Old Man and the Sea
 *
 * 2018年6月5日
 */

@RestController
@RequestMapping("/api/club/member/")
public class ClubMemberController {

	private static Logger log = LoggerFactory.getLogger(ClubMemberController.class);
	
	@Autowired
	private AuthTokenService tokenService;
	
	@Autowired
	private ClubMemberService memberService;
	
	@Autowired
	private ClubBaseService baseService;
	
	@Autowired
	private UserService userService;
	
	/***
	 * 团队成员
	 * @param clubId
	 * @return
	 */
	@PostMapping("pglist.do")
	public ResponseDto clubMember(@RequestParam(required=true)Integer clubId) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			List<ClubMember> list = memberService.selectClubMember(clubId);
			userService.setFriendNickName(list, user.getId());
			return ResultUtil.result(0, "查询成功",list);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("slect clubMember page Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/***
	 * 修改群成员昵称
	 * @param clubMember
	 * @param result
	 * @return
	 */
	@PostMapping("update.do")
	public ResponseDto update(@Validated(Nick.class) ClubMember clubMember,BindingResult result) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		if(result.hasErrors()){
			return ResultUtil.result(2, result.getAllErrors().get(0).getDefaultMessage());
		}
		try {
			ClubMember member = memberService.selectClubMemberById(clubMember.getId());
			if(member != null){
				boolean flag = memberService.userIsClubAdmin(member.getClubId(), user.getId());
				if(!flag && member.getMemberId().intValue() != user.getId().intValue()){
					return ResultUtil.result(3, "没有权限");
				}
			}else{
				return ResultUtil.result(2, "没有该成员");
			}
			
			memberService.updateClubMember(clubMember);
			return ResultUtil.result(0, "修改成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("update clubMember nickName Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/***
	 * 设置取消管理员
	 * @param id
	 * @param op
	 * @return
	 */
	@PostMapping("setAdmin.do")
	public ResponseDto update(@RequestParam(required=true) Long id,@RequestParam(required=true) Integer op) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			ClubMember member = memberService.selectClubMemberById(id);
			if(member != null){
				ClubInfo clubInfo = baseService.selectClubInfoById(member.getClubId());
				if(clubInfo.getCreator().intValue() != user.getId()){
					return ResultUtil.result(3, "没有权限");
				}else if(member.getMemberId().intValue() == user.getId().intValue()){
					return ResultUtil.result(2, "你已经是管理员了");
				}
				if(op == 1){
					if(member.getRole().intValue() > 0 && member.getRole().intValue() < 3){
						return ResultUtil.result(2, "该用户已经是管理员");
					}
				}else{
					if(member.getRole().intValue() == 0 || member.getRole().intValue() >= 3){
						return ResultUtil.result(2, "该用户不是管理员");
					}
				}
				
			}else{
				return ResultUtil.result(2, "没有该成员");
			}
			ClubMember clubMember = new ClubMember();
			clubMember.setId(id);
			clubMember.setRole(op == 1?(byte)2:(byte)3);
			memberService.updateClubMember(clubMember);
			return ResultUtil.result(0, "修改成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("update clubMember role Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	
}
