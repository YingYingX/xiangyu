package com.chinamobile.hnu.xiangyu.web.user;

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

import com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubSpeakVo;
import com.chinamobile.hnu.xiangyu.common.dao.PubGradeRuleMapper;
import com.chinamobile.hnu.xiangyu.common.enums.ExtidType;
import com.chinamobile.hnu.xiangyu.common.enums.PointRuleType;
import com.chinamobile.hnu.xiangyu.common.pojo.PubBinding;
import com.chinamobile.hnu.xiangyu.common.pojo.PubFeedback;
import com.chinamobile.hnu.xiangyu.common.service.PubBindingService;
import com.chinamobile.hnu.xiangyu.common.service.PubPointService;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.pojo.UserCertification;
import com.chinamobile.hnu.xiangyu.user.pojo.UserFriend;
import com.chinamobile.hnu.xiangyu.user.pojo.UserLabel;
import com.chinamobile.hnu.xiangyu.user.pojo.UserReservedQuestion;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.user.service.UserAccountService;
import com.chinamobile.hnu.xiangyu.user.service.UserService;
import com.chinamobile.hnu.xiangyu.user.vo.UserFavoriteVo;
import com.chinamobile.hnu.xiangyu.user.vo.UserHistoricalFootprintVo;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.Utils;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author The Old Man and the Sea
 *
 *         2018年6月7日
 */
@RestController
@RequestMapping("/api/user/")
public class UserController {

	private final static Logger log = LoggerFactory.getLogger(UserController.class);

	@Value("${constant.card}")
	private String CARD;

	@Value("${constant.head}")
	private String HEAD;

	@Autowired
	private UploadDocumentUtil fileUtil;

	@Autowired
	private AuthTokenService tokenService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserAccountService accountService;

	@Autowired
	private ClubSpeakService speakService;

	@Autowired
	private PubBindingService bindService;

	@Autowired
	private PubGradeRuleMapper gradeRuleMapper;

	@Autowired
	private PubPointService pointService;

