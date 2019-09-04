/**  

 * <p>Title: ClubAdminController.java</p>  

 * <p>Description: </p>  

 * <p>Copyright: Copyright douziit(c) 2017</p>  

 * @author lh  

 * @date 2018年6月15日  

 * @version 1.0  

 */
package com.chinamobile.hnu.xiangyu.web.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinamobile.hnu.xiangyu.auth.service.IAuthResourcesService;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubActivity;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubActivityComment;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubInfo;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubMember;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubNotice;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubSpeakComment;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubTeamBuildingComment;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubVoteInfo;
import com.chinamobile.hnu.xiangyu.club.service.ClubActivityService;
import com.chinamobile.hnu.xiangyu.club.service.ClubBaseService;
import com.chinamobile.hnu.xiangyu.club.service.ClubMemberService;
import com.chinamobile.hnu.xiangyu.club.service.ClubNoticeService;
import com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService;
import com.chinamobile.hnu.xiangyu.club.service.ClubVoteService;
import com.chinamobile.hnu.xiangyu.club.service.TeamBuildService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubNoticeVo;
import com.chinamobile.hnu.xiangyu.club.vo.CommentVo;
import com.chinamobile.hnu.xiangyu.common.pojo.SysUserNews;
import com.chinamobile.hnu.xiangyu.common.service.ISysUserNewsService;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.Utils;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;
import com.chinamobile.hnu.xiangyu.web.api.vo.AclubActivityInfoVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.AclubActivityVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.AclubInfoVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.AclubVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.ClubSpeackInfoVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.ClubSpeackVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.TeamBuildingVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.VoteInfoVo;
import com.um.pojo.Alarm;
import com.um.service.UmengService;

/**
 * 
 * <p>
 * Title: ClubAdminController.java
 * </p>
 * 
 * <p>
 * Description: 团队管理(后台管理端)
 * </p>
 * 
 * @author lh
 * 
 * @date 2018年6月15日
 * 
 * @version 1.0
 */
@RestController
@RequestMapping("/api/admin/club/")
public class ClubAdminController {
	private final Logger logger = Logger.getLogger(ClubAdminController.class);

	@Autowired
	private AuthTokenService tokenService;

	@Autowired
	private ClubActivityService activityService;

	@Autowired
	private ClubMemberService memberService;

	@Autowired
	private TeamBuildService teamBuildService;

	@Autowired
	private ClubSpeakService speackService;

	@Autowired
	private ClubVoteService voteService;

	@Autowired
	private ClubNoticeService noticeService;

	@Autowired
	private ClubBaseService baseService;

	@Autowired
	private ISysUserNewsService newsService;

	@Autowired
	private IAuthResourcesService resourcesService;

	@Autowired
	private UmengService umengService;

