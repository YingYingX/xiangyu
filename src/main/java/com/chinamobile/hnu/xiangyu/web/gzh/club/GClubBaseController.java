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

import com.chinamobile.hnu.xiangyu.club.service.ClubBaseService;
import com.chinamobile.hnu.xiangyu.club.service.ClubMemberService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubVo;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.user.service.UserService;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * @author The Old Man and the Sea
 *
 *         2018年5月28日
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/gzh/club/base/")
public class GClubBaseController {

	private static Logger log = LoggerFactory.getLogger(GClubBaseController.class);



	@Autowired
	private AuthTokenService tokenService;

	@Autowired
	private ClubBaseService baseService;

	@Autowired
	private UserService userService;

	@Autowired
	private ClubMemberService memberService;
	
	
	/****
	 * 分页查询团队列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param type
	 *            1：热门团队；2：最新的团队；3：我的团队；4：收藏;5.推荐的团队
	 * @return
	 */
	@PostMapping("pglist.do")
	public ResponseDto list(@RequestParam Integer pageCurrent,
			@RequestParam Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		Page<ClubVo> page = null;
		try {
			page = baseService.selectByGzhPage(pageCurrent, pageSize, map);
			return ResultUtil.result(0, "查询成功",new PageVo(page.getData(),page.getTotalCount(),page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select gzh clubInfo page Exception:", e);
			return ResultUtil.serviceException();
		}
	}


	/****
	 * 查询团队详情信息
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("detail.do")
	public ResponseDto detail(@RequestParam Integer id) {

		try {
			ClubVo vo = baseService.selectGzhDetailById(id);
			return ResultUtil.result(0, "查询成功", vo);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select gzh clubInfo detail Exception:", e);
			return ResultUtil.serviceException();
		}
	}
}
