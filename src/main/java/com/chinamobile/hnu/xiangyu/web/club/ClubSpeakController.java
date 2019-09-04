/**
 * 
 */
package com.chinamobile.hnu.xiangyu.web.club;

import java.util.ArrayList;
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

import com.chinamobile.hnu.xiangyu.club.pojo.ClubInfo;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubMember;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubSpeak;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubSpeakComment;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubSpeakLike;
import com.chinamobile.hnu.xiangyu.club.pojo.Photo;
import com.chinamobile.hnu.xiangyu.club.service.ClubBaseService;
import com.chinamobile.hnu.xiangyu.club.service.ClubMemberService;
import com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubSpeakVo;
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
 * 2018年5月29日
 */
@RestController
@RequestMapping("/api/club/speak/")
public class ClubSpeakController {
	
	private final static Logger log = LoggerFactory.getLogger(ClubSpeakController.class);
	
	@Value("${constant.speak}")
	private String SPEAK;
	
	@Autowired
	private UploadDocumentUtil fileUtil;
	
	@Autowired
	private AuthTokenService tokenService;
	
	@Autowired
	private ClubSpeakService speakService;
	
	@Autowired
	private ClubMemberService memberService;
	
	@Autowired
	private ClubBaseService clubService;
	
	@Autowired
	private PubPointService pointService;
	
	@Autowired
	private UserService userService;
	
