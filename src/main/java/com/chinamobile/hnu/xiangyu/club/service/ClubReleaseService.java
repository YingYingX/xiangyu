/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.service;

import java.util.Map;

import com.chinamobile.hnu.xiangyu.club.vo.ClubReleaseVo;
import com.chinamobile.hnu.xiangyu.util.dto.Page;

/**
 * @author The Old Man and the Sea
 *
 * 2018年6月6日
 */
public interface ClubReleaseService {

	/**
	 * 最新分页
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<ClubReleaseVo> selectByPage(Integer pageCurrent, Integer pageSize, Map<String, Object> map);

}
