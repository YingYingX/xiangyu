/**  

 * <p>Title: ClubAdminController.java</p>  

 * <p>Description: </p>  

 * <p>Copyright: Copyright douziit(c) 2017</p>  

 * @author lh  

 * @date 2018年6月15日  

 * @version 1.0  

 */
package com.chinamobile.hnu.xiangyu.web.api;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sun.misc.BASE64Encoder;

import com.chinamobile.hnu.xiangyu.auth.pojo.AuthResources;
import com.chinamobile.hnu.xiangyu.auth.pojo.AuthRole;
import com.chinamobile.hnu.xiangyu.auth.pojo.AuthRoleResources;
import com.chinamobile.hnu.xiangyu.auth.service.IAuthResourcesService;
import com.chinamobile.hnu.xiangyu.auth.service.IAuthRoleResourcesService;
import com.chinamobile.hnu.xiangyu.auth.service.IAuthRoleService;
import com.chinamobile.hnu.xiangyu.auth.service.IAuthUserRoleService;
import com.chinamobile.hnu.xiangyu.auth.vo.RoleVo;
import com.chinamobile.hnu.xiangyu.club.service.ClubActivityService;
import com.chinamobile.hnu.xiangyu.club.service.ClubBaseService;
import com.chinamobile.hnu.xiangyu.user.pojo.AuthToken;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.user.service.UserAccountService;
import com.chinamobile.hnu.xiangyu.user.vo.UserVo;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * 
 * <p>
 * Title: ClubAdminController.java
 * </p>
 * 
 * <p>
 * Description: 权限管理(后台管理端)
 * </p>
 * 
 * @author lh
 * 
 * @date 2018年6月15日
 * 
 * @version 1.0
 */
@RestController
@RequestMapping("/api/admin/auth/")
public class AuthAdminController {
	private final Logger logger = Logger.getLogger(AuthAdminController.class);

	@Autowired
	private AuthTokenService tokenService;

	@Autowired
	private IAuthResourcesService resourcesService;

	@Autowired
	private IAuthRoleResourcesService roleResourcesService;

	@Autowired
	private IAuthRoleService roleService;

	@Autowired
	private UserAccountService userService;

	@Autowired
	private IAuthUserRoleService userRoleService;

	@Autowired
	private ClubActivityService activityService;

	@Autowired
	private ClubBaseService baseService;

	@Autowired
	private UserAccountService service;

