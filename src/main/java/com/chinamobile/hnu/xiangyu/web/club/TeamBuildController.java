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

import com.chinamobile.hnu.xiangyu.club.pojo.ClubMember;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubTeamBuilding;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubTeamBuilding.Add;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubTeamBuilding.Update;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubTeamBuildingComment;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubTeamBuildingLike;
import com.chinamobile.hnu.xiangyu.club.pojo.Photo;
import com.chinamobile.hnu.xiangyu.club.service.ClubBaseService;
import com.chinamobile.hnu.xiangyu.club.service.ClubMemberService;
import com.chinamobile.hnu.xiangyu.club.service.TeamBuildService;
import com.chinamobile.hnu.xiangyu.club.vo.CommentVo;
import com.chinamobile.hnu.xiangyu.club.vo.TeamBuildVo;
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
 * 2018年5月30日
 */
@RestController
@RequestMapping("/api/club/tb/")
public class TeamBuildController {
	
	private final static Logger log = LoggerFactory.getLogger(ClubSpeakController.class);
	
	@Value("${constant.tb}")
	private String TB;
	
	@Autowired
	private UploadDocumentUtil fileUtil;
	
	@Autowired
	private AuthTokenService tokenService;
	
	@Autowired
	private TeamBuildService buildService;
	
	@Autowired
	private ClubBaseService baseService;
	
	@Autowired
	private ClubMemberService memberService;
	
	@Autowired
	private PubPointService pointService;
	
	@Autowired
	private UserService userService;
	
	
	/***
	 * 分页查询团建
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
		if(type == 1){
			map.put("type", type);
		}
		map.put("uid", user.getId());
		if(id != null){
			map.put("clubId", id);
		}else{
			return ResultUtil.result(2, "团队id不能为空");
		}
		Page<TeamBuildVo> page = null;
		try {
			page = buildService.selectByPage(pageCurrent,pageSize,map);
			userService.setFriendNickName(page.getData(), user.getId());
			return ResultUtil.result(0, "查询成功",new PageVo(page.getData(),page.getTotalCount(),page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select ClubTeamBuilding page Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/****
	 * 发布团建
	 * @param request
	 * @param clubTeamBuild
	 * @param result
	 * @return
	 */
	@PostMapping("save.do")
	public ResponseDto save(HttpServletRequest request,@Validated(Add.class) ClubTeamBuilding clubTeamBuild,BindingResult result) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		if(result.hasErrors()){
			return ResultUtil.result(2, result.getAllErrors().get(0).getDefaultMessage());
		}
		