	/**
	 * 团队列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param name
	 *            :社团名称（查询）
	 * @param startTime
	 *            ：开始时间（查询）
	 * @param endTime
	 *            :结束时间（查询）
	 * @param status
	 *            :社团状态。0-正常；1-封禁
	 * @return
	 */
	@PostMapping("clubList.do")
	public ResponseDto clubList(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		// String name = (String) maps.get("name");
		// String startTime = (String) maps.get("startTime");
		// String endTime = (String) maps.get("endTime");
		// Integer status = (Integer) maps.get("status");
		// String college = (String) maps.get("college");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// 判断是否有权限
		List<String> listMaps = resourcesService.selectUserResourceLists(user
				.getRoleId());
		if (listMaps.size() > 0) {
			if (!listMaps.contains("/team")) {
				return ResultUtil.result(5, "暂无权限!");
			}
		} else {
			return ResultUtil.result(5, "暂无权限!");
		}
		// Map<String, Object> map = new HashMap<String, Object>();
		// if (StringUtils.isNotBlank(name)) {
		// map.put("name", name);
		// }
		// if (StringUtils.isNotBlank(startTime)) {
		// map.put("startTime", startTime);
		// }
		// if (StringUtils.isNotBlank(endTime)) {
		// map.put("endTime", endTime);
		// }
		// if (StringUtils.isNotBlank(endTime)) {
		// map.put("endTime", endTime);
		// }
		// if (status != null) {
		// map.put("status", status);
		// }
		// if (StringUtils.isNotBlank(college)) {
		// map.put("college", college);
		// }
		try {
			Page<AclubVo> page = baseService.selectByclubList(maps,
					pageCurrent, pageSize);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("clubList is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 团队详情
	 * 
	 * @param clubId
	 * @return
	 */
	@PostMapping("clubInfo.do")
	public ResponseDto clubInfo(@RequestBody Map<String, Object> maps) {
		Integer clubId = (Integer) maps.get("clubId");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			AclubInfoVo infoVo = baseService.selectClubInfoByclubId(clubId);
			return ResultUtil.result(0, "查询成功", infoVo);
		} catch (Exception e) {
			logger.error("clubInfo is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 团队详情（成员）
	 * 
	 * @param clubId
	 * @return
	 */
	@PostMapping("clubInfoByUser.do")
	public ResponseDto clubInfoByUser(@RequestBody Map<String, Object> maps) {
		Integer clubId = (Integer) maps.get("clubId");
		String nickname = (String) maps.get("nickname");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("clubId", clubId);
		if (StringUtils.isNotBlank(nickname)) {
			map.put("nickname", nickname);
		}
		try {
			List<ClubMember> cm = memberService.selectClubMemberByClubId(map);
			return ResultUtil.result(0, "查询成功", cm);
		} catch (Exception e) {
			logger.error("clubInfo is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 封禁or解封团队
	 * 
	 * @param clubId
	 * @param status
	 *            :0-正常；1-封禁
	 * @return
	 */
	@PostMapping("clubInfoStatus.do")
	public ResponseDto clubInfoStatus(@RequestBody Map<String, Object> maps) {
		Integer clubId = (Integer) maps.get("clubId");
		Integer status = (Integer) maps.get("status");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			ClubInfo clubInfo = new ClubInfo();
			clubInfo.setId(clubId);
			clubInfo.setStatus((byte) status.intValue());
			baseService.updateClubInfo(clubInfo);

			// 查询团队创建人
			ClubInfo info = baseService.selectClubInfoById(clubId);
			SysUserNews record = new SysUserNews();
			record.setBizId(Long.valueOf(clubId));
			record.setReplyId(user.getId());
			record.setReceiveId(info.getCreator());
			record.setGmtCreate(new Date());
			record.setCategory(2);
			record.setStatus(4);
			if (status.equals(1)) {
				// 发送系统消息
				record.setContent("您的团队：<" + info.getName()
						+ ">涉嫌违规已被封禁，具体详情请联系系统管理员！");
				newsService.insertSelective(record);
				// 友盟推送消息
				try {
					Alarm alarm = new Alarm();
					alarm.setTitle("团队封禁提醒");
					alarm.setType(1);
					alarm.setMsg("您的团队：<" + info.getName()
							+ ">涉嫌违规已被封禁，具体详情请联系系统管理员！");
					alarm.setReceiveId(info.getCreator());
					umengService.sendAndroidBroadcast(alarm);
					umengService.sendIOSCustomizedcast(alarm);
				} catch (Exception e) {
					logger.error("send umeng msg  is fail", e);
				}
			} else {
				record.setContent("您的团队：<" + info.getName() + ">已解封！");
				newsService.insertSelective(record);
				// 友盟推送消息
				try {
					Alarm alarm = new Alarm();
					alarm.setTitle("团队解封提醒");
					alarm.setType(1);
					alarm.setMsg("您的团队：<" + info.getName() + ">已解封！");
					alarm.setReceiveId(info.getCreator());
					umengService.sendAndroidBroadcast(alarm);
					umengService.sendIOSCustomizedcast(alarm);
				} catch (Exception e) {
					logger.error("send umeng msg  is fail", e);
				}
			}
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			logger.error("clubInfo is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 团建列表（成员）
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@PostMapping("teamBuildingList.do")
	public ResponseDto teamBuildingList(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		Integer clubId = (Integer) maps.get("clubId");
		String title = (String) maps.get("title");
		String startTime = (String) maps.get("startTime");
		String endTime = (String) maps.get("endTime");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// 判断是否有权限
		List<String> listMaps = resourcesService.selectUserResourceLists(user
				.getRoleId());
		if (listMaps.size() > 0) {
			if (!listMaps.contains("/team")) {
				return ResultUtil.result(5, "暂无权限!");
			}
		} else {
			return ResultUtil.result(5, "暂无权限!");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(title)) {
			map.put("title", title);
		}
		if (StringUtils.isNotBlank(startTime)) {
			map.put("startTime", startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime", endTime);
		}
		map.put("clubId", clubId);
		try {
			Page<TeamBuildingVo> page = teamBuildService.selectTbPage(
					pageCurrent, pageSize, map);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("teamBuildingList is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 团建详情
	 * 
	 * @param clubId
	 * @return
	 */
	@PostMapping("buildingInfo.do")
	public ResponseDto buildingInfo(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			TeamBuildingVo tb = teamBuildService.selectById(id);
			return ResultUtil.result(0, "查询成功", tb);
		} catch (Exception e) {
			logger.error("buildingInfo is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 团价评论(分页)
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param id
	 * @return
	 */
	@PostMapping("tbComment.do")
	public ResponseDto tbComment(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tbId", id);
			Page<CommentVo> page = teamBuildService.selectCommentByPage(
					pageCurrent, pageSize, map);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("tbComment is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 删除团建评价
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("deltbComment.do")
	public ResponseDto deltbComment(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			ClubTeamBuildingComment ctbc = teamBuildService
					.selectClubTeamBuildingCommentById(Long.valueOf(id));
			if (ctbc != null) {
				teamBuildService.deleteClubTeamBuildingCommentById(
						Long.valueOf(id), ctbc.getTbId());
			}

			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("DeltbComment is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 团建删除
	 * 
	 * @param maps
	 * @return
	 */
	@PostMapping("deltb.do")
	public ResponseDto deltb(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			teamBuildService.deleteClubTeamBuildingById(Long.valueOf(id));
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("DeltbComment is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 团言列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@PostMapping("clubSpeakList.do")
	public ResponseDto clubSpeakList(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		Integer clubId = (Integer) maps.get("clubId");
		String name = (String) maps.get("name");
		String startTime = (String) maps.get("startTime");
		String endTime = (String) maps.get("endTime");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// 判断是否有权限
		List<String> listMaps = resourcesService.selectUserResourceLists(user
				.getRoleId());
		if (listMaps.size() > 0) {
			if (!listMaps.contains("/team")) {
				return ResultUtil.result(5, "暂无权限!");
			}
		} else {
			return ResultUtil.result(5, "暂无权限!");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(name)) {
			map.put("name", name);
		}
		if (StringUtils.isNotBlank(startTime)) {
			map.put("startTime", startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime", endTime);
		}
		map.put("clubId", clubId);
		try {
			Page<ClubSpeackVo> page = speackService.selectClubSpeakList(
					pageCurrent, pageSize, map);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("clubSpeakList is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 团言详情
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("clubSpeakInfo.do")
	public ResponseDto clubSpeakInfo(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			ClubSpeackInfoVo csi = speackService.selectClubSpeakInfo(Long
					.valueOf(id));
			return ResultUtil.result(0, "查询成功", csi);
		} catch (Exception e) {
			logger.error("clubSpeakInfo is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 团言评论列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param id
	 *            ：团言id
	 * @return
	 */
	@PostMapping("speakCommentList.do")
	public ResponseDto speakCommentList(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("speakId", id);
		try {
			Page<CommentVo> page = speackService.selectCommentByPage(
					pageCurrent, pageSize, map);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("SpeakCommentList is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 删除团言评论
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("delSpeakComment.do")
	public ResponseDto delSpeakComment(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {

			ClubSpeakComment csc = speackService
					.selectClubSpeakCommentById(Long.valueOf(id));
			if (csc != null) {
				speackService.deleteClubSpeakCommentById(Long.valueOf(id),
						csc.getSpeakId());
			}

			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("DelSpeakComment is err:", e);
			return ResultUtil.serviceException();
		}
	}

	// 团言禁言or取消禁言
	/**
	 * 团言删除
	 * 
	 * @param maps
	 * @return
	 */
	@PostMapping("delSpeak.do")
	public ResponseDto delSpeak(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			speackService.deleteClubSpeakById(Long.valueOf(id));
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("DeltbComment is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 活动列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@PostMapping("clubActivityList.do")
	public ResponseDto clubActivityList(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		Integer clubId = (Integer) maps.get("clubId");
		String name = (String) maps.get("name");
		String startTime = (String) maps.get("startTime");
		String endTime = (String) maps.get("endTime");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// 判断是否有权限
		List<String> listMaps = resourcesService.selectUserResourceLists(user
				.getRoleId());
		if (listMaps.size() > 0) {
			if (!listMaps.contains("/team")) {
				return ResultUtil.result(5, "暂无权限!");
			}
		} else {
			return ResultUtil.result(5, "暂无权限!");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(name)) {
			map.put("name", name);
		}
		if (StringUtils.isNotBlank(startTime)) {
			map.put("startTime", startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime", endTime);
		}
		map.put("clubId", clubId);
		try {
			Page<AclubActivityVo> page = activityService.selectActivityList(
					pageCurrent, pageSize, map);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("clubActivityList is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 活动详情
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("clubActivityInfo.do")
	public ResponseDto clubActivityInfo(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			AclubActivityInfoVo ai = activityService.selectActivityInfo(Long
					.valueOf(id));
			return ResultUtil.result(0, "查询成功", ai);
		} catch (Exception e) {
			logger.error("clubSpeakInfo is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 活动评论分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param id
	 * @return
	 */
	@PostMapping("activityCommentList.do")
	public ResponseDto activityCommentList(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityId", id);
		try {
			Page<CommentVo> page = activityService.selectCommentByPage(
					pageCurrent, pageSize, map);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("SpeakCommentList is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 删除评论
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("activityComment.do")
	public ResponseDto activityComment(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {

			ClubActivityComment cac = activityService
					.selectClubActivityCommentById(Long.valueOf(id));
			if (cac != null) {
				activityService.deleteClubActivityCommentById(Long.valueOf(id),
						cac.getActivityId());
			}

			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("activityComment is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 活动删除
	 * 
	 * @param maps
	 * @return
	 */
	@PostMapping("delActivity.do")
	public ResponseDto delActivity(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			ClubActivity ca = new ClubActivity();
			ca.setStatus((byte) 9);
			ca.setId(Long.valueOf(id));
			activityService.deleteClubActivityById(Long.valueOf(id));
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("DeltbComment is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 投票列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @param clubId
	 * @return
	 */
	@PostMapping("voteInfoList.do")
	public ResponseDto voteInfoList(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		Integer clubId = (Integer) maps.get("clubId");
		String name = (String) maps.get("name");
		String startTime = (String) maps.get("startTime");
		String endTime = (String) maps.get("endTime");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// 判断是否有权限
		List<String> listMaps = resourcesService.selectUserResourceLists(user
				.getRoleId());
		if (listMaps.size() > 0) {
			if (!listMaps.contains("/team")) {
				return ResultUtil.result(5, "暂无权限!");
			}
		} else {
			return ResultUtil.result(5, "暂无权限!");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(name)) {
			map.put("name", name);
		}
		if (StringUtils.isNotBlank(startTime)) {
			map.put("startTime", startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime", endTime);
		}
		map.put("clubId", clubId);
		try {
			Page<ClubSpeackVo> page = voteService.selectVoteList(pageCurrent,
					pageSize, map);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("voteInfoList is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 投票详情
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("voteInfo.do")
	public ResponseDto voteInfo(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		List<VoteInfoVo> vo = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			vo = voteService.selectVoteInfoByVoteId(Long.valueOf(id));
			if (vo.size() > 0) {
				map.put("header", vo.get(0).getHeader());
				map.put("nickName", vo.get(0).getNickname());
				if (vo.get(0).getDeadline() != null) {
					map.put("Deadline",
							Utils.formatMiddle(vo.get(0).getDeadline()));
				}
				map.put("time", vo.get(0).getTime());
			}
			map.put("map", vo);
			return ResultUtil.result(0, "查询成功", map);
		} catch (Exception e) {
			logger.error("voteInfo is err:", e);
			return ResultUtil.serviceException();
		}
	}

	// 投票_禁言or取消禁言
	/**
	 * 投票删除
	 * 
	 * @param maps
	 * @return
	 */
	@PostMapping("delVote.do")
	public ResponseDto delVote(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			ClubVoteInfo record = new ClubVoteInfo();
			record.setId(Long.valueOf(id));
			record.setStatus((byte) 9);
			voteService.deleteDeleteClubVoteById(Long.valueOf(id));
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("DeltbComment is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 公告列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @param clubId
	 * @return
	 */
	@PostMapping("clubNoticeList.do")
	public ResponseDto clubNoticeList(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		Integer clubId = (Integer) maps.get("clubId");
		String name = (String) maps.get("name");
		String startTime = (String) maps.get("startTime");
		String endTime = (String) maps.get("endTime");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// 判断是否有权限
		List<String> listMaps = resourcesService.selectUserResourceLists(user
				.getRoleId());
		if (listMaps.size() > 0) {
			if (!listMaps.contains("/team")) {
				return ResultUtil.result(5, "暂无权限!");
			}
		} else {
			return ResultUtil.result(5, "暂无权限!");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(name)) {
			map.put("name", name);
		}
		if (StringUtils.isNotBlank(startTime)) {
			map.put("startTime", startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime", endTime);
		}
		map.put("clubId", clubId);
		try {
			Page<ClubNoticeVo> page = noticeService.selectByPage(pageCurrent,
					pageSize, map);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("clubNoticeList is err:", e);
			return ResultUtil.serviceException();
		}
	}

	// 公告禁言
	/**
	 * 投票删除
	 * 
	 * @param maps
	 * @return
	 */
	@PostMapping("delNotice.do")
	public ResponseDto delNotice(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			ClubNotice record = new ClubNotice();
			record.setId(Long.valueOf(id));
			record.setStatus((byte) 9);
			noticeService.deleteClubNoticeById(Long.valueOf(id));
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("DeltbComment is err:", e);
			return ResultUtil.serviceException();
		}
	}

	// 查询个人信息

}
