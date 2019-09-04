/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.service;

import java.util.Map;

import com.chinamobile.hnu.xiangyu.club.pojo.ClubTeamBuilding;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubTeamBuildingComment;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubTeamBuildingLike;
import com.chinamobile.hnu.xiangyu.club.vo.CommentVo;
import com.chinamobile.hnu.xiangyu.club.vo.TeamBuildVo;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.web.api.vo.TeamBuildingVo;

/**
 * @author The Old Man and the Sea
 *
 *         2018年5月31日
 */
public interface TeamBuildService {

	/**
	 * 发布团建
	 * 
	 * @param clubTeamBuild
	 * @param photos
	 */
	void insertClubTeamBuilding(ClubTeamBuilding clubTeamBuild);

	/**
	 * 修改团建内容
	 * 
	 * @param clubTeamBuild
	 * @param photos
	 * @param delFileIds
	 */
	void updateClubTeamBuilding(ClubTeamBuilding clubTeamBuild,
			String delFileIds);

	/**
	 * 发表团建评论
	 * 
	 * @param buildComment
	 */
	void insertTeamBuildComment(ClubTeamBuildingComment buildComment);

	/**
	 * @param tbId
	 * @return
	 */
	ClubTeamBuilding selectClubTeamBuildingById(Long tbId);

	/**
	 * @param tbId
	 */
	void deleteClubTeamBuildingById(Long tbId);

	/**
	 * 查询团建评论
	 * 
	 * @param id
	 * @return
	 */
	ClubTeamBuildingComment selectClubTeamBuildingCommentById(Long id);

	/**
	 * 删除团建评论
	 * 
	 * @param id
	 */
	void deleteClubTeamBuildingCommentById(Long id,Long tid);

	/**
	 * 推荐团建到大厅
	 * 
	 * @param tbId
	 * @param op
	 */
	void updateClubTeamBuildingRecommend(Long tbId, Integer op);

	/**
	 * 查询用户是否点赞
	 * 
	 * @param tbId
	 * @param id
	 * @return
	 */
	ClubTeamBuildingLike selectClubTeamBuildingLikeById(Long tbId, Integer uid);

	/**
	 * 点赞或取消点赞
	 * 
	 * @param like
	 * @param op
	 */
	void updateClubTeamBuildingLike(ClubTeamBuildingLike like, Integer op);

	/**
	 * 团建分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<TeamBuildVo> selectByPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map);

	/**
	 * 团建评论分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<CommentVo> selectCommentByPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map);

	/**
	 * 团建分页（后台）
	 * 
	 * @param map
	 * @return
	 */
	Page<TeamBuildingVo> selectTbPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map);

	/**
	 * 团建详情(后台)
	 * 
	 * @param id
	 * @return
	 */
	TeamBuildingVo selectById(Integer id);

	/**
	 * 团建公众号分页
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<TeamBuildVo> selectByGzhPage(Integer pageCurrent, Integer pageSize, Map<String, Object> map);

	/**
	 * 公众号查询团建详情
	 * @param id
	 * @return
	 */
	TeamBuildVo selectGzhDetailById(Long id) throws Exception;
}
