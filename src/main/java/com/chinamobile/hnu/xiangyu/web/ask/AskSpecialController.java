package com.chinamobile.hnu.xiangyu.web.ask;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskInfo;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskSpecial;
import com.chinamobile.hnu.xiangyu.ask.service.IAskCommentService;
import com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService;
import com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService;
import com.chinamobile.hnu.xiangyu.ask.vo.AskInfoVo;
import com.chinamobile.hnu.xiangyu.common.enums.ExtidType;
import com.chinamobile.hnu.xiangyu.common.enums.PointRuleType;
import com.chinamobile.hnu.xiangyu.common.service.PubPointService;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.user.service.UserService;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * 问问-专场
 * 
 * @author lh <自动生成> @联系QQ：2465613689
 * @version 2018-06-05 16:00:38
 */
@RestController
@RequestMapping(value = "/api/ask/special/")
public class AskSpecialController {
	private final Logger logger = Logger.getLogger(AskSpecialController.class);

	@Resource
	private IAskSpecialService askSpecialService;

	@Resource
	private IAskCommentService commentService;

	@Resource
	private IAskInfoService infoService;

	@Autowired
	private AuthTokenService tokenService;

	@Resource
	HttpServletRequest request;

	@Resource
	HttpServletResponse response;

	@Autowired
	private PubPointService pointService;
	
	@Autowired
	private UserService userService;

