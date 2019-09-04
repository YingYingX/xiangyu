/**
 * 
 */
package com.chinamobile.hnu.xiangyu.web.gzh.club;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinamobile.hnu.xiangyu.club.pojo.ClubInfo;
import com.chinamobile.hnu.xiangyu.club.service.ClubActivityService;
import com.chinamobile.hnu.xiangyu.club.service.ClubBaseService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubActivityVo;
import com.chinamobile.hnu.xiangyu.club.vo.CommentVo;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * @author The Old Man and the Sea
 *
 * 2018年6月1日
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/gzh/club/activity/")
public class GClubActivityController {

	private final static Logger log = LoggerFactory.getLogger(GClubSpeakController.class);
	
	@Autowired
	private ClubActivityService activityService;
	
	@Autowired
	private ClubBaseService baseService;
	
	
	/****
	 * 热门活动分页
	 * @param pageCurrent
	 * @param pageSize
	 * @return
	 */
	@PostMapping("hotlist.do")
	public ResponseDto hotlist(
			@RequestParam Integer pageCurrent,
			@RequestParam Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hot", 1);
		Page<ClubActivityVo> page = null;
		try {
			page = activityService.selectHotByGzhPage(pageCurrent,pageSize,map);
			return ResultUtil.result(0, "查询成功",new PageVo(page.getData(),page.getTotalCount(),page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select gzh hot ClubActivity page Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/***
	 * 活动详情
	 * @param id
	 * @return
	 */
	@PostMapping("detail.do")
	public ResponseDto detail(@RequestParam(required = true) Long id) {
		
		ClubActivityVo vo = null; 
		try {
			vo = activityService.selectGzhClubActivityDetailById(id);
			ClubInfo clubInfo = baseService.selectClubInfoById(vo.getClubId());
			vo.setCollege(clubInfo == null?"":clubInfo.getCollege());
			return ResultUtil.result(0, "查询成功", vo);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select gzh ClubActivity detail Exception:", e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/***
	 * 活动评论分页
	 * @param pageCurrent
	 * @param pageSize
	 * @param id
	 * @return
	 */
	@PostMapping("pgcomment.do")
	public ResponseDto listComment(
			@RequestParam Integer pageCurrent,
			@RequestParam Integer pageSize,
			@RequestParam(required = true) Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityId", id);
		Page<CommentVo> page = null;
		try {
			page = activityService.selectCommentByPage(pageCurrent,pageSize,map);
			return ResultUtil.result(0, "查询成功",new PageVo(page.getData(),page.getTotalCount(),page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select gzh ClubActivityComment page Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	
	
}
