/**
 * 
 */
package com.chinamobile.hnu.xiangyu.web.club;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinamobile.hnu.xiangyu.club.pojo.ClubActivity;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubActivity.Add;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubActivity.Update;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubActivityApplicant;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubActivityComment;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubInfo;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubMember;
import com.chinamobile.hnu.xiangyu.club.pojo.Photo;
import com.chinamobile.hnu.xiangyu.club.service.ClubActivityService;
import com.chinamobile.hnu.xiangyu.club.service.ClubBaseService;
import com.chinamobile.hnu.xiangyu.club.service.ClubMemberService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubActivityVo;
import com.chinamobile.hnu.xiangyu.club.vo.ClubVo;
import com.chinamobile.hnu.xiangyu.club.vo.CommentVo;
import com.chinamobile.hnu.xiangyu.club.vo.LikeVo;
import com.chinamobile.hnu.xiangyu.club.vo.Visibility;
import com.chinamobile.hnu.xiangyu.common.enums.ExtidType;
import com.chinamobile.hnu.xiangyu.common.enums.PointRuleType;
import com.chinamobile.hnu.xiangyu.common.service.PubPointService;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.user.service.UserService;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * @author The Old Man and the Sea
 *
 * 2018年6月1日
 */
@RestController
@RequestMapping("/api/club/activity/")
public class ClubActivityController {

	private final static Logger log = LoggerFactory.getLogger(ClubSpeakController.class);
	
	@Value("${constant.activity}")
	private String ACTIVITY;
	
	@Autowired
	private UploadDocumentUtil fileUtil;
	
	@Autowired
	private AuthTokenService tokenService;
	
	@Autowired
	private ClubActivityService activityService;
	
	@Autowired
	private ClubMemberService memberService;
	
	@Autowired
	private ClubBaseService clubService;
	
	@Autowired
	private PubPointService pointService;
	
	@Autowired
	private UserService userService;
	
