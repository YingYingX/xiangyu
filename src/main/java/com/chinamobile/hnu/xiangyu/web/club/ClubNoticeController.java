/**
 * 
 */
package com.chinamobile.hnu.xiangyu.web.club;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinamobile.hnu.xiangyu.club.pojo.ClubMember;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubNotice;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubNotice.Add;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubNotice.Update;
import com.chinamobile.hnu.xiangyu.club.service.ClubBaseService;
import com.chinamobile.hnu.xiangyu.club.service.ClubMemberService;
import com.chinamobile.hnu.xiangyu.club.service.ClubNoticeService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubNoticeVo;
import com.chinamobile.hnu.xiangyu.club.vo.Visibility;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.user.service.UserService;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * @author The Old Man and the Sea
 *
 * 2018年6月5日
 */
@RestController
@RequestMapping("/api/club/notice/")
public class ClubNoticeController {

	private final static Logger log = LoggerFactory.getLogger(ClubNoticeController.class);
	
	@Autowired
	private AuthTokenService tokenService;
	
	@Autowired
	private ClubNoticeService noticeService;
	
	@Autowired
	private ClubMemberService memberService;
	
	@Autowired
	private ClubBaseService clubService;
	
	@Autowired
	private UserService userService;
	
	
	/****
	 * 分页查询公告
	 * @param pageCurrent
	 * @param pageSize
	 * @param id
	 * @return
	 */
	@PostMapping("pglist.do")
	public ResponseDto list(@RequestParam(name = "pn", required = true, defaultValue = "0") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "10") Integer pageSize,
			@RequestParam(required = true) Integer id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", user.getId());
		if(id != null){
			map.put("clubId", id);
		}else{
			return ResultUtil.result(2, "团队id不能为空");
		}
		Page<ClubNoticeVo> page = null;
		try {
			Visibility vi = clubService.selectVisibilityId(id);
			if(vi.getSettingNotice().intValue() == 1){
				ClubMember clubMember = memberService.selectUserJoin(id, user.getId());
				if(clubMember == null){
					return ResultUtil.result(0, "查询成功",new PageVo(new ArrayList<>(),0L,0));
				}
			}
			page = noticeService.selectByPage(pageCurrent, pageSize, map);
			userService.setFriendNickName(page.getData(), user.getId());
			return ResultUtil.result(0, "查询成功", new PageVo(page.getData(), page.getTotalCount(), page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select ClubNotice page Exception:", e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/****
	 * 发布公告
	 * @param clubNotice
	 * @param result
	 * @return
	 */
	@PostMapping("save.do")
	public ResponseDto save(@Validated(Add.class) ClubNotice clubNotice,BindingResult result) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		if(result.hasErrors()){
			return ResultUtil.result(2, result.getAllErrors().get(0).getDefaultMessage());
		}
		
		try {
			ClubMember member = memberService.selectUserJoin(clubNotice.getClubId(),user.getId());
			if(member == null){
				return ResultUtil.result(2, "你还没有加入该团队");
			}
			clubNotice.setPresentor(user.getId());
			noticeService.insertClubNotice(clubNotice);
			Map<String,Object> map = new HashMap<>();
			map.put("id", clubNotice.getId());
			
			return ResultUtil.result(0, "发布成功",map);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("save ClubNotice Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	/**
	 * 编辑公告
	 * @param clubNotice
	 * @param result
	 * @return
	 */
	@PostMapping("update.do")
	public ResponseDto update(@Validated(Update.class) ClubNotice clubNotice,BindingResult result) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		if(result.hasErrors()){
			return ResultUtil.result(2, result.getAllErrors().get(0).getDefaultMessage());
		}
		
		try {
			ClubNotice old = noticeService.selectClubNoticeById(clubNotice.getId());
			if(old ==null || old.getPresentor().intValue() != user.getId().intValue()){
				return ResultUtil.result(3, "没有权限"); 
			}
			noticeService.updateClubNotice(clubNotice);
			return ResultUtil.result(0, "修改成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("update ClubNotice Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/****
	 * 删除公共
	 * @param id
	 * @return
	 */
	@PostMapping("delete.do")
	public ResponseDto delete(@RequestParam(required=true) Long id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			ClubNotice old = noticeService.selectClubNoticeById(id);
			if(old != null){
				boolean flag = memberService.userIsClubAdmin(old.getClubId(), user.getId());
				if(!flag && old.getPresentor().intValue() != user.getId()){
					return ResultUtil.result(3, "没有权限");
				}
			}else{
				return ResultUtil.result(2, "没有该公告");
			}
			noticeService.deleteClubNoticeById(id);
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("delete ClubNotice Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
}