	/**
	 * 分页查询专场/文章列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param type
	 * @return
	 */
	@PostMapping("pglist.do")
	public ResponseDto pglist(@RequestParam(name = "pn", required = true, defaultValue = "1") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "12") Integer pageSize,
			@RequestParam(name = "type", required = false) Integer type) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Page<AskSpecial> page = null;
		try {
			if (type == null) {
				type = 1;
			}
			page = askSpecialService.selectPage(pageCurrent, pageSize, type, user.getId());
			userService.setFriendNickNameAsk(page.getData(), user.getId());
			return ResultUtil.result(0, "查询成功", new PageVo(page.getData(), page.getTotalCount(), page.getTotalPage()));
		} catch (Exception e) {
			logger.error("pglist pglist is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 删除专场评论
	 * 
	 * @param id
	 *            :二级评论id
	 * @return
	 */
	@PostMapping("deleteComment.do")
	public ResponseDto deleteComment(@RequestParam(name = "id", required = true) Long id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			commentService.deleteByPrimaryKey(id);
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("deleteComment is err:", e);
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
	public ResponseDto pgComment(@RequestParam(name = "pn", required = true, defaultValue = "1") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "12") Integer pageSize,
			@RequestParam(name = "id", required = true) Integer id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Page<AskInfoVo> page = null;
		try {
			page = askSpecialService.selectPgComment(pageCurrent, pageSize, id);
			userService.setFriendNickNameAsk(page.getData(), user.getId());
			return ResultUtil.result(0, "查询成功", new PageVo(page.getData(), page.getTotalCount(), page.getTotalPage()));
		} catch (Exception e) {
			logger.error("pgComment  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 专场热评分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param id
	 *            : 专场id
	 * @return
	 */
	@PostMapping("pgHotReview.do")
	public ResponseDto pgHotReview(@RequestParam(name = "pn", required = true, defaultValue = "1") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "12") Integer pageSize,
			@RequestParam(name = "id", required = true) Integer id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Page<AskInfoVo> page = null;
		try {
			page = askSpecialService.selectPgHotReview(pageCurrent, pageSize, id, user.getId());
			userService.setFriendNickNameAsk(page.getData(), user.getId());
			return ResultUtil.result(0, "查询成功", new PageVo(page.getData(), page.getTotalCount(), page.getTotalPage()));
		} catch (Exception e) {
			logger.error("pgHotReview  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 保存热答专题专场评论
	 * 
	 * @param id
	 *            ：专场id
	 * @param type
	 *            ： 1:一级评论，2:二级评论
	 * @param content
	 * @param anonymity
	 *            :匿名发表的标记。0-不匿名；1-匿名
	 * @return
	 */
	@PostMapping("HotReviewComment.do")
	public ResponseDto HotReviewComment(@RequestParam(name = "id", required = true) Integer id,
			@RequestParam(name = "askid", required = false) Long askid,
			@RequestParam(name = "type", required = true) Integer type,
			@RequestParam(name = "anonymity", required = false) Byte anonymity,
			@RequestParam(name = "content", required = true) String content) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			Map<String, Object> map = askSpecialService.saveComment(anonymity, content, askid, id, type, user.getId());
			if (type == 1) {
				AskSpecial askSpecial = askSpecialService.selectByPrimaryKey(id);
				// 增加经验值
				pointService.addPoint(user.getId(), PointRuleType.TYPE8.getValue(), map.get("id").toString(),
						ExtidType.TYPE1_.getValue());
				// 被评论者增加经验值
				pointService.addPoint(askSpecial.getPresentor(), PointRuleType.TYPE8.getValue(),
						askSpecial.getId().toString(), ExtidType.TYPE1_.getValue());
			}

			return ResultUtil.result(0, "评论成功", map);
		} catch (Exception e) {
			logger.error("HotReviewComment insertComment is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 保存文章专场评论(只有一级评论)
	 * 
	 * @param id
	 *            ：专场id
	 * @param type
	 * @param content
	 *            * @param anonymity :匿名发表的标记。0-不匿名；1-匿名
	 * @return
	 */
	@PostMapping("comment.do")
	public ResponseDto HotReviewComment(@RequestParam(name = "anonymity", required = false) Byte anonymity,
			@RequestParam(name = "id", required = true) Integer id,
			@RequestParam(name = "content", required = true) String content) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			if (content.length() > 32) {
				return ResultUtil.result(3, "评论内容超过32个字");
			}
			AskInfo record = new AskInfo();
			record.setGmtCreate(new Date());
			record.setName(content);
			record.setPresentor(user.getId());
			record.setSpecialId(id);
			if (anonymity != null) {
				record.setAnonymity(anonymity);
			}
			infoService.insertSelective(record);

			AskSpecial askSpecial = askSpecialService.selectByPrimaryKey(id);
			// 增加经验值
			pointService.addPoint(user.getId(), PointRuleType.TYPE8.getValue(), record.getId().toString(),
					ExtidType.TYPE1_.getValue());
			// 被评论者增加经验值
			pointService.addPoint(askSpecial.getPresentor(), PointRuleType.TYPE8.getValue(),
					askSpecial.getId().toString(), ExtidType.TYPE1_.getValue());

			return ResultUtil.result(0, "评论成功");
		} catch (Exception e) {
			logger.error("HotReviewComment insertComment is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 
	 * @param id
	 *            ：文章专题id
	 * @param status
	 *            :1-点赞，2-取消点赞
	 * @return
	 */
	@PostMapping("likes.do")
	public ResponseDto likes(@RequestParam(name = "id", required = true) Integer id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			Integer presentor = user.getId();
			ResponseDto dto = askSpecialService.likes(presentor, id);
			if(dto.getData() != null){
				AskSpecial askSpecial = askSpecialService.selectByPrimaryKey(id);
				// 加经验值
				pointService.addPoint(user.getId(), PointRuleType.TYPE6.getValue(), dto.getData().toString(),
						ExtidType.TYPE2_.getValue());
				//被点赞者增加经验值
				pointService.addPoint(askSpecial.getPresentor(), PointRuleType.TYPE7.getValue(), askSpecial.getId().toString(),
						ExtidType.TYPE2_.getValue());
			}
			return ResultUtil.result(0, dto.getMsg());
		} catch (Exception e) {
			logger.error("askInfo likes is err:", e);
			return ResultUtil.serviceException();
		}
	}
}
