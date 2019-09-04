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

import org.apache.commons.lang3.StringUtils;
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
import com.chinamobile.hnu.xiangyu.club.pojo.ClubInfo.Add;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubInfo.Update;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubInfo.UpdateBase;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubMember;
import com.chinamobile.hnu.xiangyu.club.service.ClubBaseService;
import com.chinamobile.hnu.xiangyu.club.service.ClubMemberService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubVo;
import com.chinamobile.hnu.xiangyu.club.vo.Visibility;
import com.chinamobile.hnu.xiangyu.common.enums.ExtidType;
import com.chinamobile.hnu.xiangyu.common.enums.PointRuleType;
import com.chinamobile.hnu.xiangyu.common.pojo.SysUserNews;
import com.chinamobile.hnu.xiangyu.common.service.PubPointService;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.pojo.UserFavorite;
import com.chinamobile.hnu.xiangyu.user.pojo.UserLabel;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.user.service.UserAccountService;
import com.chinamobile.hnu.xiangyu.user.service.UserService;
import com.chinamobile.hnu.xiangyu.util.ElasticSearchUtil;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;
import com.um.service.UmengService;

/**
 * @author The Old Man and the Sea
 *
 *         2018年5月28日
 */
@RestController
@RequestMapping("/api/club/base/")
public class ClubBaseController {

	private static Logger log = LoggerFactory
			.getLogger(ClubBaseController.class);

	@Value("${constant.logo}")
	private String LOGO;

	@Value("${constant.cover}")
	private String COVER;

	@Autowired
	private UploadDocumentUtil fileUtil;

	@Autowired
	private AuthTokenService tokenService;

	@Autowired
	private ClubBaseService baseService;

	@Autowired
	private UserAccountService accountService;

	@Autowired
	private UserService userService;

	@Autowired
	private ClubMemberService memberService;

	@Autowired
	private PubPointService pointService;

	@Autowired
	private UmengService umengService;

