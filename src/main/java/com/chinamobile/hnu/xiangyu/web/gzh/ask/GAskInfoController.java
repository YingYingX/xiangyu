package com.chinamobile.hnu.xiangyu.web.gzh.ask;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService;
import com.chinamobile.hnu.xiangyu.ask.vo.AskCommentVo;
import com.chinamobile.hnu.xiangyu.ask.vo.AskInfoVo;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * 问问-问题
 * 
 * @author lh <自动生成> @联系QQ：2465613689
 * @version 2018-06-05 16:00:38
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/gzh/ask/base")
public class GAskInfoController {
	private final Logger logger = Logger.getLogger(GAskInfoController.class);

	@Autowired
	private IAskInfoService askInfoService;

	/**
	 * 问问列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param type 1-按推荐查询；2-按时间查询；3-按热门查询 *
	 * 
	 * @return
	 */
	@PostMapping("pglist.do")
	public ResponseDto pglist(@RequestParam Integer pageCurrent, @RequestParam Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		Page<AskInfoVo> page = null;
		try {
			map.put("type", 3);
			page = askInfoService.selectGzhPage(pageCurrent, pageSize, map);
			return ResultUtil.result(0, "查询成功", new PageVo(page.getData(), page.getTotalCount(), page.getTotalPage()));
		} catch (Exception e) {
			logger.error("pglist gzh pglist is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 查询问问详情
	 * 
	 * @param askid
	 * @return
	 */
	@PostMapping("detail.do")
	public ResponseDto askInfo(@RequestParam Long id) {
		try {
			AskInfoVo infoVo = askInfoService.GzhAskInfo(id);
			return ResultUtil.result(0, "查询成功", infoVo);
		} catch (Exception e) {
			logger.error("askInfo gzh askInfo is err:", e);
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
	@PostMapping("pgcomment.do")
	public ResponseDto pgfirst(@RequestParam Integer pageCurrent, @RequestParam Integer pageSize,
			@RequestParam Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Page<AskCommentVo> page = null;
		try {
			map.put("askid", id);
			page = askInfoService.GzhPgfirst(pageCurrent, pageSize, map);
			return ResultUtil.result(0, "查询成功", new PageVo(page.getData(), page.getTotalCount(), page.getTotalPage()));
		} catch (Exception e) {
			logger.error("pglist gzh pgfirst is err:", e);
			return ResultUtil.serviceException();
		}
	}

}