	/**
	 * 新增 or 保存 资源
	 * 
	 * @param resources
	 * @return
	 */
	@PostMapping("saveResources.do")
	public ResponseDto saveResources(@RequestBody AuthResources resources) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// 判断是否有权限操作
		if (StringUtils.isBlank(resources.getResourceName())) {
			return ResultUtil.result(3, "资源名称不能为空");
		}
		if (StringUtils.isBlank(resources.getResourceUrl())) {
			return ResultUtil.result(3, "资源地址不能为空");
		}
		try {
			if (resources.getId() == null) {
				resources.setCreateTime(new Date());
				resourcesService.insertSelective(resources);
				return ResultUtil.result(0, "保存成功");
			} else {
				resourcesService.updateByPrimaryKeySelective(resources);
				return ResultUtil.result(0, "更新成功");
			}
		} catch (Exception e) {
			logger.error("saveResources  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 删除资源
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("deleteResources.do")
	public ResponseDto deleteResources(
			@RequestParam(name = "id", required = true) Integer id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// 判断是否有权限操作
		try {
			AuthResources record = new AuthResources();
			record.setStatus(1);
			record.setId(id);
			resourcesService.updateByPrimaryKeySelective(record);
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			logger.error("deleteResources  is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 资源列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @return
	 */
	@PostMapping("pglist.do")
	public ResponseDto pglist(@RequestBody Map<String, Object> maps) {
		Integer pageCurrent = (Integer) maps.get("pn");
		Integer pageSize = (Integer) maps.get("ps");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Page<AuthResources> page = null;
		try {
			page = resourcesService.selectPage(pageCurrent, pageSize);
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
	 * 创建or更新角色名称
	 * 
	 * @param role
	 * @return
	 */
	@PostMapping("saveRole.do")
	public ResponseDto saveRole(@RequestBody AuthRole role) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		if (StringUtils.isBlank(role.getRoleName())) {
			return ResultUtil.result(3, "角色名称不能为空");
		}
		try {
			if (role.getRoleId() == null) {
				roleService.insertSelective(role);
				return ResultUtil.result(0, "创建成功");
			} else {
				roleService.updateByPrimaryKeySelective(role);
				return ResultUtil.result(0, "更新成功");
			}
		} catch (Exception e) {
			logger.error("saveRole is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 批量删除角色
	 * 
	 * @param ids
	 * @return
	 */
	@PostMapping("batchDelRole.do")
	public ResponseDto batchDelRole(@RequestBody List<Integer> ids) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		if (ids.size() <= 0) {
			return ResultUtil.result(3, "角色id不能为空");
		}
		try {
			List<Integer> idList = new ArrayList<Integer>();
			// String[] strs = ids.split(",");
			for (Integer str : ids) {
				if (str.equals(20)) {
					return ResultUtil.result(3, "专家角色不能做删除操作！");
				}
				idList.add(str);
			}
			roleService.batchDelRolesById(idList);
			return ResultUtil.result(0, "删除成功");
		} catch (Exception e) {
			logger.error("batchDelRole is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 角色设置权限
	 * 
	 * @param resourceId
	 * @param roleId
	 * @return
	 */
	@PostMapping("saveRoleResource/{roleId}.do")
	public ResponseDto saveRoleResource(@RequestBody List<Integer> resourceId,
			@PathVariable("roleId") Integer roleId) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			List<AuthRoleResources> roleRes = new ArrayList<AuthRoleResources>();
			// String[] strs = resourceId.split(",");
			for (Integer str : resourceId) {
				AuthRoleResources record = new AuthRoleResources();
				record.setRoleId(roleId);
				record.setResourceId(str);
				roleRes.add(record);
			}
			roleResourcesService.insertBatchRoleRes(roleRes, roleId);
			return ResultUtil.result(0, "设置成功");
		} catch (Exception e) {
			logger.error("saveRoleResource is err:", e);
			return ResultUtil.serviceException();
		}

	}

	/**
	 * 角色列表
	 * 
	 * @return
	 */
	@PostMapping("roleList.do")
	public ResponseDto roleList() {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		// 判断是否有权限
		List<String> listMaps = resourcesService.selectUserResourceLists(user
				.getRoleId());
		if (listMaps.size() > 0) {
			if (!listMaps.contains("/role")) {
				return ResultUtil.result(5, "暂无权限!");
			}
		} else {
			return ResultUtil.result(5, "暂无权限!");
		}
		try {
			List<RoleVo> roleVo = roleService.selectRoleList();
			return ResultUtil.result(0, "查询成功", roleVo);
		} catch (Exception e) {
			logger.error("roleList is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 资源列表（树形）
	 * 
	 * @return
	 */
	@PostMapping("resourceList.do")
	public ResponseDto resourceList() {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			JSONArray ztree = resourcesService.selectResourceList();
			return ResultUtil.result(0, "查询成功", ztree);
		} catch (Exception e) {
			logger.error("resourceList is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 用户列表
	 * 
	 * @return
	 */
	@PostMapping("userList.do")
	public ResponseDto userList(@RequestBody Map<String, Object> maps) {
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
			if (!listMaps.contains("/user")) {
				return ResultUtil.result(5, "暂无权限!");
			}
		} else {
			return ResultUtil.result(5, "暂无权限!");
		}

		try {
			Page<UserVo> page = userService.selectUserList(pageCurrent,
					pageSize);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("userList is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 新增or修改用户
	 * 
	 * @param userAccount
	 * @return
	 */
	@PostMapping("insertUser.do")
	public ResponseDto insertUser(@RequestBody UserAccount userAccount) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		if (StringUtils.isBlank(userAccount.getNickname())
				&& StringUtils.isBlank(userAccount.getPassword())) {
			return ResultUtil.result(3, "请输入用户名和密码");
		}
		if (AuthAdminController.isEnglish(userAccount.getTelephone()) == false) {
			return ResultUtil.result(3, "用户名只能包含英文数字下划线");
		}
		if (userAccount.getTelephone().length() > 11) {
			return ResultUtil.result(3, "用户名最长不能超过11位");
		}
		try {
			userAccount.setGmtCreate(new Date());
			if (userAccount.getId() == null) {
				boolean phoneUse = true;
				// 查询手机是否已经绑定
				try {
					phoneUse = service.checkPhone(userAccount.getTelephone());
					// 已经存在手机号
					if (phoneUse) {
						return ResultUtil.result(3, "该账号已经注册了");
					}
				} catch (Exception e2) {
					logger.error("checkPhone is err:", e2);
					return ResultUtil.serviceException();
				}
				try {
					String p = AuthAdminController.encodePwd(userAccount
							.getPassword());
					userAccount.setPassword(p);
				} catch (Exception e) {
					logger.error("unsportted exception or encode exception", e);
					return ResultUtil.result(3, "验证密码时出错");
				}
				userService.insertSelective(userAccount);
				return ResultUtil.result(0, "新增成功");
			} else {
				userService.updateByPrimaryKeySelective(userAccount);
				return ResultUtil.result(0, "更新成功");
			}
		} catch (Exception e) {
			logger.error("insertUser is err:", e);
			return ResultUtil.serviceException();
		}

	}

	/**
	 * 禁用用户（批量）
	 * 
	 * @param userId
	 * @param type
	 *            :0-不禁用；1-禁用
	 * @return
	 */
	@PostMapping("batchEnableUser/{type}.do")
	public ResponseDto batchEnableUser(@RequestBody List<Integer> idList,
			@PathVariable("type") Integer type) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			// List<Integer> idList = new ArrayList<Integer>();
			// String[] strs = userId.split(",");
			// for (String str : strs) {
			// idList.add(Integer.valueOf(str));
			// }
			for (Integer userIds : idList) {
				if (type.equals(1)) {
					userService.updateEanbled(userIds, false);
				}
				if (type.equals(0)) {
					userService.updateEanbled(userIds, true);
				}
			}
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			logger.error("batchEnableUser is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 重置密码
	 * 
	 * @param pwd
	 * @return
	 */
	@PostMapping("resetPwd.do")
	public ResponseDto resetPwd(@RequestBody Map<String, Object> maps) {
		Integer userId = (Integer) maps.get("userId");
		String pwd = (String) maps.get("pwd");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		String p = null;
		try {
			p = AuthAdminController.encodePwd(pwd);
		} catch (Exception e) {
			logger.error("unsportted exception or encode exception", e);
			return ResultUtil.result(3, "验证密码时出错");
		}
		try {
			userService.updatePassword(userId, p);
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			logger.error("resetPwd is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 修改密码
	 * 
	 * @param pwd
	 * @return
	 */
	@PostMapping("/modifyPwd.do")
	public ResponseDto modifyPwd(@RequestBody Map<String, Object> maps) {
		Integer userId = (Integer) maps.get("userId");
		String mpwd = (String) maps.get("mpwd");
		String pwd = (String) maps.get("pwd");
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		String mp = null;
		String p = null;
		try {
			// 原始密码
			mp = AuthAdminController.encodePwd(mpwd);
			p = AuthAdminController.encodePwd(pwd);
		} catch (Exception e) {
			logger.error("unsportted exception or encode exception", e);
			return ResultUtil.result(3, "验证密码时出错");
		}
		try {
			if (!mp.equals(user.getPassword())) {
				return ResultUtil.result(3, "原始密码输入错误！");
			}
			userService.updatePassword(userId, p);
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			logger.error("resetPwd is err:", e);
			return ResultUtil.serviceException();
		}
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
	public ResponseDto login(HttpServletRequest request,
			HttpServletResponse response, @RequestBody Map<String, Object> maps) {
		String phone = (String) maps.get("phone");
		String pwd = (String) maps.get("pwd");
		if (StringUtils.isBlank(phone)) {
			return ResultUtil.result(3, "请输入用户名");
		}

		if (StringUtils.isBlank(pwd)) {
			return ResultUtil.result(3, "请输入密码");
		}

		// 根据手机号码查询用户
		UserAccount user = null;
		try {
			user = service.findByPhone(phone);
		} catch (Exception ex) {
			logger.error("query user by phone exception:", ex);
			return ResultUtil.result(3, "后台查询用户失败");
		}

		if (user == null) {
			return ResultUtil.result(3, "该账号还没有注册");
		}
		if (user.getEnable() == 1) {
			return ResultUtil.result(3, "该账号已禁用");
		}
		try {
			String p = AuthAdminController.encodePwd(pwd);
			// 验证密码是否正确
			if (!p.equals(user.getPassword())) {
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
			request.getSession()
					.setAttribute(AuthToken.ATTR_SESSION_USER, user);
		} catch (Exception ex) {
			logger.error("query user info exception", ex);
			return ResultUtil.result(3, "后台错误，登录失败");
		}

		// 生成token
		String token = UUID.randomUUID().toString()
				+ UUID.randomUUID().toString();
		logger.info("gen token==========>" + token);
		AuthToken item = new AuthToken();
		item.setToken(token);
		item.setUid(userid);
		try {
			// 插入token，忽略其异常
			tokenService.insert(item);
		} catch (Exception ex) {
			logger.warn("insert user token exception,{}" + phone, ex);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("firstFlag", firstFlag);
		map.put("uid", userid);
		map.put("token", token);
		// 把资源树返回出去
		// JSONArray jsonArray = resourcesService.selectUserResourceList(user
		// .getRoleId());
		// map.put("resource", jsonArray);
		return ResultUtil.result(0, "登录成功", map);
	}

	/**
	 * 获取当前用户资源树
	 * 
	 * @return
	 */
	@PostMapping(value = "/userResource.do")
	public ResponseDto userResource() {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 把资源树返回出去
			JSONArray jsonArray = resourcesService.selectUserResourceList(user
					.getRoleId());
			map.put("resource", jsonArray);
			return ResultUtil.result(0, "获取资源树成功", map);
		} catch (Exception e) {
			logger.error("delete token exception:{}", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 退出登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/logout.do")
	public ResponseDto logout(HttpServletRequest request,
			HttpServletResponse response) {
		String token = request.getHeader(AuthToken.USER_ACCESS_TOKEN);
		if (StringUtils.isBlank(token)) {
			return ResultUtil.result(3, "请正确退出登录");
		}

		try {
			tokenService.deleteByToken(token);
			request.getSession().removeAttribute(AuthToken.ATTR_SESSION_USER);
		} catch (Exception ex) {
			logger.error("delete token exception:{}" + token, ex);
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
	protected static String encodePwd(String pwd)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
		return base64en.encode(md5.digest(pwd.getBytes("utf-8")));
	}

	/**
	 * 判断是否是英文
	 * 
	 * @param charaString
	 * @return
	 */
	public static boolean isEnglish(String charaString) {

		return charaString.matches("^[a-zA-Z0-9][a-zA-Z0-9_]+$");

	}

	public static void main(String[] args) {
		System.out.println(isEnglish("asdas_123456789"));
	}

}
