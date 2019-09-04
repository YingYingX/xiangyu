package com.chinamobile.hnu.xiangyu.web.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinamobile.hnu.xiangyu.common.pojo.PubAdvertisement;
import com.chinamobile.hnu.xiangyu.common.pojo.PubConstPlane;
import com.chinamobile.hnu.xiangyu.common.service.PubConstPlaneService;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * 常量管理
 * 
 * @author The Old Man and the Sea
 *
 *         2018年5月28日
 */

@RestController
@RequestMapping("/api/pub/category/")
public class CategoryController {
	private static final Logger logger = Logger
			.getLogger(CategoryController.class);

	@Autowired
	private PubConstPlaneService conService;

	/**
	 * 常量列表查询
	 * 
	 * @return
	 */
	@PostMapping("list.do")
	public ResponseDto list(
			@RequestParam(name = "type", required = true) String type) {
		List<PubConstPlane> list = null;
		try {
			if ("question".equals(type)) {
				list = new ArrayList<PubConstPlane>();
				list.addAll(conService.selectPlanesByType("question1"));
				list.addAll(conService.selectPlanesByType("question2"));
				list.addAll(conService.selectPlanesByType("question3"));
			} else {
				list = conService.selectPlanesByType(type);
			}
		} catch (Exception e) {
			logger.error("select all category exception:", e);
			return ResultUtil.serviceException();
		}
		return ResultUtil.result(0, "查询成功", list);
	}

	/****
	 * 查询广告图片
	 * 
	 * @param category
	 * @return
	 */
	@PostMapping("advertisement.do")
	public ResponseDto advertisement(
			@RequestParam(name = "category", required = true) Integer category) {
		List<PubAdvertisement> list = null;
		try {
			list = conService.selectPubAdvertisementByType(category);
		} catch (Exception e) {
			logger.error("select all PubAdvertisement exception:", e);
			return ResultUtil.serviceException();
		}
		return ResultUtil.result(0, "查询成功", list);
	}

}