	/***
	 * 活动分页
	 * @param pageCurrent
	 * @param pageSize
	 * @param id 团队id
	 * @return
	 */
	@PostMapping("pglist.do")
	public ResponseDto list(
			@RequestParam(name = "pn", required = true,defaultValue="0") Integer pageCurrent,
			@RequestParam(name = "ps", required = true,defaultValue="10") Integer pageSize,
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
		Page<ClubActivityVo> page = null;
		try {
			
			Visibility vi = clubService.selectVisibilityId(id);
			if(vi.getSettingActivity().intValue() == 1){
				ClubMember clubMember = memberService.selectUserJoin(id, user.getId());
				if(clubMember == null){
					return ResultUtil.result(0, "查询成功",new PageVo(new ArrayList<>(),0L,0));
				}
			}
			page = activityService.selectByPage(pageCurrent,pageSize,map);
			return ResultUtil.result(0, "查询成功",new PageVo(page.getData(),page.getTotalCount(),page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select ClubActivity page Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	/****
	 * 热门活动分页
	 * @param pageCurrent
	 * @param pageSize
	 * @return
	 */
	@PostMapping("hotlist.do")
	public ResponseDto hotlist(
			@RequestParam(name = "pn", required = true,defaultValue="0") Integer pageCurrent,
			@RequestParam(name = "ps", required = true,defaultValue="10") Integer pageSize) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hot", 1);
		map.put("uid", user.getId());
		Page<ClubActivityVo> page = null;
		try {
			page = activityService.selectHotByPage(pageCurrent,pageSize,map);
			return ResultUtil.result(0, "查询成功",new PageVo(page.getData(),page.getTotalCount(),page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select hot ClubActivity page Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	
	/****
	 * 发布活动
	 * @param request
	 * @param clubActivity
	 * @param result
	 * @return
	 */
	@PostMapping("save.do")
	public ResponseDto save(HttpServletRequest request,@Validated(Add.class) ClubActivity clubActivity,BindingResult result) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		if(result.hasErrors()){
			return ResultUtil.result(2, result.getAllErrors().get(0).getDefaultMessage());
		}
		
		try {
			ClubMember member = memberService.selectUserJoin(clubActivity.getClubId(),user.getId());
			if(member == null){
				return ResultUtil.result(2, "你还没有加入该团队");
			}
			clubActivity.setPresentor(user.getId());
			List<Photo> photos = null;
			ResponseDto dto = fileUtil.savebatchFile(request, user.getId().toString(), ACTIVITY,
					new String[] { "jpg", "png" });
			if(dto.getCode() == 2){
				return dto;
			}else if(dto.getCode() == 0){
				photos = new ArrayList<>();
				for (String fileId : (List<String>)dto.getData()) {
					Photo cp = new Photo();
					cp.setPhotoId(fileId);
					photos.add(cp);
				}
				clubActivity.setPhotos(photos);
			}
			activityService.insertClubActivity(clubActivity);
			Map<String,Object> map = new HashMap<>();
			map.put("id", clubActivity.getId());
			if(clubActivity.getPhotos() != null && clubActivity.getPhotos().size()>0){
				map.put("photos", clubActivity.getPhotos());
			}
			return ResultUtil.result(0, "发表成功",map);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("save ClubActivity Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/***
	 * 修改活动内容
	 * @param request
	 * @param clubActivity
	 * @param result
	 * @param delFileIds
	 * @return
	 */
	@PostMapping("update.do")
	public ResponseDto update(HttpServletRequest request,@Validated(Update.class) ClubActivity clubActivity,BindingResult result,
			@RequestParam(required=false)String delFileIds) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		if(result.hasErrors()){
			return ResultUtil.result(2, result.getAllErrors().get(0).getDefaultMessage());
		}
		
		try {
			ClubActivity ca = activityService.selectClubActivityById(clubActivity.getId());
			if(ca == null){
				return ResultUtil.result(2, "无效数据");
			}else if(ca.getPresentor().intValue() != user.getId()){
				return ResultUtil.result(3, "没有权限");
			}
			clubActivity.setPresentor(user.getId());
			List<Photo> photos = null;
			ResponseDto dto = fileUtil.savebatchFile(request, user.getId().toString(), ACTIVITY,
					new String[] { "jpg", "png" });
			if(dto.getCode() == 2){
				return dto;
			}else if(dto.getCode() == 0){
				photos = new ArrayList<>();
				for (String fileId : (List<String>)dto.getData()) {
					Photo cp = new Photo();
					cp.setPhotoId(fileId);
					photos.add(cp);
				}
				clubActivity.setPhotos(photos);
			}
			activityService.updateClubActivity(clubActivity,delFileIds);
			Map<String,Object> map = new HashMap<>();
			if(clubActivity.getPhotos() != null && clubActivity.getPhotos().size()>0){
				map.put("photos", clubActivity.getPhotos());
			}
			return ResultUtil.result(0, "修改成功",map);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("update ClubActivity Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/***
	 * 活动详情
	 * @param id
	 * @return
	 */
	@PostMapping("detail.do")
	public ResponseDto detail(@RequestParam(required = true) Long id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		ClubActivityVo vo = null; 
		try {
			vo = activityService.selectClubActivityDetailById(id,user.getId());
			if(vo != null){
				ClubVo club = new ClubVo();
				ClubMember member = memberService.selectUserJoin(vo.getClubId(),user.getId());
				club.setIsJoin(member==null?(byte)0:1);
				//团队名称;2018/11/1修改内容
				ClubInfo clubInfo = clubService.selectClubInfoById(vo.getClubId());
				club.setName(clubInfo.getName());
				vo.setClubInfo(club);
			}
			return ResultUtil.result(0, "查询成功", vo);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select ClubActivity detail Exception:", e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/****
	 * 查询参与活动人员
	 * @param id
	 * @return
	 */
	@PostMapping("member.do")
	public ResponseDto member(@RequestParam(required = true) Long id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		List<LikeVo> vo = null;
		try {
			vo = activityService.selectClubActivityMember(id);
			userService.setFriendNickName(vo, user.getId());
			return ResultUtil.result(0, "查询成功", vo);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select ClubActivity member Exception:", e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/****
	 * 删除活动
	 * @param id
	 * @return
	 */
	@PostMapping("delete.do")
	public ResponseDto delete(@RequestParam(required=true)Long id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		try {
			ClubActivity ca = activityService.selectClubActivityById(id);
			
			
			if(ca == null){
				return ResultUtil.result(2, "无效数据");
			}else{
				boolean flag = memberService.userIsClubAdmin(ca.getClubId(), user.getId());
				if (!flag && ca.getPresentor().intValue() != user.getId()) {
					return ResultUtil.result(3, "没有权限");
				}
			}
			activityService.deleteClubActivityById(id);
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("delete ClubActivity Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/***
	 * 活动评论分页
	 * @param pageCurrent
	 * @param pageSize
	 * @param id
	 * @return
	 */
	@PostMapping("pgcomment.do")
	public ResponseDto listComment(
			@RequestParam(name = "pn", required = true,defaultValue="0") Integer pageCurrent,
			@RequestParam(name = "ps", required = true,defaultValue="10") Integer pageSize,
			@RequestParam(required = true) Long id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityId", id);
		Page<CommentVo> page = null;
		try {
			page = activityService.selectCommentByPage(pageCurrent,pageSize,map);
			userService.setFriendNickName(page.getData(), user.getId());
			return ResultUtil.result(0, "查询成功",new PageVo(page.getData(),page.getTotalCount(),page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select ClubActivityComment page Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/***
	 * 发表评论
	 * @param request
	 * @param clubActivityComment
	 * @param result
	 * @return
	 */
	@PostMapping("comment.do")
	public ResponseDto comment(HttpServletRequest request,@Validated ClubActivityComment clubActivityComment,BindingResult result) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		if(result.hasErrors()){
			return ResultUtil.result(2, result.getAllErrors().get(0).getDefaultMessage());
		}
		
		try {
			ClubActivity activity = activityService.selectClubActivityById(clubActivityComment.getActivityId());
			
//			ClubMember member = memberService.selectUserJoin(activity.getClubId(),user.getId());
//			if(member == null){
//				return ResultUtil.result(2, "你还没有加入该团队");
//			}
			clubActivityComment.setPresentor(user.getId());
			activityService.insertClubActivityComment(clubActivityComment);
			
			//增加经验值
			pointService.addPoint(clubActivityComment.getPresentor(), PointRuleType.TYPE6.getValue(), clubActivityComment.getId().toString(),
					ExtidType.TYPE1.getValue());
			//被评论者增加经验值
			pointService.addPoint(activity.getPresentor(), PointRuleType.TYPE7.getValue(), activity.getId().toString(),
					ExtidType.TYPE1.getValue());
			Map<String,Object> map = new HashMap<>();
			map.put("id", clubActivityComment.getId());
			return ResultUtil.result(0, "评论成功",map);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("save ClubActivityComment Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	/***
	 * 删除评论
	 * @param id
	 * @return
	 */
	@PostMapping("delcomment.do")
	public ResponseDto delcomment(@RequestParam(required=true)Long id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			ClubActivityComment cac = activityService.selectClubActivityCommentById(id);
			if(cac == null){
				log.info("ClubActivityComment is null");
				return ResultUtil.result(3, "没有权限");
			}else{
				ClubActivity ca = activityService.selectClubActivityById(cac.getActivityId());
				if(ca == null){
					return ResultUtil.result(3, "无效数据");
				}else{
					boolean flag = memberService.userIsClubAdmin(ca.getClubId(), user.getId());
					if (!flag && cac.getPresentor().intValue() != user.getId()) {
						return ResultUtil.result(3, "没有权限");
					}
				}
			}
			activityService.deleteClubActivityCommentById(id,cac.getActivityId());
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("delete ClubActivityComment Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/***
	 * 报名参加活动
	 * @param id 活动id
	 * @param op
	 * @return
	 */
	@PostMapping("apply.do")
	public ResponseDto like(@RequestParam(required=true)Long id,@RequestParam(required=true)Integer op) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		try {
			
			ClubActivity activity = activityService.selectClubActivityById(id);
			if(activity == null){
				return ResultUtil.result(2, "没有该活动");
			}else if(new Date().getTime() > activity.getApplyDeadline().getTime()){
				return ResultUtil.result(2, "活动已过期");
			}
//			ClubMember member = memberService.selectUserJoin(activity.getClubId(),user.getId());
//			if(member == null){
//				return ResultUtil.result(2, "你还没有加入该团队");
//			}
			
			ClubActivityApplicant applicant = activityService.selectClubActivityApplicantById(id,user.getId());
			if(op == 1){
				if(applicant != null){
					return ResultUtil.result(2, "你已经参加过了");
				}
			}else{
				if(applicant == null){
					return ResultUtil.result(2, "你还没有参加该活动");
				}
			}
			applicant = new ClubActivityApplicant();
			applicant.setMemberId(user.getId());
			applicant.setActivityId(id);
			activityService.updateClubActivityApplicant(applicant,op);
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("update ClubActivityApplicant Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	
}
