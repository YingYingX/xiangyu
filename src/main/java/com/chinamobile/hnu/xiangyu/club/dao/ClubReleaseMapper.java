/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinamobile.hnu.xiangyu.club.pojo.ClubRelease;
import com.chinamobile.hnu.xiangyu.club.vo.ClubReleaseVo;

/**
 * @author The Old Man and the Sea
 *
 * 2018年6月6日
 */
public interface ClubReleaseMapper {

	/**
	 * 新增发布
	 * @param release
	 */
	void insert(ClubRelease release);

	/**
	 * 删除发布
	 * @param voteId
	 * @param i
	 */
	void delete(@Param("bzid")Long bzid, @Param("type")int type);
	
	
	/***
	 * 发布分页
	 * @param map
	 * @return
	 */
	List<ClubReleaseVo> selectByPage(Map<String,Object> map);

}