	/***
	 * 团言分页
	 * @param pageCurrent
	 * @param pageSize
	 * @param type
	 * @param id
	 * @return
	 */
	@PostMapping("pglist.do")
	public ResponseDto list(
			@RequestParam(name = "pn", required = true,defaultValue="0") Integer pageCurrent,
			@RequestParam(name = "ps", required = true,defaultValue="10") Integer pageSize,
			@RequestParam(required = true) Integer type,
			@RequestParam(required = true) Integer id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("uid", user.getId());
		if(id != null){
			map.put("clubId", id);
		}else{
			return ResultUtil.result(2, "团队id不能为空");
		}
		Page<ClubSpeakVo> page = null;
		try {
			Visibility vi = clubService.selectVisibilityId(id);
			if(vi.getSettingSpeak().intValue() == 1){
				ClubMember clubMember = memberService.selectUserJoin(id, user.getId());
				if(clubMember == null){
					return ResultUtil.result(0, "查询成功",new PageVo(new ArrayList<>(),0L,0));
				}
			}
			page = speakService.selectByPage(pageCurrent,pageSize,map);
			if(page.getData().size() > 0){
				userService.setFriendNickName(page.getData(), user.getId());
			}
			return ResultUtil.result(0, "查询成功",new PageVo(page.getData(),page.getTotalCount(),page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select clubSpeak page Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	
	/****
	 * 团言详情
	 * @param id
	 * @return
	 */
	@PostMapping("detail.do")
	public ResponseDto detail(@RequestParam Long id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		try {
			ClubSpeakVo vo = speakService.selectDetailById(id);
			if(null != vo && null != vo.getLikes()){
				// 我是否点过赞
				for (LikeVo like : vo.getLikes()) {
					if (like.getUid().intValue() == user.getId().intValue()) {
						vo.setIsLike((byte) 1);
					}
				}
			}
			userService.setFriendNickName(vo, user.getId());
			userService.setFriendNickName(vo.getLikes(), user.getId());
			
			//团队名称;2018/11/1修改内容
			ClubInfo clubInfo = clubService.selectClubInfoById(vo.getClubId());
			vo.setClubName(clubInfo.getName());
			return ResultUtil.result(0, "查询成功",vo);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select clubSpeak detail Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/****
	 * 发表团言
	 * @param clubSpeak
	 * @param result
	 * @return
	 */
	@PostMapping("save.do")
	public ResponseDto save(HttpServletRequest request,@Validated ClubSpeak clubSpeak,BindingResult result) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		if(result.hasErrors()){
			return ResultUtil.result(2, result.getAllErrors().get(0).getDefaultMessage());
		}
		
		try {
			ClubMember member = memberService.selectUserJoin(clubSpeak.getClubId(),user.getId());
			if(member == null){
				return ResultUtil.result(2, "你还没有加入该团队");
			}
			clubSpeak.setPresentor(user.getId());
			List<Photo> photos = null;
			ResponseDto dto = fileUtil.savebatchFile(request, user.getId().toString(), SPEAK,
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
				clubSpeak.setPhotos(photos);
			}
			speakService.insertClubSpeak(clubSpeak);
			Map<String,Object> map = new HashMap<>();
			map.put("id", clubSpeak.getId());
			if(photos != null){
				photos.forEach(photo -> {
					photo.setPhotoId(UploadDocumentUtil.buildPublicPath(photo.getPhotoId()));
				});
				map.put("photos", photos);
			}
			return ResultUtil.result(0, "发表成功",map);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("save clubSpeak Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/****
	 * 删除团言
	 * @param speakId
	 * @return
	 */
	@PostMapping("delete.do")
	public ResponseDto delete(@RequestParam(required=true)Long speakId) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		try {
			ClubSpeak cs = speakService.selectClubSpeakById(speakId);
			boolean flag = memberService.userIsClubAdmin(cs.getClubId(), user.getId());
			if (!flag && cs.getPresentor().intValue() != user.getId()) {
				return ResultUtil.result(3, "没有权限");
			}
			speakService.deleteClubSpeakById(speakId);
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("delete ClubSpeak Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	/***
	 * 发表评论
	 * @param request
	 * @param clubSpeak
	 * @param result
	 * @return
	 */
	@PostMapping("comment.do")
	public ResponseDto comment(HttpServletRequest request,@Validated ClubSpeakComment speakComment,BindingResult result) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		if(result.hasErrors()){
			return ResultUtil.result(2, result.getAllErrors().get(0).getDefaultMessage());
		}
		
		try {
			ClubSpeak speak = speakService.selectClubSpeakById(speakComment.getSpeakId());
			if(speak == null ){
				return ResultUtil.result(2, "无效数据");
			}
			ClubMember member = memberService.selectUserJoin(speak.getClubId(),user.getId());
			if(member == null){
				return ResultUtil.result(2, "你还没有加入该团队");
			}
			
			speakComment.setPresentor(user.getId());
			speakService.insertClubSpeakComment(speakComment);
			//增加经验值
			pointService.addPoint(speakComment.getPresentor(), PointRuleType.TYPE6.getValue(), speakComment.getId().toString(),
					ExtidType.TYPE1.getValue());
			//被评论者增加经验值
			pointService.addPoint(speak.getPresentor(), PointRuleType.TYPE7.getValue(), speak.getId().toString(),
					ExtidType.TYPE1.getValue());
			Map<String,Object> map = new HashMap<>();
			map.put("id", speakComment.getId());
			return ResultUtil.result(0, "评论成功",map);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("save ClubSpeakComment Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/***
	 * 团言评论分页
	 * @param pageCurrent
	 * @param pageSize
	 * @param speakId
	 * @return
	 */
	@PostMapping("pgcomment.do")
	public ResponseDto listComment(
			@RequestParam(name = "pn", required = true,defaultValue="0") Integer pageCurrent,
			@RequestParam(name = "ps", required = true,defaultValue="10") Integer pageSize,
			@RequestParam(required = true) Long speakId) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("speakId", speakId);
		Page<CommentVo> page = null;
		try {
			page = speakService.selectCommentByPage(pageCurrent,pageSize,map);
			userService.setFriendNickName(page.getData(), user.getId());
			return ResultUtil.result(0, "查询成功",new PageVo(page.getData(),page.getTotalCount(),page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select ClubSpeakComment page Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/***
	 * 删除团言的评论
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
			
			ClubSpeakComment csc = speakService.selectClubSpeakCommentById(id);
			if(csc == null){
				return ResultUtil.result(2, "无效数据");
			}else{
				ClubSpeak cs = speakService.selectClubSpeakById(csc.getSpeakId());
				boolean flag = memberService.userIsClubAdmin(cs.getClubId(), user.getId());
				if (!flag && csc.getPresentor().intValue() != user.getId()) {
					return ResultUtil.result(3, "没有权限");
				}
			}
			speakService.deleteClubSpeakCommentById(id,csc.getSpeakId());
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("delete ClubSpeakComment Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	
	/***
	 * 设置团言为他们说或者取消团言的他们说
	 * @param speakId
	 * @param op
	 * @return
	 */
	@PostMapping("itsaid.do")
	public ResponseDto itsaid(@RequestParam(required=true)Long speakId,@RequestParam(required=true)Integer op) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		try {
			
			ClubSpeak cs = speakService.selectClubSpeakById(speakId);
			if(cs == null){
				return ResultUtil.result(2, "无效数据");
			}
			boolean flag = memberService.userIsClubAdmin(cs.getClubId(), user.getId());
			if (!flag) {
				return ResultUtil.result(3, "没有权限");
			}
			speakService.updateClubSpeakItsaid(speakId,op);
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("update ClubSpeak itsaid Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/***
	 * 给团言点赞
	 * @param speakId
	 * @param op
	 * @return
	 */
	@PostMapping("like.do")
	public ResponseDto like(@RequestParam(required=true)Long speakId,@RequestParam(required=true)Integer op) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		try {
			ClubSpeakLike like = speakService.selectClubSpeakLikeById(speakId,user.getId());
			if(op == 1){
				if(like != null){
					return ResultUtil.result(2, "你已经点过赞了");
				}
			}else{
				if(like == null){
					return ResultUtil.result(2, "你还没有点赞");
				}
			}
			like = new ClubSpeakLike();
			like.setMemberId(user.getId());
			like.setSpeakId(speakId);
			speakService.updateClubSpeakLike(like,op);
			if(op == 1){
				ClubSpeak cs = speakService.selectClubSpeakById(speakId);
				// 加经验值
				pointService.addPoint(like.getMemberId(), PointRuleType.TYPE6.getValue(), like.getSpeakId().toString(),
						ExtidType.TYPE2.getValue());
				//被点赞者增加经验值
				pointService.addPoint(cs.getPresentor(), PointRuleType.TYPE7.getValue(), cs.getId().toString(),
						ExtidType.TYPE2.getValue());
			}
			
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("update ClubSpeakLike Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	

}