		try {
			ClubMember member = memberService.selectUserJoin(clubTeamBuild.getClubId(),user.getId());
			if(member == null){
				return ResultUtil.result(2, "你还没有加入该团队");
			}
			clubTeamBuild.setPresentor(user.getId());
			List<Photo> photos = null;
			ResponseDto dto = fileUtil.savebatchFile(request, user.getId().toString(), TB,
					new String[] { "jpg", "png" });
			if(dto.getCode() == 2){
				return dto;
			}else if(dto.getCode() == 0){
				photos = new ArrayList<>();
				for (String fileId : (List<String>)dto.getData()) {
					Photo ctbp = new Photo();
					ctbp.setPhotoId(fileId);
					photos.add(ctbp);
				}
			}
			clubTeamBuild.setPhotos(photos);
			buildService.insertClubTeamBuilding(clubTeamBuild);
			Map<String,Object> map = new HashMap<>();
			map.put("id", clubTeamBuild.getId());
			if(clubTeamBuild.getPhotos() != null && clubTeamBuild.getPhotos().size()>0){
				map.put("photos", clubTeamBuild.getPhotos());
			}
			return ResultUtil.result(0, "发布成功",map);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("save ClubTeamBuilding Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	/***
	 * 修改团建内容
	 * @param request
	 * @param clubTeamBuild
	 * @param result
	 * @return
	 */
	@PostMapping("update.do")
	public ResponseDto update(HttpServletRequest request,@Validated(Update.class) ClubTeamBuilding clubTeamBuild,BindingResult result,
			@RequestParam(required=false)String delFileIds) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		if(result.hasErrors()){
			return ResultUtil.result(2, result.getAllErrors().get(0).getDefaultMessage());
		}
		
		try {
			ClubTeamBuilding  tb = buildService.selectClubTeamBuildingById(clubTeamBuild.getId());
			if(tb == null){
				return ResultUtil.result(2, "无效数据");
			}
			
			if(tb.getPresentor().intValue() != user.getId()){
				return ResultUtil.result(3, "没有权限");
			}
			
			List<Photo> photos = null;
			ResponseDto dto = fileUtil.savebatchFile(request, user.getId().toString(), TB,
					new String[] { "jpg", "png" });
			if(dto.getCode() == 2){
				return dto;
			}else if(dto.getCode() == 0){
				photos = new ArrayList<>();
				for (String fileId : (List<String>)dto.getData()) {
					Photo ctbp = new Photo();
					ctbp.setPhotoId(fileId);
					photos.add(ctbp);
				}
				clubTeamBuild.setPhotos(photos);
			}
			buildService.updateClubTeamBuilding(clubTeamBuild,delFileIds);
			Map<String,Object> map = new HashMap<>();
			if(clubTeamBuild.getPhotos() != null && clubTeamBuild.getPhotos().size()>0){
				map.put("photos", clubTeamBuild.getPhotos());
			}
			return ResultUtil.result(0, "修改成功",map);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("update ClubTeamBuilding Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/****
	 * 删除团建
	 * @param tbId
	 * @return
	 */
	@PostMapping("delete.do")
	public ResponseDto delete(@RequestParam(required=true)Long tbId) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		try {
			
			
			ClubTeamBuilding ctb = buildService.selectClubTeamBuildingById(tbId);
			if(ctb == null){
				return ResultUtil.result(2, "无效数据");
			}else{
				boolean flag = memberService.userIsClubAdmin(ctb.getClubId(), user.getId());
				if (!flag && ctb.getPresentor().intValue() != user.getId()) {
					return ResultUtil.result(3, "没有权限");
				}
			}
			buildService.deleteClubTeamBuildingById(tbId);
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("delete ClubTeamBuilding Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	/***
	 * 团建评论分页
	 * @param pageCurrent
	 * @param pageSize
	 * @param tbId
	 * @return
	 */
	@PostMapping("pgcomment.do")
	public ResponseDto listComment(
			@RequestParam(name = "pn", required = true,defaultValue="0") Integer pageCurrent,
			@RequestParam(name = "ps", required = true,defaultValue="10") Integer pageSize,
			@RequestParam(required = true) Long tbId) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tbId", tbId);
		Page<CommentVo> page = null;
		try {
			page = buildService.selectCommentByPage(pageCurrent,pageSize,map);
			userService.setFriendNickName(page.getData(), user.getId());
			return ResultUtil.result(0, "查询成功",new PageVo(page.getData(),page.getTotalCount(),page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select ClubTeamBuildingComment page Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	
	/****
	 * 发表团建评论
	 * @param request
	 * @param BuildComment
	 * @param result
	 * @return
	 */
	@PostMapping("comment.do")
	public ResponseDto comment(HttpServletRequest request,@Validated ClubTeamBuildingComment buildComment,BindingResult result) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		if(result.hasErrors()){
			return ResultUtil.result(2, result.getAllErrors().get(0).getDefaultMessage());
		}
		
		try {
			ClubTeamBuilding tb = buildService.selectClubTeamBuildingById(buildComment.getTbId());
			ClubMember member = memberService.selectUserJoin(tb.getClubId(),user.getId());
			if(member == null){
				return ResultUtil.result(2, "你还没有加入该团队");
			}
			buildComment.setPresentor(user.getId());
			buildService.insertTeamBuildComment(buildComment);
			//增加经验值
			pointService.addPoint(buildComment.getPresentor(), PointRuleType.TYPE6.getValue(), buildComment.getId().toString(),
					ExtidType.TYPE1.getValue());
			//被评论者增加经验值
			pointService.addPoint(tb.getPresentor(), PointRuleType.TYPE7.getValue(), tb.getId().toString(),
					ExtidType.TYPE1.getValue());
			Map<String,Object> map = new HashMap<>();
			map.put("id", buildComment.getId());
			return ResultUtil.result(0, "评论成功",map);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("save ClubTeamBuildingComment Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/***
	 * 删除团建评论
	 * @param tbId
	 * @return
	 */
	@PostMapping("delcomment.do")
	public ResponseDto delcomment(@RequestParam(required=true)Long id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			ClubTeamBuildingComment ctbc = buildService.selectClubTeamBuildingCommentById(id);
			if(ctbc == null){
				return ResultUtil.result(2, "无效数据");
			}else{
				ClubTeamBuilding ctb = buildService.selectClubTeamBuildingById(ctbc.getTbId());
				if(ctb == null){
					return ResultUtil.result(2, "无效数据");
				}
				boolean flag = memberService.userIsClubAdmin(ctb.getClubId(), user.getId());
				if (!flag && ctbc.getPresentor().intValue() != user.getId()) {
					return ResultUtil.result(3, "没有权限");
				}
			}
			buildService.deleteClubTeamBuildingCommentById(id,ctbc.getTbId());
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("delete ClubTeamBuildingComment Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	
	/****
	 * 推荐到大厅
	 * @param tbId
	 * @param op
	 * @return
	 */
	@PostMapping("hall.do")
	public ResponseDto itsaid(@RequestParam(required=true)Long tbId,@RequestParam(required=true)Integer op) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		try {
			ClubTeamBuilding ctb = buildService.selectClubTeamBuildingById(tbId);
			if(ctb == null){
				return ResultUtil.result(2, "无效数据");
			}else{
				boolean flag = memberService.userIsClubAdmin(ctb.getClubId(), user.getId());
				if (!flag && ctb.getPresentor().intValue() != user.getId()) {
					return ResultUtil.result(3, "没有权限");
				}
			}
			buildService.updateClubTeamBuildingRecommend(tbId,op);
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("update ClubTeamBuilding Recommend Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/****
	 * 给团建点赞
	 * @param tbId
	 * @param op
	 * @return
	 */
	@PostMapping("like.do")
	public ResponseDto like(@RequestParam(required=true)Long tbId,@RequestParam(required=true)Integer op) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		
		try {
			ClubTeamBuildingLike like = buildService.selectClubTeamBuildingLikeById(tbId,user.getId());
			if(op == 1){
				if(like != null){
					return ResultUtil.result(2, "你已经点过赞了");
				}
			}else{
				if(like == null){
					return ResultUtil.result(2, "你还没有点赞");
				}
			}
			
			like = new ClubTeamBuildingLike();
			like.setMemberId(user.getId());
			like.setTbId(tbId);
			buildService.updateClubTeamBuildingLike(like,op);
			if(op == 1){
				ClubTeamBuilding tb = buildService.selectClubTeamBuildingById(tbId);
				// 加经验值
				pointService.addPoint(like.getMemberId(), PointRuleType.TYPE6.getValue(), like.getTbId().toString(),
						ExtidType.TYPE2.getValue());
				//被点赞者增加经验值
				pointService.addPoint(tb.getPresentor(), PointRuleType.TYPE7.getValue(), tb.getId().toString(),
						ExtidType.TYPE2.getValue());
			}
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("update ClubTeamBuildingLike Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	

}
