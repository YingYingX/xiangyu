/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.service;

import java.util.Map;

import com.chinamobile.hnu.xiangyu.club.pojo.ClubNotice;
import com.chinamobile.hnu.xiangyu.club.vo.ClubNoticeVo;
import com.chinamobile.hnu.xiangyu.util.dto.Page;

/**
 * @author The Old Man and the Sea
 *
 * 2018年6月5日
 */
public interface ClubNoticeService {

	/**
	 * 发布公告
	 * @param clubNotice
	 */
	void insertClubNotice(ClubNotice clubNotice);

	/**
	 * 修改公告
	 * @param clubNotice
	 */
	void updateClubNotice(ClubNotice clubNotice);

	/**
	 * 查询公告
	 * @param id
	 * @return
	 */
	ClubNotice selectClubNoticeById(Long id);

	/**
	 * 删除公告
	 * @param id
	 */
	void deleteClubNoticeById(Long id);

	/**
	 * 公告分页
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<ClubNoticeVo> selectByPage(Integer pageCurrent, Integer pageSize, Map<String, Object> map);

}
