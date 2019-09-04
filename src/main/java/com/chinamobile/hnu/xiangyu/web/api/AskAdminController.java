/**  

 * <p>Title: AskAdminController.java</p>  

 * <p>Description: </p>  

 * <p>Copyright: Copyright douziit(c) 2017</p>  

 * @author lh  

 * @date 2018年6月21日  

 * @version 1.0  

 */
package com.chinamobile.hnu.xiangyu.web.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskSpecial;
import com.chinamobile.hnu.xiangyu.ask.service.IAskCommentReplyService;
import com.chinamobile.hnu.xiangyu.ask.service.IAskCommentService;
import com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService;
import com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService;
import com.chinamobile.hnu.xiangyu.ask.service.IAskVisitHistoryService;
import com.chinamobile.hnu.xiangyu.ask.vo.AskCommentVo;
import com.chinamobile.hnu.xiangyu.ask.vo.AskInfoVo;
import com.chinamobile.hnu.xiangyu.auth.service.IAuthResourcesService;
import com.chinamobile.hnu.xiangyu.club.service.ClubBaseService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubVo;
import com.chinamobile.hnu.xiangyu.common.pojo.PubAdvertisement;
import com.chinamobile.hnu.xiangyu.common.pojo.PubConstPlane;
import com.chinamobile.hnu.xiangyu.common.service.IPubFeebackService;
import com.chinamobile.hnu.xiangyu.common.service.PubConstPlaneService;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.user.service.UserAccountService;
import com.chinamobile.hnu.xiangyu.user.service.UserService;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;
import com.chinamobile.hnu.xiangyu.web.api.vo.AaskInfoVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.AskComentVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.AskSpecialVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.ClubSpeackVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.FeebackVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.UserCertificationInfoVo;

/**
 * 
 * <p>
 * Title: AskAdminController.java
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author lh
 * 
 * @date 2018年6月21日
 * 
 * @version 1.0
 */
@RestController
@RequestMapping("/api/admin/ask/")
public class AskAdminController {

	private final Logger logger = Logger.getLogger(AskAdminController.class);

	@Autowired
	private AuthTokenService tokenService;

	@Value("${constant.as}")
	private String AS;

	@Autowired
	private ClubBaseService baseService;

	@Autowired
	private IAskInfoService askInfoService;

	@Autowired
	private UploadDocumentUtil fileUtil;

	@Autowired
	private IAskVisitHistoryService visitService;

	@Resource
	private IAskSpecialService askSpecialService;

	@Resource
	private IAskCommentService commentService;

	@Resource
	private UserService userService;

	@Resource
	private UserAccountService accountService;

	@Resource
	private IAskInfoService infoService;

	@Resource
	private PubConstPlaneService constPlaneService;

	@Resource
	private IAskCommentReplyService commentReplyService;

	@Autowired
	private IAuthResourcesService resourcesService;

	@Autowired
	private IPubFeebackService feebackService;

