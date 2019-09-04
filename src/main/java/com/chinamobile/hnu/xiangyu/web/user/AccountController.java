/**
 * 
 */
package com.chinamobile.hnu.xiangyu.web.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sun.misc.BASE64Encoder;

import com.chinamobile.hnu.xiangyu.common.pojo.PubBinding;
import com.chinamobile.hnu.xiangyu.common.service.PubBindingService;
import com.chinamobile.hnu.xiangyu.sms.SmsService;
import com.chinamobile.hnu.xiangyu.user.pojo.AuthToken;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.user.service.UserAccountService;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.Utils;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * 用户账号相关的Web处理逻辑
 * 
 * @author songcl
 * @date 2018年5月18日
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	// 短信内容
	public static final String SMS_CONTENT = "验证码：%s。请输入验证码完成验证，验证码有效时间为30分钟【湘遇】";

	@Autowired
	private PubBindingService bindService;

	@Autowired
	private SmsService smsService;

	@Autowired
	private UserAccountService service;

	@Autowired
	private AuthTokenService tokenService;

	/**
	 * 给手机发送短信验证码
	 * 
	 * @param phone
	 *            接受短信的手机号码
	 * @param forget
	 *            是否为忘记密码时发送短信验证码
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "/scode.do")
	public ResponseDto scode(@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "forget", required = false) String forget) throws IOException {
		// 手机号为空判断
		if (StringUtils.isBlank(phone)) {
			return ResultUtil.result(3, "手机号不能为空");
		}

		// 手机号码格式验证
		if (!Utils.checkPhone(phone)) {
			return ResultUtil.result(3, "手机号格式输入错误");
		}

		logger.info("send sms to phone:" + phone);

		// 是否为忘记密码发送验证
		boolean isforget = false;
		if (StringUtils.isNotBlank(forget)) {
			isforget = true;
		}

		boolean phoneUse = true;
		// 查询手机是否已经绑定
		try {
			phoneUse = service.checkPhone(phone);
			if (isforget) {
				if (!phoneUse) {
					return ResultUtil.result(3, "该手机号码还没有注册");
				}

				phoneUse = !phoneUse;
			}

			// 已经存在手机号
			if (phoneUse) {
				return ResultUtil.result(3, "该手机号码已经注册了");
			}

			// 判断今天是否已经有20条发送验证码数据
			if (bindService.getBindCountByNow(phone, 20)) {
				return ResultUtil.result(3, "此手机号今日发送验证码数量超过限制");
			} else if (bindService.getBindByTimeOffset(phone, 1)) {
				// 判断刚发的验证码是在1分钟之内吗
				return ResultUtil.result(3, "1分钟之内不能重复发送");
			}
		} catch (Exception ex) {
			logger.error("query binding item exception, phone:{}", phone, ex);
			return ResultUtil.result(3, "后台处理出错，请联系客服。");
		}

		// 生成验证码
		String genCode = RandomStringUtils.randomNumeric(6);
		// 向手机发送验证码
		String smsMsg = null;
		smsMsg = String.format(SMS_CONTENT, genCode);
		int ret = smsService.sendSms(phone, smsMsg);
		logger.info("send code sms message:" + phone + "," + smsMsg + ",send result:" + ret);
		if (ret == 0) {
			// 短信发送成功
			try {
				PubBinding bd = new PubBinding();
				bd.setPhone(phone);
				bd.setCode(genCode);
				bd.setGmtCreate(new Date());
				bindService.insert(bd);
				return ResultUtil.result(0, "验证码已发送");
			} catch (Exception e) {
				logger.warn("send phone code exception:" + phone, e);
			}
		}

		return ResultUtil.result(3, "短信发送失败，请联系客服。");
	}

	/**
	 * 检查手机号码是否已经被注册
	 * 
	 * @param uname
	 * @return
	 */
	@PostMapping(value = "/checkphone.do")
	public ResponseDto checkphone(@RequestParam(value = "phone", required = true) String phone) {
		// 手机号为空判断
		if (StringUtils.isBlank(phone)) {
			return ResultUtil.result(3, "手机号码不能为空");
		}

		boolean ret = false;
		try {
			ret = service.checkPhone(phone);
		} catch (Exception ex) {
			logger.error("check phone exception:" + phone, ex);
			return ResultUtil.result(3, "后台查询出错");
		}

		Map<String, Object> map = new HashMap<>();
		map.put("flag", ret ? 1 : 0);
		return ResultUtil.result(0, "查询成功", map);
	}

	/**
	 * 注册新的用户
	 * 
	 * @param phone
	 *            手机号码
	 * @param code
	 *            短信验证码
	 * @param pwd
	 *            密码
	 * @return
	 */
	@PostMapping(value = "/register.do")
	public ResponseDto register(HttpServletRequest request, HttpServletResponse response, @RequestParam String phone,
			@RequestParam String code, @RequestParam String pwd, String school) {
		// 手机号码合法性检查，
		if (StringUtils.isBlank(phone)) {
			return ResultUtil.result(3, "手机号码不能为空");
		}

		// 验证合法性
		if (!Utils.checkPhone(phone)) {
			return ResultUtil.result(3, "不是合法的手机号码");
		}

		// 查询是否已经被占用
		if (service.checkPhone(phone)) {
			return ResultUtil.result(3, "此手机号码已经注册了，请直接登录");
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

		// 验证密码的合法性
		if (StringUtils.isBlank(pwd)) {
			return ResultUtil.result(3, "密码不能为空");
		}

		String encodePwd = null;

		try {
			encodePwd = AccountController.encodePwd(pwd);
		} catch (Exception ex) {
			logger.error("unsportted exception or encode exception", ex);
			return ResultUtil.result(3, "密码加密时出错");
		}

		Date date = new Date();
		UserAccount user = new UserAccount();
		user.setTelephone(phone);
		user.setNickname(phone.substring(0, 7)+"****");
		user.setPassword(encodePwd);
		user.setGmtCreate(date);
		user.setGmtModified(date);
		user.setCollege(school);
		// 生成token
		String token = UUID.randomUUID().toString() + UUID.randomUUID().toString();
		try {
			service.insertSelective(user);
			try {
				// 计入session
				request.getSession().setAttribute(AuthToken.ATTR_SESSION_USER, user);
			} catch (Exception ex) {
				logger.error("query user info exception", ex);
				return ResultUtil.result(3, "后台错误，登录失败");
			}

			logger.info("gen token:{}", token);
			AuthToken item = new AuthToken();
			item.setToken(token);
			item.setUid(user.getId());
			try {
				// 插入token，忽略其异常
				tokenService.insert(item);
			} catch (Exception ex) {
				logger.warn("insert user token exception,{}", phone, ex);
			}
		} catch (Exception ex) {
			logger.error("register new user exception:{}", user, ex);
			return ResultUtil.result(3, "注册失败，后台错误");
		}

		return ResultUtil.result(0, "恭喜你，注册成功，请登录", token);
	}

	/**
	 * 重置密码 0 密码重置成功 1 手机号不能为空 2 验证码不能为空 3 密码不能为空 4 手机号码输入有误 5 密码重置失败
	 * 
	 * @return
	 */
	@PostMapping(value = "/resetpwd.do")
	public ResponseDto resetpwd(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("phone") String phone, @RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "pwd", required = true) String pwd) {
		if (StringUtils.isBlank(phone)) {
			return ResultUtil.result(3, "手机号不能为空");
		}
		if (!Utils.checkPhone(phone)) {
			return ResultUtil.result(3, "不是合法的手机号码");
		}

		if (StringUtils.isBlank(code)) {
			return ResultUtil.result(3, "手机验证码不能为空");
		}

		if (StringUtils.isBlank(pwd)) {
			return ResultUtil.result(3, "密码不能为空");
		}

		// 手机号验证通过
		PubBinding bd = new PubBinding();
		bd.setPhone(phone);
		bd.setCode(code);

		try {
			if (bindService.checkBindingCode(bd)) {
				UserAccount user = service.findByPhone(phone);
				if (user == null) {
					return ResultUtil.result(2, "该手机号码还没有注册哦");
				}

				// 验证码验证通过,修改密码
				service.updatePassword(phone, AccountController.encodePwd(pwd));
			} else {
				return ResultUtil.result(3, "手机验证码错误");
			}
		} catch (Exception ex) {
			logger.error("reset password exception, phone: {}", phone, ex);
			return ResultUtil.result(3, "密码重置失败，请联系客服");
		}

		return ResultUtil.result(0, "密码重置成功");
	}

	/**
	 * 更新密码 ret值 Msg值 0 密码修改成功 1 用户没有登录 2 旧密码有误 3 新密码不能为空 4 密码修改失败
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping(value = "/uppwd.do")
	public ResponseDto uppwd(HttpServletRequest request, HttpServletResponse response, @RequestParam("pwd") String pwd,
			@RequestParam(value = "newpwd", required = true) String newpwd) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.result(1, "用户没有登录");
		}

		logger.info("update password, user:{}", user);

		if (StringUtils.isBlank(newpwd)) {
			return ResultUtil.result(3, "新密码不能为空");
		}

		// 加密密码
		String encodeOldPwd = null, encodeNewPwd = null;
		try {
			encodeOldPwd = AccountController.encodePwd(pwd);
			encodeNewPwd = AccountController.encodePwd(newpwd);
		} catch (Exception ex) {
			logger.error("unsportted exception or encode exception", ex);
			return ResultUtil.result(3, "密码加密时出错");
		}

		// 验证旧密码是否正确
		if (!service.verifyPwd(user.getId(), encodeOldPwd)) {
			return ResultUtil.result(3, "旧密码错误，请重新输入");
		}

		try {
			service.updatePassword(user.getId(), encodeNewPwd);
		} catch (Exception ex) {
			logger.warn("update password exception:{}", user, ex);
			return ResultUtil.result(3, "密码修改失败，后台错误");
		}

		return ResultUtil.result(0, "密码修改成功！");
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @param phone
	 * @param pwd
	 * @return
	 */
	@PostMapping(value = "/login.do")
	public ResponseDto login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("phone") String phone, @RequestParam(value = "pwd", required = true) String pwd) {
		if (StringUtils.isBlank(phone)) {
			return ResultUtil.result(3, "请输入手机号码");
		}

		if (!Utils.checkPhone(phone)) {
			return ResultUtil.result(3, "不是合法的手机号码");
		}

		if (StringUtils.isBlank(pwd)) {
			return ResultUtil.result(3, "请输入密码");
		}

		// 根据手机号码查询用户
		UserAccount user = null;
		try {
			user = service.findByPhone(phone);
		} catch (Exception ex) {
			logger.error("query user by phone exception, phone: {}", phone, ex);
			return ResultUtil.result(3, "后台查询用户失败");
		}

		if (user == null) {
			return ResultUtil.result(3, "该手机号码还没有注册");
		}
		if (user.getEnable() == 1) {
			return ResultUtil.result(3, "该账号已禁用");
		}
		try {
			String p = AccountController.encodePwd(pwd);
			// 验证密码是否正确
			if (p.equals(user.getPassword())) {
				return ResultUtil.result(3, "密码错误");
			}
		} catch (Exception e) {
			logger.error("unsportted exception or encode exception", e);
			return ResultUtil.result(3, "验证密码时出错");
		}

		int userid = user.getId();
		int firstFlag = 0;
		try {
			// 删除已经登录的token
			firstFlag = tokenService.deleteByUid(userid);
			// 计入session
			request.getSession().setAttribute(AuthToken.ATTR_SESSION_USER, user);
		} catch (Exception ex) {
			logger.error("query user info exception", ex);
			return ResultUtil.result(3, "后台错误，登录失败");
		}

		// 生成token
		String token = UUID.randomUUID().toString() + UUID.randomUUID().toString();
		logger.info("gen token:{}", token);
		AuthToken item = new AuthToken();
		item.setToken(token);
		item.setUid(userid);
		try {
			// 插入token，忽略其异常
			tokenService.insert(item);
		} catch (Exception ex) {
			logger.warn("insert user token exception,{}", phone, ex);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("firstFlag", firstFlag);
		map.put("uid", userid);
		map.put("token", token);
		return ResultUtil.result(0, "登录成功", map);
	}

	/**
	 * 退出登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/logout.do")
	public ResponseDto logout(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader(AuthToken.USER_ACCESS_TOKEN);
		if (StringUtils.isBlank(token)) {
			return ResultUtil.result(3, "请正确退出登录");
		}

		try {
			tokenService.deleteByToken(token);
			request.getSession().removeAttribute(AuthToken.ATTR_SESSION_USER);
		} catch (Exception ex) {
			logger.error("delete token exception:{}", token, ex);
		}

		return ResultUtil.result(0, "退出登录成功");
	}

	/**
	 * 密码加密，md5+BASE64
	 * 
	 * @param pwd
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	protected static String encodePwd(String pwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
		return base64en.encode(md5.digest(pwd.getBytes("utf-8")));
	}

}
