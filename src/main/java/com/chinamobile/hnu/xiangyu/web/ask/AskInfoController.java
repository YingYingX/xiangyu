package com.chinamobile.hnu.xiangyu.web.ask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskInfo;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskLabel;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskPhoto;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskVisitHistory;
import com.chinamobile.hnu.xiangyu.ask.service.IAskCommentReplyService;
import com.chinamobile.hnu.xiangyu.ask.service.IAskCommentService;
import com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService;
import com.chinamobile.hnu.xiangyu.ask.service.IAskVisitHistoryService;
import com.chinamobile.hnu.xiangyu.ask.vo.AskCommentReplyVo;
import com.chinamobile.hnu.xiangyu.ask.vo.AskCommentVo;
import com.chinamobile.hnu.xiangyu.ask.vo.AskInfoVo;
import com.chinamobile.hnu.xiangyu.common.enums.ExtidType;
import com.chinamobile.hnu.xiangyu.common.enums.PointRuleType;
import com.chinamobile.hnu.xiangyu.common.service.PubPointService;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.pojo.UserLabel;
import com.chinamobile.hnu.xiangyu.user.pojo.UserReport;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.user.service.UserService;
import com.chinamobile.hnu.xiangyu.util.ElasticSearchUtil;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * 问问-问题
 * 
 * @author lh <自动生成> @联系QQ：2465613689
 * @version 2018-06-05 16:00:38
 */
@RestController
@RequestMapping(value = "/api/ask/base")
public class AskInfoController {
	private final Logger logger = Logger.getLogger(AskInfoController.class);

	private String AI = "ai";

	@Autowired
	private IAskInfoService askInfoService;
	@Autowired
	private IAskCommentService commentService;
	@Autowired
	private IAskCommentReplyService commentReplyService;

	@Autowired
	private UploadDocumentUtil fileUtil;

	@Autowired
	private AuthTokenService tokenService;

	@Autowired
	private IAskVisitHistoryService visitService;

	@Autowired
	private UserService userService;

	@Resource
	HttpServletRequest request;

	@Resource
	HttpServletResponse response;

	@Autowired
	private PubPointService pointService;

	/**
	 * 发布问题
	 * 
	 * @param request
	 * @param info
	 * @return
	 */
	@PostMapping("save.do")
	public ResponseDto save(HttpServletRequest request, AskInfo info,
			@RequestParam(name = "labels", required = true) String labels) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			Map<String, Object> mapss = new HashMap<String, Object>();
			mapss.put("uid", user.getId());
			mapss.put("askName", info.getName());
			List<AskInfo> askList = askInfoService.selectByExample(mapss);
			if (askList.size() > 0) {
				return ResultUtil.result(2, "请勿发布重复的问问");
			}
			info.setPresentor(user.getId());
			List<AskPhoto> photos = null;
			List<AskLabel> listLabel = null;
			String[] lab = labels.split(",");
			if (lab.length > 0) {
				listLabel = new ArrayList<AskLabel>();
				for (String la : lab) {
					AskLabel al = new AskLabel();
					al.setName(la);
					listLabel.add(al);
				}
			}
			List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
			ResponseDto dto = fileUtil.savebatchFile(request, user.getId()
					.toString(), AI, new String[] { "jpg", "png" });
			if (dto.getCode() == 2) {
				return dto;
			} else if (dto.getCode() == 0) {
				photos = new ArrayList<>();
				for (String fileId : (List<String>) dto.getData()) {
					Map<String, Object> ph = new HashMap<String, Object>();
					AskPhoto ap = new AskPhoto();
					ap.setPhotoId(fileId);
					photos.add(ap);
					ph.put("photos",
							UploadDocumentUtil.buildPublicPath(ap.getPhotoId()));
					maps.add(ph);
				}
			}

			Long askId = askInfoService.insertAskInfo(info, photos, listLabel);

