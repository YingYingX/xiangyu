/**
 * 
 */
package com.chinamobile.hnu.xiangyu.web.gzh.club;

import java.util.ArrayList;
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

import com.chinamobile.hnu.xiangyu.club.service.ClubBaseService;
import com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubSpeakVo;
import com.chinamobile.hnu.xiangyu.club.vo.CommentVo;
import com.chinamobile.hnu.xiangyu.club.vo.Visibility;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * @author The Old Man and the Sea
 *
 * 2018年5月29日
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/gzh/club/speak/")
public class GClubSpeakController {
	
	private final static Logger log = LoggerFactory.getLogger(GClubSpeakController.class);
	
	@Autowired
	private ClubSpeakService speakService;
	
	@Autowired
	private ClubBaseService clubService;
	
	/***
	 * 团言分页
	 * @param pageCurrent
	 * @param pageSize
	 * @param type
	 * @param id
	 * @return
	 */
	@PostMapping("pglist.do")
	public ResponseDto list(
			@RequestParam Integer pageCurrent,
			@RequestParam Integer pageSize,
			@RequestParam Integer type,
			@RequestParam Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("clubId", id);
		Page<ClubSpeakVo> page = null;
		try {
			Visibility vi = clubService.selectVisibilityId(id);
			if(vi.getSettingSpeak().intValue() == 1){
				return ResultUtil.result(0, "查询成功",new PageVo(new ArrayList<>(),0L,0));
			}
			page = speakService.selectByGzhPage(pageCurrent,pageSize,map);
			return ResultUtil.result(0, "查询成功",new PageVo(page.getData(),page.getTotalCount(),page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select gzh clubSpeak page Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	
	/****
	 * 团言详情
	 * @param id
	 * @return
	 */
	@PostMapping("detail.do")
	public ResponseDto detail(@RequestParam Long id) {
		try {
			ClubSpeakVo vo = speakService.selectDetailById(id);
			return ResultUtil.result(0, "查询成功",vo);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select gzh clubSpeak detail Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	
	
	/***
	 * 团言评论分页
	 * @param pageCurrent
	 * @param pageSize
	 * @param speakId
	 * @return
	 */
	@PostMapping("pgcomment.do")
	public ResponseDto listComment(
			@RequestParam(name = "pn", required = true,defaultValue="0") Integer pageCurrent,
			@RequestParam(name = "ps", required = true,defaultValue="10") Integer pageSize,
			@RequestParam(required = true) Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("speakId", id);
		Page<CommentVo> page = null;
		try {
			page = speakService.selectCommentByPage(pageCurrent,pageSize,map);
			return ResultUtil.result(0, "查询成功",new PageVo(page.getData(),page.getTotalCount(),page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select gzh ClubSpeakComment page Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	
	
	
	
}
