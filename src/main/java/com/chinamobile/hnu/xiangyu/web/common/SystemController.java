/**
 * 
 */
package com.chinamobile.hnu.xiangyu.web.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinamobile.hnu.xiangyu.common.pojo.AppVersion;
import com.chinamobile.hnu.xiangyu.common.service.IAppVersionService;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * @author The Old Man and the Sea
 *
 *         2018年1月15日
 */
@RestController
@RequestMapping("/api/system/")
public class SystemController {

	private static final Logger logger = LoggerFactory
			.getLogger(SystemController.class);

	@Autowired
	private AuthTokenService tokenService;

	@Autowired
	private IAppVersionService versionService;

	/***
	 * 查询最新版本信息
	 * 
	 * @return
	 */
	@PostMapping(path = "lastVersion.do")
	public ResponseDto lastVersion() {

		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}

		try {
			AppVersion version = versionService.selectAppVersionNow(1);
			return ResultUtil.result(0, "查询成功", version);

		} catch (Exception e) {
			logger.info("find app Version exception :", e);
			return ResultUtil.serviceException();
		}
	}

	/***
	 * 保存版本信息
	 * 
	 * @param version
	 * @param result
	 * @return
	 */
	@PostMapping(path = "saveVersion.do")
	public ResponseDto saveVersion(@RequestBody @Validated AppVersion version,
			BindingResult result) {

		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			version.setUptime(new Date());
			version.setApptype(1);
			versionService.insert(version);
		} catch (Exception e) {
			logger.info("app Version save exception :", e);
			return ResultUtil.serviceException();
		}

		return ResultUtil.result(0, "发布成功");
	}

	/***
	 * 版本分页
	 * 
	 * @param pn
	 * @param ps
	 * @return
	 */
	@PostMapping(path = "list.do")
	public ResponseDto list(@RequestBody Map<String, Integer> params) {

		Integer ps = params.get("ps");
		if (ps == null) {
			return ResultUtil.result(2, "ps不能为空");
		}
		Integer pn = params.get("pn");
		if (pn == null) {
			return ResultUtil.result(2, "pn不能为空");
		}

		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Page<AppVersion> page = versionService.selectAppVersionByPage(pn,
					ps, map);
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.info("select app Version list exception :", e);
			return ResultUtil.serviceException();
		}

	}

}