	/****
	 * 查询当前用户信息
	 * 
	 * @return
	 */
	@PostMapping("info.do")
	public ResponseDto info(@RequestParam(required = false) Integer uid) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		int userid = user.getId();
		try {
			if (uid != null) {
				user = accountService.selectByPrimaryKey(uid);
				if (user == null) {
					ResultUtil.result(0, "没有用户信息");
				}
			}
			UserAccount userInfo = new UserAccount();
			if (StringUtils.isNotBlank(user.getHeader())) {
				userInfo.setHeader(UploadDocumentUtil.buildPublicPath(user.getHeader()));
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
			//用户标签
			List<UserLabel> userLabels = userService.selectUserLabel(user.getId());
			userInfo.setUserLabels(userLabels);
			// 升级所需总经验值
			userInfo.setTotalExperience(gradeRuleMapper.selectByPrimaryKey(user.getGrade()).getLower());
			// 当前级升级所需经验值
			int pre = gradeRuleMapper.selectByPrimaryKey(user.getGrade() + 1).getLower();
			userInfo.setNeedExperience(pre - userInfo.getTotalExperience());
			
			//查询其他用户时，设置备注
			if(uid != null && uid.intValue() != userid){
				userInfo.setUid(uid);
				userInfo.setUserNickName(userInfo.getNickname());
				userService.setFriendNickName(userInfo, userid);
				userInfo.setUid(null);
			}
			
			return ResultUtil.result(0, "查询成功", userInfo);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select UserInfo Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/****
	 * 修改用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	@PostMapping("update.do")
	public ResponseDto update(HttpServletRequest request, UserAccount userInfo) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}

		try {
			Map<String, Object> map = new HashMap<>();
			userInfo.setId(user.getId());
			ResponseDto dto = fileUtil.saveFile(request, user.getId().toString(), HEAD, new String[] { "jpg", "png" });
			if (dto.getCode() == 0) {
				if (StringUtils.isNotBlank(user.getHeader())) {
					pointService.addPoint(user.getId(), PointRuleType.TYPE9.getValue(), user.getId().toString(),
							ExtidType.TYPE1.getValue());
				}
				userInfo.setHeader(dto.getData().toString());
				map.put("head", UploadDocumentUtil.buildPublicPath(userInfo.getHeader()));

			} else if (dto.getCode() == 2) {
				return dto;
			}
			if (StringUtils.isNotBlank(userInfo.getNickname())) {
				if (userInfo.getNickname().length() > 32) {
					return ResultUtil.result(2, "昵称字符过长");
				}
				UserAccount acct = accountService.selectByNickname(userInfo.getNickname());
				if (acct != null && acct.getId().intValue() != user.getId()) {
					return ResultUtil.result(2, "昵称被占用");
				}
			}
			accountService.updateByPrimaryKeySelective(userInfo);
			return ResultUtil.result(0, "修改成功", map);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("update UserInfo Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	
	/****
	 * 修改用户备注
	 * @param userInfo
	 * @return
	 */
	@PostMapping("updateRemark.do")
	public ResponseDto update(UserFriend userFriend) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}

		try {
			userFriend.setUserId(user.getId());
			userService.updateRemark(userFriend);
			return ResultUtil.result(0, "修改成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("update UserFriend Exception:", e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/****
	 * 用户预设问题
	 * 
	 * @param questions
	 * @return
	 */
	@PostMapping("question.do")
	public ResponseDto question(@RequestParam(required = true) String questions) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}

		Date date = new Date();
		try {
			JSONArray array = JSONArray.fromObject(questions);
			List<UserReservedQuestion> list = new ArrayList<>(array.size());
			for (Object object : array) {
				UserReservedQuestion question = (UserReservedQuestion) JSONObject.toBean((JSONObject) object,
						UserReservedQuestion.class);
				if (question == null) {
					return ResultUtil.result(2, "json格式错误");
				} else if (StringUtils.isBlank(question.getContent())) {
					return ResultUtil.result(2, "问题不能为空");
				} else if (StringUtils.isBlank(question.getAnswer())) {
					return ResultUtil.result(2, "问题答案不能为空");
				} else if (question.getAnswer().length() > 32) {
					return ResultUtil.result(2, "问题答案字符过长");
				}
				question.setUserId(user.getId());
				question.setGmtCreate(date);
				list.add(question);
			}
			userService.insertUserReservedQuestion(list);
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("insert UserReservedQuestion Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/****
	 * 标签选择
	 * 
	 * @param sex
	 * @param labels
	 * @return
	 */
	@PostMapping("label.do")
	public ResponseDto label(@RequestParam(required = true) Integer sex, String labels) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}

		try {
			userService.insertUserLabelAndSex(user.getId(), Byte.valueOf(sex.toString()), labels);
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("insert UserLabel Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/****
	 * 会员认证
	 * 
	 * @param request
	 * @param certification
	 * @param result
	 * @return
	 */
	@PostMapping("certification.do")
	public ResponseDto save(HttpServletRequest request, @Validated UserCertification certification,
			BindingResult result) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}

		if (result.hasErrors()) {
			return ResultUtil.result(2, result.getAllErrors().get(0).getDefaultMessage());
		}

		try {
			certification.setUserId(user.getId());
			ResponseDto dto = fileUtil.savebatchFile(request, user.getId().toString(), CARD,
					new String[] { "jpg", "png" });
			if (dto.getCode() == 0) {
				int temp = 0;
				for (String fileId : (List<String>) dto.getData()) {
					if (temp == 0) {
						temp++;
						certification.setIdcardPositive(fileId);
					} else {
						certification.setIdcardNegative(fileId);
					}

				}
			} else {
				return dto;
			}
			userService.insertUserCertification(certification);
			return ResultUtil.result(0, "提交成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("save ClubActivity Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/***
	 * 我的团言
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @return
	 */
	@PostMapping("mySpeak.do")
	public ResponseDto list(@RequestParam(name = "pn", required = true, defaultValue = "0") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "10") Integer pageSize) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", user.getId());
		map.put("my", true);
		Page<ClubSpeakVo> page = null;
		try {
			page = speakService.selectByPage(pageCurrent, pageSize, map);
			return ResultUtil.result(0, "查询成功", new PageVo(page.getData(), page.getTotalCount(), page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select my clubSpeak page Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/****
	 * 我的收藏
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @return
	 */
	@PostMapping("myfavorite.do")
	public ResponseDto myfavorite(@RequestParam(name = "pn", required = true, defaultValue = "0") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "10") Integer pageSize,
			@RequestParam(required=false) Integer type) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", user.getId());
		map.put("type", type==null?0:type);
		Page<UserFavoriteVo> page = null;
		try {
			page = userService.selectUserFavoriteByPage(pageCurrent, pageSize, map);
			return ResultUtil.result(0, "查询成功", new PageVo(page.getData(), page.getTotalCount(), page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select UserFavorite page Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/***
	 * 我的足迹
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param type
	 * @return
	 */
	@PostMapping("myfootprint.do")
	public ResponseDto myfootprint(@RequestParam(name = "pn", required = true, defaultValue = "0") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "10") Integer pageSize,
			@RequestParam(required = true) Integer type) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", user.getId());
		map.put("type", type);
		Page<UserHistoricalFootprintVo> page = null;
		try {
			page = userService.selectUserHistoricalFootprintByPage(pageCurrent, pageSize, map);
			return ResultUtil.result(0, "查询成功", new PageVo(page.getData(), page.getTotalCount(), page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select UserHistoricalFootprint page Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/***
	 * 查询我预设的问题
	 * 
	 * @return
	 */
	@PostMapping("myQuestion.do")
	public ResponseDto myQuestion() {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			List<UserReservedQuestion> list = userService.selctUserReservedQuestion(user.getId());
			return ResultUtil.result(0, "查询成功", list);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select my Question Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/****
	 * 修改手机号
	 * 
	 * @param phone
	 * @param code
	 * @param questionId
	 * @param answer
	 * @return
	 */
	@PostMapping("updatePhone.do")
	public ResponseDto updatePhone(@RequestParam(required = true) String phone,
			@RequestParam(required = true) String code, @RequestParam(required = true) Integer questionId,
			@RequestParam(required = true) String answer) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// 手机号码合法性检查，
		if (StringUtils.isBlank(phone)) {
			return ResultUtil.result(3, "手机号码不能为空");
		}

		// 验证合法性
		if (!Utils.checkPhone(phone)) {
			return ResultUtil.result(3, "不是合法的手机号码");
		}
		try {

			// 查询是否已经被占用
			if (accountService.checkPhone(phone)) {
				return ResultUtil.result(3, "此手机号码已经注册了");
			}

			if (StringUtils.isBlank(code)) {
				return ResultUtil.result(3, "验证码不能为空");
			} else {
				// 验证码验证
				PubBinding bd = new PubBinding();
				bd.setPhone(phone);
				bd.setCode(code);
				if (!bindService.checkBindingCode(bd)) {
					// 验证失败
					return ResultUtil.result(3, "验证码有误");
				}
			}
			UserReservedQuestion question = userService.selctUserReservedQuestionById(questionId);
			if (question == null) {
				return ResultUtil.result(2, "没有该项目");
			} else {
				if (!answer.equals(question.getAnswer())) {
					return ResultUtil.result(2, "问题答案不正确");
				}
			}
			UserAccount userAccount = new UserAccount();
			userAccount.setId(user.getId());
			userAccount.setTelephone(phone);
			accountService.updateByPrimaryKeySelective(userAccount);
			return ResultUtil.result(0, "修改成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("update updatePhone Exception:", e);
			return ResultUtil.serviceException();
		}
	}

	/****
	 * 用户反馈
	 * 
	 * @param pubFeedback
	 * @param result
	 * @return
	 */
	@PostMapping("feedback.do")
	public ResponseDto feedback(@Validated PubFeedback pubFeedback, BindingResult result) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			pubFeedback.setPresentor(user.getId());
			userService.insertPubFeedback(pubFeedback);
			return ResultUtil.result(0, "提交成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("insert feedback Exception:", e);
			return ResultUtil.serviceException();
		}
	}

}
