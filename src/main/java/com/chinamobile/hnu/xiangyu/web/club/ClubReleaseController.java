/**
 * 
 */
package com.chinamobile.hnu.xiangyu.web.club;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinamobile.hnu.xiangyu.club.service.ClubBaseService;
import com.chinamobile.hnu.xiangyu.club.service.ClubReleaseService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubReleaseVo;
import com.chinamobile.hnu.xiangyu.club.vo.Visibility;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.user.service.UserService;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;

/**
 * @author The Old Man and the Sea
 *
 * 2018年6月6日
 */
@RestController
@RequestMapping("/api/club/release/")
public class ClubReleaseController {
	
	private final static Logger log = LoggerFactory.getLogger(ClubNoticeController.class);

	@Autowired
	private AuthTokenService tokenService;
	
	@Autowired
	private ClubReleaseService releaseService;
	
	@Autowired
	private ClubBaseService clubService;
	
	@Autowired
	private UserService userService;
	
	/***
	 * 最新分页
	 * @param pageCurrent
	 * @param pageSize
	 * @param id
	 * @return
	 */
	@PostMapping("pglist.do")
	public ResponseDto list(@RequestParam(name = "pn", required = true, defaultValue = "0") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "10") Integer pageSize,
			@RequestParam(required = true) Integer id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", user.getId());
		if(id != null){
			map.put("clubId", id);
		}else{
			return ResultUtil.result(2, "团队id不能为空");
		}
		Page<ClubReleaseVo> page = null;
		try {
			Visibility vi = clubService.selectVisibilityId(id);
			if(vi != null){
				map.put("vi",vi);
			}
			page = releaseService.selectByPage(pageCurrent, pageSize, map);
			if(page.getData().size() > 0){
				List<Object> objct = new ArrayList<>();
				for (ClubReleaseVo vo : page.getData()) {
					objct.add(vo.getObj());
				}
				userService.setFriendNickName(objct, user.getId());
			}
			return ResultUtil.result(0, "查询成功", new PageVo(page.getData(), page.getTotalCount(), page.getTotalPage()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("select ClubRelease page Exception:", e);
			return ResultUtil.serviceException();
		}
	}

}
