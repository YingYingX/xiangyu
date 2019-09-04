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

import com.chinamobile.hnu.xiangyu.club.service.TeamBuildService;
import com.chinamobile.hnu.xiangyu.club.vo.CommentVo;
import com.chinamobile.hnu.xiangyu.club.vo.TeamBuildVo;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.user.service.UserService;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * @author The Old Man and the Sea
 *
 * 2018年5月30日
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/gzh/club/tb/")
public class GTeamBuildController {
	
	private final static Logger log = LoggerFactory.getLogger(GClubSpeakController.class);
	
	@Autowired
	private AuthTokenService tokenService;
	
	@Autowired
	private TeamBuildService buildService;
	
	@Autowired
	private UserService userService;
	
	
	/***
	 * 分页查询团建
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
		if(type == 1){
			map.put("type", type);
		}
			map.put("clubId", id);
		Page<TeamBuildVo> page = null;
		try {
			page = buildService.selectByGzhPage(pageCurrent,pageSize,map);
			return ResultUtil.result(0, "查询成功",new PageVo(page.getData(),page.getTotalCount(),page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select gzh ClubTeamBuilding page Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/****
	 * 查询团建详情
	 * @param id
	 * @return
	 */
	@PostMapping("detail.do")
	public ResponseDto detail(@RequestParam Long id) {
		try {
			TeamBuildVo vo = buildService.selectGzhDetailById(id);
			return ResultUtil.result(0, "查询成功",vo);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select gzh detail Exception:",e);
			return ResultUtil.serviceException();
		}
	}
	
	
	/***
	 * 团建评论分页
	 * @param pageCurrent
	 * @param pageSize
	 * @param tbId
	 * @return
	 */
	@PostMapping("pgcomment.do")
	public ResponseDto listComment(
			@RequestParam Integer pageCurrent,
			@RequestParam Integer pageSize,
			@RequestParam(required = true) Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tbId", id);
		Page<CommentVo> page = null;
		try {
			page = buildService.selectCommentByPage(pageCurrent,pageSize,map);
			return ResultUtil.result(0, "查询成功",new PageVo(page.getData(),page.getTotalCount(),page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select gzh ClubTeamBuildingComment page Exception:",e);
			return ResultUtil.serviceException();
		}
	}
}