	/**
	 * 专题列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param name
	 *            :名称（查询）
	 * @param startTime
	 *            ：开始时间（查询）
	 * @param endTime
	 *            :结束时间（查询）
	 * @return
	 */
	@PostMapping("ztSpeakList.do")
	public ResponseDto ztSpeakList(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
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
			if (!listMaps.contains("/special")) {
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
		try {
			Page<ClubSpeackVo> page = askSpecialService.selectAskSpecialByList(
					map, pageCurrent, pageSize, 2);
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
	 * 创建or修改问答专题
	 * 
	 * @param request
	 * @param info
	 * @return
	 */
	@PostMapping("saveSpecial.do")
	public ResponseDto saveSpecial(HttpServletRequest request,
			@RequestBody AskSpecial record) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		if (StringUtils.isBlank(record.getTitle())) {
			return ResultUtil.result(3, "标题不能为空");
		}
		if (StringUtils.isBlank(record.getPresentorName())) {
			return ResultUtil.result(3, "发布者不能空");
		}
		if (StringUtils.isBlank(record.getPresentorJob())) {
			return ResultUtil.result(3, "职位介绍不能为空客");
		}
		try {
			if (record.getId() != null) {
				ResponseDto dto = fileUtil.saveFile(request, user.getId()
						.toString(), AS, new String[] { "jpg", "png" });
				if (dto.getCode() == 2) {
					return dto;
				} else if (dto.getCode() == 0) {
					String fileId = (String) dto.getData();
					record.setCoverImage(fileId);
				}
				record.setCategory((byte) 1);
				record.setGmtModified(new Date());
				record.setPresentor(user.getId());
				askSpecialService.updateByPrimaryKeySelective(record);
				return ResultUtil.result(0, "修改专题成功");
			} else {
				ResponseDto dto = fileUtil.saveFile(request, user.getId()
						.toString(), AS, new String[] { "jpg", "png" });
				if (dto.getCode() == 2) {
					return dto;
				} else if (dto.getCode() == 0) {
					String fileId = (String) dto.getData();
					record.setCoverImage(fileId);
				}
				record.setCategory((byte) 1);
				record.setGmtCreate(new Date());
				record.setPresentor(user.getId());
				askSpecialService.insertSelective(record);
				return ResultUtil.result(0, "创建专题成功");
			}
		} catch (Exception e) {
			logger.error("saveSpecial is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 专题查看
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("specialInfo.do")
	public ResponseDto specialInfo(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			AskSpecialVo vo = askSpecialService.selectSpecialInfoById(id);
			return ResultUtil.result(0, "查询成功", vo);
		} catch (Exception e) {
			logger.error("specialInfo is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 专场评论分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param id
	 *            : 专场id
	 * @return
	 */
	@PostMapping("pgHotReview.do")
	public ResponseDto pgHotReview(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Page<AskInfoVo> page = null;
		try {
			page = askSpecialService.selectPgHotReview(pageCurrent, pageSize,
					id, user.getId());
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("pgHotReview  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 删除专场评论
	 * 
	 * @param id
	 *            :评论id
	 * @return
	 */
	@PostMapping("deleteComment.do")
	public ResponseDto deleteComment(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			int i = askInfoService.deleteByPrimaryKey(Long.valueOf(id));
			if (i > 0) {
				return ResultUtil.result(0, "删除成功");
			} else {
				return ResultUtil.result(2, "删除失败");
			}
		} catch (Exception e) {
			logger.error("deleteComment is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 保专题回答评论
	 * 
	 * @param id
	 *            ：专场id
	 * @param type
	 *            ： 1:一级评论，2:二级评论(后台管理type传2)
	 * @param content
	 * @param anonymity
	 *            :匿名发表的标记。0-不匿名；1-匿名
	 * @return
	 */
	@PostMapping("HotReviewComment.do")
	public ResponseDto HotReviewComment(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		Integer askid = (Integer) maps.get("askid");
		Integer type = (Integer) maps.get("type");
		Integer anonymity = (Integer) maps.get("anonymity");
		String content = (String) maps.get("content");
		int anonymitys = 0;
		if (anonymity != null) {
			anonymitys = anonymity;
		}
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			Map<String, Object> map = askSpecialService.saveComment(
					(byte) anonymitys, content, Long.valueOf(askid), id, type,
					user.getId());
			return ResultUtil.result(0, "评论成功", map);
		} catch (Exception e) {
			logger.error("HotReviewComment insertComment is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 删除专场二级评论
	 * 
	 * @param id
	 *            :二级评论id
	 * @return
	 */
	@PostMapping("deleteTwoComment.do")
	public ResponseDto deleteTwoComment(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			commentService.deleteByPrimaryKey(Long.valueOf(id));
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("deleteComment is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 修改专题信息获取详情
	 * 
	 * @param id
	 *            :专题id
	 * @return
	 */
	@PostMapping("upSpecialInfo.do")
	public ResponseDto upSpecialInfo(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			AskSpecial ak = askSpecialService.selectByPrimaryKey(id);
			ak.setCoverImage(UploadDocumentUtil.buildPublicPath(ak
					.getCoverImage()));
			return ResultUtil.result(0, "查询成功", ak);
		} catch (Exception e) {
			logger.error("upSpecialInfo is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 批量删除专场
	 * 
	 * @param id
	 *            :专场id
	 * @return
	 */
	@PostMapping("deleteBatchSpecial.do")
	public ResponseDto deleteBatchSpecial(@RequestBody List<Integer> ids) {

		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			List<Integer> idList = new ArrayList<Integer>();
			// String ids[] = id.split(",");
			for (Integer sid : ids) {
				idList.add(sid);
			}
			askSpecialService.deleteBatchSpecial(idList);
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("deleteComment is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 删除专场
	 * 
	 * @param id
	 *            :专场id
	 * @return
	 */
	@PostMapping("DeleteSpecial.do")
	public ResponseDto DeleteSpecial(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			askSpecialService.deleteByPrimaryKey(id);
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("deleteComment is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 文章列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param name
	 *            :名称（查询）
	 * @param startTime
	 *            ：开始时间（查询）
	 * @param endTime
	 *            :结束时间（查询）
	 * @return
	 */
	@PostMapping("wzSpeakList.do")
	public ResponseDto wzSpeakList(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
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
			if (!listMaps.contains("/article")) {
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
		try {
			Page<ClubSpeackVo> page = askSpecialService.selectAskSpecialByList(
					map, pageCurrent, pageSize, 1);
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
	 * 创建or修改文章
	 * 
	 * @param request
	 * @param info
	 * @return
	 */
	@PostMapping("saveWzSpecial.do")
	public ResponseDto saveWzSpecial(HttpServletRequest request,
			@RequestBody AskSpecial record) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		if (StringUtils.isBlank(record.getTitle())) {
			return ResultUtil.result(3, "标题不能为空");
		}
		if (StringUtils.isBlank(record.getPresentorName())) {
			return ResultUtil.result(3, "发布者不能空");
		}
		if (StringUtils.isBlank(record.getPresentorJob())) {
			return ResultUtil.result(3, "职位介绍不能为空客");
		}
		try {
			if (record.getId() != null) {
				ResponseDto dto = fileUtil.saveFile(request, user.getId()
						.toString(), AS, new String[] { "jpg", "png" });
				if (dto.getCode() == 2) {
					return dto;
				} else if (dto.getCode() == 0) {
					String fileId = (String) dto.getData();
					record.setCoverImage(fileId);
				}
				record.setCategory((byte) 2);
				record.setGmtModified(new Date());
				record.setPresentor(user.getId());
				askSpecialService.updateByPrimaryKeySelective(record);
				return ResultUtil.result(0, "修改文章成功");
			} else {
				ResponseDto dto = fileUtil.saveFile(request, user.getId()
						.toString(), AS, new String[] { "jpg", "png" });
				if (dto.getCode() == 2) {
					return dto;
				} else if (dto.getCode() == 0) {
					String fileId = (String) dto.getData();
					record.setCoverImage(fileId);
				}
				record.setCategory((byte) 2);
				record.setGmtCreate(new Date());
				record.setPresentor(user.getId());
				askSpecialService.insertSelective(record);
				return ResultUtil.result(0, "创建文章成功");
			}
		} catch (Exception e) {
			logger.error("saveSpecial is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/***
	 * 专场 文章评论分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param id
	 *            :专场id
	 * @return
	 */
	@PostMapping("pgComment.do")
	public ResponseDto pgComment(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Page<AskInfoVo> page = null;
		try {
			page = askSpecialService.selectPgComment(pageCurrent, pageSize, id);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("pgComment  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	// 文章导入
	// TODO

	// 文章导出
	// TODO

	/**
	 * 举报列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@PostMapping("reportList.do")
	public ResponseDto reportList(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		String name = (String) maps.get("name");
		String startTime = (String) maps.get("startTime");
		String endTime = (String) maps.get("endTime");
		String type = (String) maps.get("type");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// 判断是否有权限
		List<String> listMaps = resourcesService.selectUserResourceLists(user
				.getRoleId());
		if (listMaps.size() > 0) {
			if (!listMaps.contains("/report")) {
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
		if (StringUtils.isBlank(type)) {
			map.put("type", "1");
		} else {
			map.put("type", type);
		}
		Page<ClubSpeackVo> page = null;
		try {
			page = askSpecialService.selectreportList(pageCurrent, pageSize,
					map);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("pgComment  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 删除被举报的内容
	 * 
	 * @param askId
	 * @return
	 */
	@PostMapping("deleteReportAskInfo.do")
	public ResponseDto deleteReportAskInfo(@RequestBody Map<String, Object> maps) {
		Integer askId = (Integer) maps.get("askId");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			askSpecialService.deleteReportAskInfo(Long.valueOf(askId));
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("DeletereportAskInfo  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 举报详情
	 * 
	 * @param askId
	 * @return
	 */
	@PostMapping("reportAskInfo.do")
	public ResponseDto reportAskInfo(@RequestBody Map<String, Object> maps) {
		Integer askId = (Integer) maps.get("askId");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			AaskInfoVo vo = askSpecialService.selectReportAskInfo(Long
					.valueOf(askId));
			return ResultUtil.result(0, "查询成功", vo);
		} catch (Exception e) {
			logger.error("reportAskInfo  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 详情评论分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param askId
	 * @return
	 */
	@PostMapping("reportAskComment.do")
	public ResponseDto reportAskComment(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		Integer askId = (Integer) maps.get("askId");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			Page<AskCommentVo> page = askInfoService.commentfirst(pageCurrent,
					pageSize, Long.valueOf(askId));
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("reportAskInfo  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 删除举报问问的评论
	 * 
	 * @param id
	 *            :一级评论id
	 * @return
	 */
	@PostMapping("deleteAskComment.do")
	public ResponseDto deleteAskComment(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			commentService.deleteCommentById(Long.valueOf(id));
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("deleteAskComment  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 问题列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@PostMapping("askInfoList.do")
	public ResponseDto askInfoList(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
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
			if (!listMaps.contains("/answer")) {
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
		Page<ClubSpeackVo> page = null;
		try {
			page = askInfoService.selectAskInfoList(pageCurrent, pageSize, map);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("askInfoList  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 问题详情
	 * 
	 * @param askId
	 * @return
	 */
	@PostMapping("askInfo.do")
	public ResponseDto askInfo(@RequestBody Map<String, Object> maps) {
		Integer askId = (Integer) maps.get("askId");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			AaskInfoVo vo = askSpecialService.selectReportAskInfo(Long
					.valueOf(askId));
			return ResultUtil.result(0, "查询成功", vo);
		} catch (Exception e) {
			logger.error("askInfo  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 问题评论分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param askId
	 * @return
	 */
	@PostMapping("askComment.do")
	public ResponseDto askComment(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		Integer askId = (Integer) maps.get("askId");

		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			Page<AskCommentVo> page = askInfoService.commentfirst(pageCurrent,
					pageSize, Long.valueOf(askId));
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("askComment  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 会员列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param status
	 * @param college
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@PostMapping("userCertificationList.do")
	public ResponseDto userCertificationList(
			@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// 判断是否有权限
		List<String> listMaps = resourcesService.selectUserResourceLists(user
				.getRoleId());
		if (listMaps.size() > 0) {
			if (!listMaps.contains("/member")) {
				return ResultUtil.result(5, "暂无权限!");
			}
		} else {
			return ResultUtil.result(5, "暂无权限!");
		}
		try {
			Page<UserAccount> page = userService.seleUserList(pageCurrent,
					pageSize, maps);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("userCertificationList  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 会员查看
	 * 
	 * @param userId
	 * @return
	 */
	@PostMapping("userCertificationInfo.do")
	public ResponseDto userCertificationInfo(
			@RequestBody Map<String, Object> maps) {
		Integer userId = (Integer) maps.get("userId");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			UserCertificationInfoVo uc = askInfoService
					.selectUserCertificationInfo(userId);
			if (uc != null) {
				return ResultUtil.result(0, "查询成功", uc);
			} else {
				return ResultUtil.result(2, "暂无数据");
			}
		} catch (Exception e) {
			logger.error("userCertificationInfo  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 会员启用or禁用
	 * 
	 * @param userId
	 * @param type
	 *            :1-封号；0-启用
	 * @return
	 */
	@PostMapping("userEnable.do")
	public ResponseDto userEnable(@RequestBody Map<String, Object> maps) {
		Integer userId = (Integer) maps.get("userId");
		Integer type = (Integer) maps.get("type");
		if (userId == null) {
			return ResultUtil.result(0, "用户id不能为空");
		}
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			if (type.equals(1)) {
				accountService.updateEanbled(userId, false);
				// 禁用用户，清除token
				tokenService.deleteByUid(user.getId());
				return ResultUtil.result(0, "封号成功");
			} else {
				accountService.updateEanbled(userId, true);
				return ResultUtil.result(0, "启用成功");
			}
		} catch (Exception e) {
			logger.error("userEnable  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 通过or驳回
	 * 
	 * @param userId
	 * @param type
	 *            :1-驳回；2-通过
	 * @return
	 */
	@PostMapping("userStatus.do")
	public ResponseDto userStatus(@RequestBody Map<String, Object> maps) {
		Integer userId = (Integer) maps.get("userId");
		Integer type = (Integer) maps.get("type");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			int i = accountService.updateUserStatus(userId, type);
			if (i > 0) {
				int status = type;
				return ResultUtil.result(0, "操作成功", status);
			} else {
				return ResultUtil.result(0, "操作失败");
			}
		} catch (Exception e) {
			logger.error("userEnable  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 广告列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @return
	 */
	@PostMapping("advertisementList.do")
	public ResponseDto advertisementList(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// 判断是否有权限
		List<String> listMaps = resourcesService.selectUserResourceLists(user
				.getRoleId());
		if (listMaps.size() > 0) {
			if (!listMaps.contains("/ad")) {
				return ResultUtil.result(5, "暂无权限!");
			}
		} else {
			return ResultUtil.result(5, "暂无权限!");
		}
		Page<PubAdvertisement> page;
		try {
			page = constPlaneService.selectAdvAll(pageCurrent, pageSize);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("advertisementList  is err:", e);
			return ResultUtil.serviceException();
		}

	}

	/**
	 * 添加or编辑广告
	 * 
	 * @param request
	 * @param adv
	 * @return
	 */
	@PostMapping("saveAdvertisement.do")
	public ResponseDto saveAdvertisement(HttpServletRequest request,
			@RequestBody PubAdvertisement adv) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		if (StringUtils.isBlank(adv.getLinkUrl())) {
			return ResultUtil.result(3, "广告路径不能为空");
		}
		// ResponseDto dto;
		// try {
		// dto = fileUtil.saveFile(request, user.getId().toString(), AS,
		// new String[] { "jpg", "png" });
		// if (dto.getCode() == 2) {
		// return dto;
		// } else if (dto.getCode() == 0) {
		// String fileId = (String) dto.getData();
		// adv.setCoverImage(fileId);
		// }
		// } catch (Exception e1) {
		// logger.error("upload  CoverImage is err:", e1);
		// return ResultUtil.serviceException();
		// }
		try {
			if (adv.getId() != null) {
				constPlaneService.updateByPrimaryKeySelective(adv);
				return ResultUtil.result(0, "更新成功");
			} else {
				constPlaneService.insertSelective(adv);
				return ResultUtil.result(0, "创建成功");
			}
		} catch (Exception e) {
			logger.error("saveAdvertisement  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 禁用项列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param name
	 * @return
	 */
	@PostMapping("pubConstDisableList.do")
	public ResponseDto pubConstDisableList(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		String name = (String) maps.get("name");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// 判断是否有权限
		List<String> listMaps = resourcesService.selectUserResourceLists(user
				.getRoleId());
		if (listMaps.size() > 0) {
			if (!listMaps.contains("/disable")) {
				return ResultUtil.result(5, "暂无权限!");
			}
		} else {
			return ResultUtil.result(5, "暂无权限!");
		}
		Page<PubConstPlane> page;
		try {
			page = constPlaneService.selectPlanesByList(pageCurrent, pageSize,
					"disable", name);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("pubConstDisableList  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 添加禁用项
	 * 
	 * @param record
	 * @return
	 */
	@PostMapping("saveConstDisable.do")
	public ResponseDto saveConstDisable(@RequestBody PubConstPlane record) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		if (StringUtils.isBlank(record.getValue())) {
			return ResultUtil.result(3, "禁用项名称不能为空");
		}
		try {
			List<PubConstPlane> pcp = constPlaneService
					.selectPlanesByType("disable");
			if (pcp.size() > 0) {
				for (PubConstPlane pubConstPlane : pcp) {
					if (pubConstPlane.getValue().equals(record.getValue())) {
						return ResultUtil.result(3, "禁用项名称不能重复!");
					}
				}
			}
			record.setCategory("disable");
			constPlaneService.insertSelective(record);
			return ResultUtil.result(0, "添加成功");
		} catch (Exception e) {
			logger.error("saveConstDisable  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 添加学校名称
	 * 
	 * @param record
	 * @return
	 */
	@PostMapping("/saveConstSchool.do")
	public ResponseDto saveConstSchool(@RequestBody PubConstPlane record) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		if (StringUtils.isBlank(record.getValue())) {
			return ResultUtil.result(3, "学校名称不能为空");
		}
		try {
			List<PubConstPlane> pcp = constPlaneService
					.selectPlanesByType("school");
			if (pcp.size() > 0) {
				for (PubConstPlane pubConstPlane : pcp) {
					if (pubConstPlane.getValue().equals(record.getValue())) {
						return ResultUtil.result(3, "学校名称不能重复!");
					}
				}
			}
			if (record.getId() != null) {
				record.setCategory("school");
				constPlaneService.updateByPrimaryKeySelective(record);
				return ResultUtil.result(0, "修改成功");
			} else {
				record.setCategory("school");
				constPlaneService.insertSelective(record);
				return ResultUtil.result(0, "添加成功");
			}
		} catch (Exception e) {
			logger.error("saveConstDisable  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 学校列表
	 * 
	 * @return
	 */
	@PostMapping("/constSchoolList.do")
	public ResponseDto constSchoolList() {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			List<PubConstPlane> consts = constPlaneService
					.selectPlanesByType("school");
			return ResultUtil.result(0, "查询成功", consts);
		} catch (Exception e) {
			logger.error("saveConstDisable  is err:", e);
			return ResultUtil.serviceException();
		}

	}

	/**
	 * 学校详情
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/constSchoolInfo/{id}.do")
	public ResponseDto constSchoolInfo(@PathVariable("id") Integer id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			PubConstPlane consts = constPlaneService.selectByPrimaryKey(id);
			return ResultUtil.result(0, "查询成功", consts);
		} catch (Exception e) {
			logger.error("saveConstDisable  is err:", e);
			return ResultUtil.serviceException();
		}

	}

	/**
	 * 学校列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param name
	 * @return
	 */
	@PostMapping("pubConstSchoolList.do")
	public ResponseDto pubConstSchoolList(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		String name = (String) maps.get("name");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// 判断是否有权限
		List<String> listMaps = resourcesService.selectUserResourceLists(user
				.getRoleId());
		if (listMaps.size() > 0) {
			if (!listMaps.contains("/school")) {
				return ResultUtil.result(5, "暂无权限!");
			}
		} else {
			return ResultUtil.result(5, "暂无权限!");
		}
		Page<PubConstPlane> page;
		try {
			page = constPlaneService.selectPlanesByList(pageCurrent, pageSize,
					"school", name);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("pubConstDisableList  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 批量删除禁用项
	 * 
	 * @param ids
	 * @return
	 */
	@PostMapping("deleteBatchConstDisable.do")
	public ResponseDto deleteBatchConstDisable(@RequestBody List<Integer> ids) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// String[] id = ids.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for (Integer idL : ids) {
			idList.add(idL);
		}
		try {
			constPlaneService.deleteBatchDisableById(idList);
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("deleteConstDisable  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 删除禁用项或标签
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("deleteConstDisable.do")
	public ResponseDto deleteConstDisable(@RequestBody Map<String, Object> maps) {
		Integer id = (Integer) maps.get("id");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			constPlaneService.deleteByPrimaryKey(id);
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("deleteConstDisable  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	// 禁用项批量导入
	// TODO

	/**
	 * 标签列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param name
	 * @return
	 */
	@PostMapping("pubConstLabelList.do")
	public ResponseDto pubConstLabelList(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		String name = (String) maps.get("name");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// 判断是否有权限
		List<String> listMaps = resourcesService.selectUserResourceLists(user
				.getRoleId());
		if (listMaps.size() > 0) {
			if (!listMaps.contains("/tag")) {
				return ResultUtil.result(5, "暂无权限!");
			}
		} else {
			return ResultUtil.result(5, "暂无权限!");
		}
		Page<PubConstPlane> page;
		try {
			page = constPlaneService.selectPlanesByList(pageCurrent, pageSize,
					"label", name);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("pubConstDisableList  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 添加标签
	 * 
	 * @param record
	 * @return
	 */
	@PostMapping("saveConstLabel.do")
	public ResponseDto saveConstLabel(@RequestBody PubConstPlane record) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		if (StringUtils.isBlank(record.getValue())) {
			return ResultUtil.result(3, "禁用项名称不能为空");
		}
		try {
			record.setCategory("label");
			constPlaneService.insertSelective(record);
			return ResultUtil.result(0, "添加成功");
		} catch (Exception e) {
			logger.error("saveConstDisable  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 上传图片
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping("uploadFile.do")
	public ResponseDto uploadFile(HttpServletRequest request) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			ResponseDto dto = fileUtil.saveFile(request, user.getId()
					.toString(), AS, new String[] { "jpg", "png" });
			if (dto.getCode() == 2) {
				return dto;
			} else if (dto.getCode() == 0) {
				String fileId = (String) dto.getData();
				return ResultUtil.result(0, "上传成功", fileId);
			} else {
				return ResultUtil.result(1, "上传失败");
			}
		} catch (Exception e) {
			logger.error("upload img is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/****
	 * 查询当前用户信息
	 * 
	 * @return
	 */
	@PostMapping("info.do")
	public ResponseDto info(@RequestBody Map<String, Object> maps) {
		Integer uid = (Integer) maps.get("uid");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			if (uid != null) {
				user = accountService.selectByPrimaryKey(uid);
				if (user == null) {
					ResultUtil.result(0, "没有用户信息");
				}
			}
			UserAccount userInfo = new UserAccount();
			if (StringUtils.isNotBlank(user.getHeader())) {
				userInfo.setHeader(UploadDocumentUtil.buildPublicPath(user
						.getHeader()));
			}
			userInfo.setId(user.getId());
			userInfo.setNickname(user.getNickname());
			userInfo.setGrade(user.getGrade());
			userInfo.setExperience(user.getExperience());
			userInfo.setSex(user.getSex());
			userInfo.setCertFlag(user.getCertFlag());
			userInfo.setSettingClub(user.getSettingClub());
			userInfo.setSettingAsk(user.getSettingAsk());
			userInfo.setCollege(user.getCollege());

			return ResultUtil.result(0, "查询成功", userInfo);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("select UserInfo Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/****
	 * 分页查询团队列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param type
	 * @return
	 */
	@PostMapping("userClublist.do")
	public ResponseDto userClublist(@RequestBody Map<String, Object> maps) {
		Integer uid = (Integer) maps.get("uid");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (uid != null) {
			map.put("type", 3);
			map.put("uid", uid);
			map.put("my", 1);
		}
		Page<ClubVo> page = null;
		try {
			page = baseService.selectByPage(0, 0, map);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("save clubInfo page Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 查询问问详情
	 * 
	 * @param askid
	 * @return
	 */
	@PostMapping("askInfos.do")
	public ResponseDto askInfos(@RequestBody Map<String, Object> maps) {
		Integer askid = (Integer) maps.get("askid");
		if (askid == null) {
			return ResultUtil.result(1, "问问id不能为空");
		}
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("askid", askid);
			AskInfoVo infoVo = askInfoService.askInfo(map, user.getId());
			// 更新总的浏览数
			if (user.getRoleId() != null) {
				askInfoService.updateVisitAmount(Long.valueOf(askid));
			}
			return ResultUtil.result(0, "查询成功", infoVo);
		} catch (Exception e) {
			logger.error("askInfo askInfo is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 专家列表
	 * 
	 * @return
	 */
	@PostMapping("/expertList.do")
	public ResponseDto expertList() {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			List<UserAccount> ua = accountService.selectExpertList();
			return ResultUtil.result(0, "查询成功", ua);
		} catch (Exception e) {
			logger.error("askInfo askInfo is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 问问评论
	 * 
	 * @param maps
	 * @return
	 */
	@PostMapping("/queryAskComent.do")
	public ResponseDto queryAskComent(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		Integer askId = (Integer) maps.get("askid");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		if (askId == null) {
			return ResultUtil.result(1, "参数不全");
		}
		try {
			Page<AskComentVo> page = commentService.selectAskComentByAskId(
					pageCurrent, pageSize, askId);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("askInfo askInfo is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 删除问问二级评论
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/deleteSecondAskComment/{id}.do")
	public ResponseDto deleteSecondAskComment(@PathVariable("id") Long id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			commentReplyService.deleteByPrimaryKey(id);
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("deleteSecondAskComment  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 反馈列表
	 * 
	 * @param maps
	 * @return
	 */
	@PostMapping("/queryFeeback.do")
	public ResponseDto queryFeeback(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// 判断是否有权限
		List<String> listMaps = resourcesService.selectUserResourceLists(user
				.getRoleId());
		if (listMaps.size() > 0) {
			if (!listMaps.contains("/feeback")) {
				return ResultUtil.result(5, "暂无权限!");
			}
		} else {
			return ResultUtil.result(5, "暂无权限!");
		}
		try {
			Page<FeebackVo> page = feebackService.selectListPageFeeback(
					pageCurrent, pageSize, maps);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("selectListPageFeeback  is err:", e);
			return ResultUtil.serviceException();
		}

	}

	/**
	 * 删除反馈
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/deleteFeeback/{id}.do")
	public ResponseDto deleteFeeback(@PathVariable("id") Integer id) {
		try {
			feebackService.deleteById(id);
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("deleteFeeback  is err:", e);
			return ResultUtil.serviceException();
		}
	}
}
