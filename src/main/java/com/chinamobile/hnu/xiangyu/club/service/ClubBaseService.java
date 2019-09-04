/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.service;

import java.util.List;
import java.util.Map;

import com.chinamobile.hnu.xiangyu.club.pojo.ClubInfo;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubMember;
import com.chinamobile.hnu.xiangyu.club.vo.ClubVo;
import com.chinamobile.hnu.xiangyu.club.vo.Visibility;
import com.chinamobile.hnu.xiangyu.common.pojo.SysUserNews;
import com.chinamobile.hnu.xiangyu.user.pojo.UserFavorite;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.web.api.vo.AclubInfoVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.AclubVo;

/**
 * @author The Old Man and the Sea
 *
 *         2018年5月28日
 */
public interface ClubBaseService {

	/**
	 * 新增团队信息
	 * 
	 * @param clubInfo
	 */
	void insertClubInfo(ClubInfo clubInfo);

	/**
	 * 修改团队信息
	 * 
	 * @param clubInfo
	 */
	void updateClubInfo(ClubInfo clubInfo);

	/**
	 * 查询团队详情信息
	 * 
	 * @param id
	 * @return
	 */
	ClubVo selectDetailById(Integer id, Integer uid) throws Exception;


	/**
	 * 收藏/取消收藏团队
	 * 
	 * @param favorite
	 * @param op
	 *            1.收藏；2.取消收藏
	 */
	void favorite(UserFavorite favorite, Integer op);


	/**
	 * 查询团队可见基本信息
	 * 
	 * @param id
	 * @return
	 */
	Visibility selectVisibilityId(Integer id) throws Exception;

	/**
	 * 修改团队可见条件
	 * 
	 * @param vi
	 */
	void insertVisibility(Visibility vi) throws Exception;

	/**
	 * 查询团队分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<ClubVo> selectByPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map);

	/**
	 * 
	 * 查询用户是否收藏过该团队
	 * 
	 * @param id
	 * @param id2
	 * @return
	 */
	UserFavorite selectFavoriteByClubId(Integer clubId, Integer uid);

	/**
	 * 修改团队昨日访问量
	 * 
	 */
	void updateClubYesterdayTraffic();


	/**
	 * 查询团队信息
	 * 
	 * @param clubId
	 * @return
	 */
	ClubInfo selectClubInfoById(Integer clubId);


	/**
	 * 后台 团队列表
	 * 
	 * @param map
	 * @return
	 */
	Page<AclubVo> selectByclubList(Map<String, Object> map,
			Integer pageCurrent, Integer pageSize);

	/**
	 * 查询团队详情（后台）
	 * 
	 * @param clubId
	 * @return
	 */
	AclubInfoVo selectClubInfoByclubId(Integer clubId);

	/**
	 * 申请加入团队，保存到通知表
	 * 
	 * @param userNews
	 */
	void savaSysUserNews(SysUserNews userNews, Integer id,
			Map<String, Object> map);

	/**
	 * 通过标签查询团队
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<ClubVo> selectByLabelPage(Integer pageCurrent, Integer pageSize, Map<String, Object> map);

	/**
	 * 公众号团队分页
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<ClubVo> selectByGzhPage(Integer pageCurrent, Integer pageSize, Map<String, Object> map);

	/**
	 * 公众号查询团队详情
	 * @param id
	 * @return
	 */
	ClubVo selectGzhDetailById(Integer id) throws Exception;

	/**
	 * 查询学校社团
	 * @param params
	 * @return
	 */
	List<ClubInfo> selectClubInfoByMap(Map<String, Object> params);

}
