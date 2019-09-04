/**  

 * <p>Title: SysUserNewsController.java</p>  

 * <p>Description: </p>  

 * <p>Copyright: Copyright douziit(c) 2017</p>  

 * @author lh  

 * @date 2018年6月29日  

 * @version 1.0  

 */
package com.chinamobile.hnu.xiangyu.web.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinamobile.hnu.xiangyu.common.pojo.SysUserNews;
import com.chinamobile.hnu.xiangyu.common.service.ISysUserNewsService;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;
import com.chinamobile.hnu.xiangyu.user.service.UserService;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.PageVo;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;
import com.chinamobile.hnu.xiangyu.web.api.vo.NewsVo;

/**
 * 
 * <p>
 * Title: SysUserNewsController.java
 * </p>
 * 
 * <p>
 * Description: 通知
 * </p>
 * 
 * @author lh
 * 
 * @date 2018年6月29日
 * 
 * @version 1.0
 */

@RestController
@RequestMapping("/api/user/news/")
public class SysUserNewsController {
	private final Logger logger = Logger.getLogger(SysUserNewsController.class);

	@Autowired
	private ISysUserNewsService userNewsService;

	@Autowired
	private AuthTokenService tokenService;
	
	@Autowired
	private UserService userService;

	/**
	 * 通知列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param type
	 *            :1-团队消息；2-团言消息；3-问题消息；
	 * @return
	 */
	@PostMapping("pglist.do")
	public ResponseDto pglist(
			@RequestParam(name = "pn", required = true, defaultValue = "1") Integer pageCurrent,
			@RequestParam(name = "ps", required = true, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "type", required = true) Integer type) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("receiveId", user.getId());
		try {
			Page<NewsVo> page = userNewsService.selectListPageNews(pageCurrent,
					pageSize, map, user.getId());
			userService.setFriendNickNameAsk(page.getData(), user.getId());
			return ResultUtil.result(
					0,
					"查询成功",
					new PageVo(page.getData(), page.getTotalCount(), page
							.getTotalPage()));
		} catch (Exception e) {
			logger.error("sysNews pglist is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 清空所有消息
	 * 
	 * @param type
	 * @return
	 */
	@PostMapping("clearBatchNews.do")
	public ResponseDto clearBatchNews(
			@RequestParam(name = "type", required = true) Integer type) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("userId", user.getId());
		try {
			userNewsService.updateNewsById(map);
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			logger.error("clearBatchNews pglist is err:", e);
			return ResultUtil.serviceException();
		}
	}

	/**
	 * 清空指定消息
	 * 
	 * @param id
	 *            ：消息id
	 * @return
	 */
	@PostMapping("clearNews.do")
	public ResponseDto clearNews(
			@RequestParam(name = "id", required = true) Integer id) {
		UserAccount user = tokenService.getLoginUser();
		if (user == null) {
			return ResultUtil.unLogin();
		}
		try {
			SysUserNews record = new SysUserNews();
			record.setStatus(9);
			record.setId(id);
			userNewsService.updateByPrimaryKeySelective(record);
			return ResultUtil.result(0, "操作成功");
		} catch (Exception e) {
			logger.error("clearBatchNews pglist is err:", e);
			return ResultUtil.serviceException();
		}
	}
}