			// 增加经验值
			pointService.addPoint(user.getId(), PointRuleType.TYPE5.getValue(),
					askId.toString(), ExtidType.TYPE_2.getValue());
			Map<String, Object> map = new HashMap<>();
			map.put("id", askId);
			map.put("labels", labels);
			map.put("files", maps);
			return ResultUtil.result(0, "发布问问成功", map);
		} catch (Exception e) {
			logger.error("save askInfo is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 最新列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param type
	 * @param name
	 *            :查询问问名字 ======= : 1-按推荐查询；2-按时间查询；3-按热门查询 *
	 * 
	 * @return
	 */
	@PostMapping("pglist.do")
	public ResponseDto pglist(
			@RequestParam(name = "pn", required = true, defaultValue = "1") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "12") Integer pageSize,
			@RequestParam(name = "type", required = false) Integer type,
			@RequestParam(name = "name", required = false) String name) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Page<AskInfoVo> page = null;
		try {
			if (type != null) {
				if (type == 1) {
					List<UserLabel> labels = userService.selectUserLabel(user
							.getId());
					// 如果用户没有设置标签，给用户推荐热门问问
					if (labels.size() > 0) {
						map.put("type", type);
						map.put("labels", labels);
					} else {
						map.put("type", 3);
					}
				} else {
					map.put("type", type);
				}
			}

			if (StringUtils.isNotBlank(name)) {
				// 调用搜索引擎
				if (type == 1 || type == 2) {
					Map<String, String> params = new HashMap<>();
					params.put("name", name);
					params.put("content", name);
					Map<String, String> returnPropertys = new HashMap<>();
					returnPropertys.put("id", "{}");

					List<Long> idList = ElasticSearchUtil.getIdList("tag1",
							params, returnPropertys, ((pageCurrent <= 0 ? 1
									: pageCurrent) - 1) * pageSize, pageSize);
					if (idList != null && idList.size() > 0) {
						map.put("idList", idList);
					} else {
						return ResultUtil.result(0, "查询成功", new PageVo(
								new ArrayList<>(), 0L, 0));
					}
				} else {
					map.put("name", name);
				}
			}
			Integer userId = user.getId();
			page = askInfoService
					.selectPage(pageCurrent, pageSize, map, userId);
			userService.setFriendNickNameAsk(page.getData(), user.getId());
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("pglist pglist is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/****
	 * 通过标签查询
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param lable
	 * @return
	 */
	@PostMapping("labelList.do")
	public ResponseDto Labellist(
			@RequestParam(name = "pn", required = true, defaultValue = "1") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "12") Integer pageSize,
			@RequestParam String label) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("label", label);
		map.put("uid", user.getId());
		Page<AskInfoVo> page = null;
		try {

			page = askInfoService.selectLabelPage(pageCurrent, pageSize, map);
			userService.setFriendNickNameAsk(page.getData(), user.getId());
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("pglist pglist is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 5.1.2.分页查询指定专场的记录
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param special
	 *            :必填，专场的ID
	 * @return
	 */
	@PostMapping("pgspecial.do")
	public ResponseDto pgspecial(
			@RequestParam(name = "pn", required = true, defaultValue = "1") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "12") Integer pageSize,
			@RequestParam(name = "special", required = true) Integer special) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Page<AskInfoVo> page = null;
		try {
			map.put("special", special);
			page = askInfoService.pgspecial(pageCurrent, pageSize, map);
			userService.setFriendNickNameAsk(page.getData(), user.getId());
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("pglist pgspecial is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 查询问问详情
	 * 
	 * @param askid
	 * @return
	 */
	@PostMapping("askInfo.do")
	public ResponseDto askInfo(
			@RequestParam(name = "askid", required = true) Long askid) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("askid", askid);
			AskInfoVo infoVo = askInfoService.askInfo(map, user.getId());
			userService.setFriendNickNameAsk(infoVo, user.getId());
			// 更新总的浏览数
			if (user.getRoleId() == null && infoVo != null) {
				askInfoService.updateVisitAmount(askid);
			}
			if (infoVo == null) {
				return ResultUtil.result(2, "该问题已被删除");
			} else {
				return ResultUtil.result(0, "查询成功", infoVo);
			}
		} catch (Exception e) {
			logger.error("askInfo askInfo is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 5.1.3.分页查询问问的一级评论
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param askid
	 * @return
	 */
	@PostMapping("pgfirst.do")
	public ResponseDto pgfirst(
			@RequestParam(name = "pn", required = true, defaultValue = "1") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "12") Integer pageSize,
			@RequestParam(name = "askid", required = true) Long askid) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Page<AskCommentVo> page = null;
		try {
			map.put("askid", askid);
			page = askInfoService.pgfirst(pageCurrent, pageSize, map,
					user.getId(), askid);
			userService.setFriendNickNameAsk(page.getData(), user.getId());
			if (pageCurrent.equals(1)) {
				// 更新总的浏览数
				askInfoService.updateVisitAmount(askid);
				// 保存今天访问人员记录
				AskVisitHistory record = new AskVisitHistory();
				record.setAskId(askid);
				record.setMemberId(user.getId());
				record.setVisitTime(new Date());
				visitService.insertSelective(record);
			}
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("pglist pgfirst is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 查询一级评论详情
	 * 
	 * @param firstid
	 * @return
	 */
	@PostMapping("commentInfo.do")
	public ResponseDto pgfirst(
			@RequestParam(name = "firstid", required = true) Long firstid) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("id", firstid);
			AskCommentVo commentVo = askInfoService.selectCommentById(map);
			userService.setFriendNickNameAsk(commentVo, user.getId());
			return ResultUtil.result(0, "查询成功", commentVo);
		} catch (Exception e) {
			logger.error("askInfo commentInfo is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 查询二级评论
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param firstid
	 * @return
	 */
	@PostMapping("pgsecond.do")
	public ResponseDto pgsecond(
			@RequestParam(name = "pn", required = true, defaultValue = "1") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "12") Integer pageSize,
			@RequestParam(name = "firstid", required = true) Long firstid) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Page<AskCommentReplyVo> page = null;
		try {
			map.put("id", firstid);
			page = askInfoService.selectReplyByFirstId(pageCurrent, pageSize,
					map);
			userService.setFriendNickNameAsk(page.getData(), user.getId());
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("askInfo pgsecond is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 评论问题
	 * 
	 * @param id
	 * @param type
	 *            1:一级评论，2:二级评论
	 * @return
	 */
	@PostMapping("comment.do")
	public ResponseDto comment(
			@RequestParam(name = "id", required = true) Long id,
			@RequestParam(name = "type", required = true) Integer type,
			@RequestParam(name = "content", required = true) String content) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			Integer presentor = user.getId();
			Long ids = askInfoService.saveComment(content, id, type, presentor);
			if (type == 1) {
				AskInfo askInfo = askInfoService.selectByPrimaryKey(id);
				// 增加经验值
				pointService.addPoint(presentor,
						PointRuleType.TYPE6.getValue(), ids.toString(),
						ExtidType.TYPE_1.getValue());
				// 被评论者增加经验值
				pointService.addPoint(askInfo.getPresentor(),
						PointRuleType.TYPE7.getValue(), askInfo.getId()
								.toString(), ExtidType.TYPE_1.getValue());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", ids);
			return ResultUtil.result(0, "评论成功", map);
		} catch (Exception e) {
			logger.error("askInfo comment is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 收藏or取消
	 * 
	 * @param id
	 *            :被操作的问问的ID
	 * @param op
	 *            :操作方式。1-收藏问问；2-取消收藏问问
	 * @return
	 */
	@PostMapping("favorite.do")
	public ResponseDto favorite(
			@RequestParam(name = "id", required = true) Long id,
			@RequestParam(name = "op", required = true) Integer op) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			Integer presentor = user.getId();
			ResponseDto dto = askInfoService.favorite(id, op, presentor);
			if (op == 1) {
				AskInfo askInfo = askInfoService.selectByPrimaryKey(id);
				// 加经验值
				pointService.addPoint(user.getId(), PointRuleType.TYPE6
						.getValue(), dto.getData().toString(), ExtidType.TYPE_3
						.getValue());
				// 被收藏者增加经验值
				pointService.addPoint(askInfo.getPresentor(),
						PointRuleType.TYPE7.getValue(), askInfo.getId()
								.toString(), ExtidType.TYPE_3.getValue());
			}
			return ResultUtil.result(0, dto.getMsg());
		} catch (Exception e) {
			logger.error("askInfo favorite is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 举报问问
	 * 
	 * @param id
	 *            ：问问id
	 * @param type
	 *            :1-问问；2-社团
	 * @param content
	 *            ：举报内容
	 * @return
	 */
	@PostMapping("inform.do")
	public ResponseDto inform(
			@RequestParam(name = "id", required = true) Long id,
			@RequestParam(name = "type", required = true) int type,
			@RequestParam(name = "content", required = false) String content) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			Integer presentor = user.getId();
			ResponseDto dto = new ResponseDto();
			UserReport report = new UserReport();
			report.setBizId(id);
			report.setCategory((byte) type);
			report.setGmtCreate(new Date());
			report.setUserId(presentor);
			report.setContent(content);
			askInfoService.inform(id, presentor, report);
			return ResultUtil.result(0, dto.getMsg());
		} catch (Exception e) {
			logger.error("askInfo inform is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 
	 * @param id
	 * @param status
	 *            :1-点赞，2-取消点赞
	 * @return
	 */
	@PostMapping("likes.do")
	public ResponseDto likes(@RequestParam(name = "id", required = true) Long id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			Integer presentor = user.getId();
			ResponseDto dto = askInfoService.likes(presentor, id);

			if (dto.getData() != null) {
				AskInfo askInfo = askInfoService.selectByPrimaryKey(id);
				// 加经验值
				pointService.addPoint(user.getId(), PointRuleType.TYPE6
						.getValue(), dto.getData().toString(), ExtidType.TYPE_2
						.getValue());
				// 被点赞者增加经验值
				pointService.addPoint(askInfo.getPresentor(),
						PointRuleType.TYPE7.getValue(), askInfo.getId()
								.toString(), ExtidType.TYPE_2.getValue());
			}

			return ResultUtil.result(0, dto.getMsg());
		} catch (Exception e) {
			logger.error("askInfo likes is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 
	 * @param id
	 *            :当前评论id
	 * @param status
	 *            :1-顶，2-取消顶
	 * @return
	 */
	@PostMapping("top.do")
	public ResponseDto top(@RequestParam(name = "id", required = true) Long id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			Integer presentor = user.getId();
			ResponseDto dto = askInfoService.top(presentor, id);
			return ResultUtil.result(0, dto.getMsg());
		} catch (Exception e) {
			logger.error("askInfo likes is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 我的问题列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param type
	 *            : 1-我的问问,2-查别人的问问
	 * @return
	 */
	@PostMapping("myPglist.do")
	public ResponseDto myPglist(
			@RequestParam(name = "pn", required = true, defaultValue = "1") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "12") Integer pageSize,
			@RequestParam(name = "type", required = true) Integer type,
			@RequestParam(name = "uid", required = false) Integer uid) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Page<AskInfoVo> page = null;
		try {
			map.put("type", type);
			map.put("userId", user.getId());
			if (uid != null) {
				map.put("uid", uid);
			}
			page = askInfoService.selectMyListPage(pageCurrent, pageSize, map,
					user.getId());
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("pglist pglist is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 删除我发布的问题
	 * 
	 * @param uid
	 *            :问问发布人id
	 * @param askid
	 *            ：问问id
	 * @return
	 */
	@PostMapping("deleteMyAsk.do")
	public ResponseDto deleteMyAsk(
			@RequestParam(name = "uid", required = true) Integer uid,
			@RequestParam(name = "askid", required = true) Long askid) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		if (user.getId() != uid) {
			return ResultUtil.result(2, "您不是当前问问的发布人，请勿删除！");
		}
		try {
			askInfoService.deleteByPrimaryKey(askid);
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("deleteMyAsk  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 删除我评论的问题
	 * 
	 * @param uid
	 *            ：评论人的id
	 * @param type
	 *            ：1-一级评论，2-二级评论
	 * @param id
	 *            :评论id
	 * @return
	 */
	@PostMapping("deleteMyComment.do")
	public ResponseDto deleteMyComment(
			@RequestParam(name = "uid", required = true) Integer uid,
			@RequestParam(name = "type", required = true) Integer type,
			@RequestParam(name = "id", required = true) Long id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		if (user.getId() != uid) {
			return ResultUtil.result(2, "您不是当前评论人，请勿删除！");
		}
		try {
			if (type.equals(1)) {
				commentService.deleteCommentById(id);
			} else {
				commentReplyService.deleteByPrimaryKey(id);
			}
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("deleteMyAsk  is err:", e);
			return ResultUtil.serviceException();
		}
	}

}