	/****
	 * 分页查询团队列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param type
	 *            1：热门团队；2：最新的团队；3：我的团队；4：收藏;5.推荐的团队;6我创建的
	 * @return
	 */
	@PostMapping("pglist.do")
	public ResponseDto list(
			@RequestParam(name = "pn", required = true, defaultValue = "0") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "10") Integer pageSize,
			@RequestParam(required = true) Integer type,
			@RequestParam(required = false) Integer uid,
			@RequestParam(required = false) String keyword) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (uid != null) {
			map.put("type", type == 6 ? 6 : 3);
			map.put("uid", uid);
			map.put("my", 1);
		} else {
			if (type == 5) {
				List<UserLabel> labels = userService.selectUserLabel(user
						.getId());
				// 如果用户没有设置标签，给用户推荐热门团队
				if (labels.size() > 0) {
					map.put("school", user.getCollege());
					map.put("type", type);
					map.put("labels", labels);
				} else {
					map.put("type", 1);
				}
			} else {
				map.put("type", type);
			}
			map.put("uid", user.getId());
		}

		if (StringUtils.isNotBlank(keyword)) {
			// 调用搜索引擎
			if (type == 1 || type == 2) {
				Map<String, String> params = new HashMap<>();
				params.put("name", keyword);
				params.put("introduction", keyword);
				params.put("college", keyword);
				Map<String, String> returnPropertys = new HashMap<>();
				returnPropertys.put("id", "{}");
				List<Long> idList = ElasticSearchUtil.getIdList("tag", params,
						returnPropertys,
						((pageCurrent <= 0 ? 1 : pageCurrent) - 1) * pageSize,
						pageSize);
				if (idList != null && idList.size() > 0) {
					map.put("idList", idList);
					pageCurrent = 0;
				} else {
					return ResultUtil.result(0, "查询成功", new PageVo(
							new ArrayList<>(), 0L, 0));
				}
			} else {
				map.put("keyword", keyword);
			}
		}

		Page<ClubVo> page = null;
		try {
			page = baseService.selectByPage(pageCurrent, pageSize, map);
			userService.setFriendNickName(page.getData(), user.getId());
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select clubInfo page Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/***
	 * 通过标签查询团队
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param lableId
	 * @return
	 */
	@PostMapping("labelList.do")
	public ResponseDto Labellist(
			@RequestParam(name = "pn", required = true, defaultValue = "0") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "10") Integer pageSize,
			@RequestParam String label) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("label", label);
		map.put("school", user.getCollege());
		Page<ClubVo> page = null;
		try {
			page = baseService.selectByLabelPage(pageCurrent, pageSize, map);
			userService.setFriendNickName(page.getData(), user.getId());
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("save clubInfo page Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/***
	 * 创建团队
	 * 
	 * @param clubInfo
	 * @param result
	 * @return
	 */
	@PostMapping("save.do")
	public ResponseDto save(HttpServletRequest request,
			@Validated({ Add.class }) ClubInfo clubInfo, BindingResult result) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}

		if (result.hasErrors()) {
			return ResultUtil.result(2, result.getAllErrors().get(0)
					.getDefaultMessage());
		}

		try {
			Map<String, Object> params = new HashMap<>();
			params.put("name", clubInfo.getName());
			params.put("college", user.getCollege());

			List<ClubInfo> list = baseService.selectClubInfoByMap(params);
			if (list.size() > 0) {
				return ResultUtil.result(2, "学校已经存在该社团名称");
			}
			ResponseDto dto = fileUtil.saveFile(request, user.getId()
					.toString(), LOGO, new String[] { "jpg", "png" });
			if (dto.getCode() == 0) {
				clubInfo.setLogoImage(dto.getData().toString());
			} else {
				return dto;
			}

			clubInfo.setCreator(user.getId());
			clubInfo.setUser(user);
			clubInfo.setCollege(user.getCollege());
			baseService.insertClubInfo(clubInfo);
			Map<String, Object> map = new HashMap<>();
			map.put("id", clubInfo.getId());
			map.put("logo",
					UploadDocumentUtil.buildPublicPath(clubInfo.getLogoImage()));
			return ResultUtil.result(0, "创建成功", map);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("save clubInfo Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/***
	 * 修改团队名称和logo
	 * 
	 * @param clubInfo
	 * @param result
	 * @return
	 */
	@PostMapping("updatebase.do")
	public ResponseDto updatebase(HttpServletRequest request,
			@Validated({ UpdateBase.class }) ClubInfo clubInfo,
			BindingResult result) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}

		if (result.hasErrors()) {
			return ResultUtil.result(2, result.getAllErrors().get(0)
					.getDefaultMessage());
		}

		try {
			boolean flag = memberService.userIsClubAdmin(clubInfo.getId(),
					user.getId());
			if (!flag) {
				return ResultUtil.result(2, "需要管理员操作");
			}
			Map<String, Object> map = new HashMap<>();
			ResponseDto dto = fileUtil.saveFile(request, user.getId()
					.toString(), LOGO, new String[] { "jpg", "png" });
			if (dto.getCode() == 2) {
				return dto;
			} else if (dto.getCode() == 0) {
				clubInfo.setLogoImage(dto.getData().toString());
				map.put("logo", UploadDocumentUtil.buildPublicPath(clubInfo
						.getLogoImage()));
			}

			baseService.updateClubInfo(clubInfo);
			return ResultUtil.result(0, "修改成功", map);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("update base clubInfo Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/***
	 * 修改团队封面图片和简介
	 * 
	 * @param clubInfo
	 * @param result
	 * @return
	 */
	@PostMapping("update.do")
	public ResponseDto update(HttpServletRequest request,
			@Validated({ Update.class }) ClubInfo clubInfo, BindingResult result) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}

		if (result.hasErrors()) {
			return ResultUtil.result(2, result.getAllErrors().get(0)
					.getDefaultMessage());
		}

		try {
			boolean flag = memberService.userIsClubAdmin(clubInfo.getId(),
					user.getId());
			if (!flag) {
				return ResultUtil.result(2, "需要管理员操作");
			}
			ResponseDto dto = fileUtil.saveFile(request, user.getId()
					.toString(), COVER, new String[] { "jpg", "png" });
			if (dto.getCode() == 2) {
				return dto;
			} else if (dto.getCode() == 0) {
				clubInfo.setCoverImage(dto.getData().toString());
			}
			baseService.updateClubInfo(clubInfo);
			return ResultUtil.result(0, "修改成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("update clubInfo Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/****
	 * 查询团队详情信息
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("detail.do")
	public ResponseDto detail(@RequestParam(required = true) Integer id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}

		try {
			ClubVo vo = baseService.selectDetailById(id, user.getId());
			if (vo != null) {
				boolean flag = memberService.userIsClubAdmin(id, user.getId());
				vo.setAdmin(flag);
			}
			userService.setFriendNickName(vo, user.getId());
			userService.setFriendNickName(vo.getUsers(), user.getId());
			return ResultUtil.result(0, "查询成功", vo);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select clubInfo detail Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/****
	 * 收藏/取消收藏团队
	 * 
	 * @param id
	 * @param op
	 * @return
	 */
	@PostMapping("favorite.do")
	public ResponseDto favorite(@RequestParam(required = true) Integer id,
			@RequestParam(required = true) Integer op) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		UserFavorite favorite = null;

		try {
			if (op == 1) {
				favorite = baseService.selectFavoriteByClubId(id, user.getId());
				if (favorite != null) {
					return ResultUtil.result(2, "你已经收藏过了");
				}
			}
			favorite = new UserFavorite();
			favorite.setUserId(user.getId());
			favorite.setCategory(Byte.valueOf("2"));
			favorite.setBizId(Long.valueOf(id));
			baseService.favorite(favorite, op);
			if (op == 1) {
				ClubInfo clubInfo = baseService.selectClubInfoById(id);
				// 加经验值
				pointService.addPoint(favorite.getUserId(), PointRuleType.TYPE6
						.getValue(), favorite.getId().toString(),
						ExtidType.TYPE3.getValue());
				// 被收藏者增加经验值
				pointService.addPoint(clubInfo.getCreator(),
						PointRuleType.TYPE7.getValue(), clubInfo.getId()
								.toString(), ExtidType.TYPE3.getValue());
			}
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("favorite clubInfo Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 是否同意入团
	 * 
	 * @param id
	 *            ：社团ID
	 * @param userId
	 *            :用户ID
	 * @param nid
	 *            :消息ID
	 * 
	 * @param op
	 *            :1-同意入团，3拒绝入团
	 * @return
	 */
	@PostMapping("apply.do")
	public ResponseDto apply(@RequestParam(required = true) Integer id,
			@RequestParam(required = true) Integer nid,
			@RequestParam(required = true) Integer userId,
			@RequestParam(required = true) Integer op) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			// 新增团队成员
			ClubMember member = new ClubMember();
			member.setClubId(id);
			member.setMemberId(userId);
			UserAccount users = accountService.selectByPrimaryKey(userId);
			memberService.inertClubMember(member, op, userId, nid);
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("apply join clubInfo Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/****
	 * 加入或退出团队
	 * 
	 * @param id
	 *            团队id
	 * @param op
	 *            1-加入此团队；2-退出团队
	 * 
	 * @param nid
	 *            :消息ID
	 * @return
	 */
	@PostMapping("join.do")
	public ResponseDto join(@RequestParam(required = true) Integer id,
			@RequestParam(required = true) Integer nid, @RequestParam Integer op) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			ClubInfo clubInfo = baseService.selectClubInfoById(id);
			if (clubInfo != null) {
				if (clubInfo.getCreator().intValue() == user.getId()) {
					return ResultUtil.result(2, "不能这么做");
				}
				// 新增团队成员
				ClubMember member = new ClubMember();
				member.setClubId(id);
				member.setMemberId(user.getId());
				member.setNickname(user.getNickname());
				memberService.inertClubMember(member, op, user.getId(), nid);
			}
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("join clubInfo Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/****
	 * 踢出群成员
	 * 
	 * @param clubId
	 * @param uid
	 * @return
	 */
	@PostMapping("expel.do")
	public ResponseDto expel(@RequestParam(required = true) Integer clubId,
			@RequestParam(required = true) Integer uid) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			boolean flag = memberService.userIsClubAdmin(clubId, user.getId());
			if (!flag) {
				return ResultUtil.result(3, "没有权限");
			}
			memberService.deleteClubMember(clubId, uid, user.getId());
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("expel clubmember Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/***
	 * 查询团队的可见性
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("visibility.do")
	public ResponseDto visibility(@RequestParam(required = true) Integer id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			Visibility visibility = baseService.selectVisibilityId(id);
			return ResultUtil.result(0, "操作成功", visibility);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select clubInfo visibility Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/***
	 * 修改团队的可见性
	 * 
	 * @param id
	 * @param club
	 * @param speak
	 * @param activity
	 * @param notice
	 * @param vote
	 * @return
	 */
	@PostMapping("setvisibility.do")
	public ResponseDto setvisibility(@Validated Visibility vi,
			BindingResult result) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		if (result.hasErrors()) {
			return ResultUtil.result(2, result.getAllErrors().get(0)
					.getDefaultMessage());
		}
		try {
			boolean flag = memberService.userIsClubAdmin(vi.getId(),
					user.getId());
			if (!flag) {
				return ResultUtil.result(2, "需要管理员操作");
			}

			baseService.insertVisibility(vi);
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("set clubInfo visibility Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 申请加入团队，保存到通知表
	 * 
	 * @param id
	 *            :社团id
	 * @param content
	 *            :申请内容
	 * @return
	 */
	@PostMapping("savaSysUserNews.do")
	public ResponseDto savaSysUserNews(
			@RequestParam(name = "id", required = true) Integer id,
			@RequestParam(name = "content", required = false) String content) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("replyId", user.getId());
			map.put("bizId", Long.valueOf(id));
			if (StringUtils.isNotBlank(content)) {
				map.put("content", content);
			}
			SysUserNews userNews = new SysUserNews();
			userNews.setBizId(Long.valueOf(id));
			userNews.setReplyId(user.getId());
			userNews.setContent(content);
			userNews.setGmtCreate(new Date());
			userNews.setCategory(2);
			baseService.savaSysUserNews(userNews, id, map);
			return ResultUtil.result(0, "申请成功");
		} catch (Exception e) {
			log.info("save savaSysUserNews Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/****
	 * 分享活动或投票
	 * 
	 * @param id
	 * @param type
	 * @return
	 */
	@PostMapping("share.do")
	public ResponseDto share(@RequestParam Integer id,
			@RequestParam Integer type) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			// 加经验值
			pointService.addPoint(user.getId(),
					type == 1 ? PointRuleType.TYPE5.getValue()
							: PointRuleType.TYPE3.getValue(), id.toString(),
					ExtidType.TYPE3.getValue());
			return ResultUtil.result(0, "分享成功");
		} catch (Exception e) {
			log.info("share Exception:", e);
			return ResultUtil.serviceException();
		}
	}
}
