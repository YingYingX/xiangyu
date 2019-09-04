/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.service;

import java.util.List;
import java.util.Map;

import com.chinamobile.hnu.xiangyu.club.pojo.ClubActivity;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubActivityApplicant;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubActivityComment;
import com.chinamobile.hnu.xiangyu.club.vo.ClubActivityVo;
import com.chinamobile.hnu.xiangyu.club.vo.CommentVo;
import com.chinamobile.hnu.xiangyu.club.vo.LikeVo;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.web.api.vo.AclubActivityInfoVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.AclubActivityVo;

/**
 * @author The Old Man and the Sea
 *
 *         2018年6月1日
 */
public interface ClubActivityService {

	/**
	 * 新增团队活动
	 * 
	 * @param clubActivity
	 * @param photos
	 */
	void insertClubActivity(ClubActivity clubActivity);

	/**
	 * 修改团队活动
	 * 
	 * @param clubActivity
	 * @param photos
	 * @param delFileIds
	 */
	void updateClubActivity(ClubActivity clubActivity, String delFileIds);

	/**
	 * 评论活动
	 * 
	 * @param clubActivityComment
	 */
	void insertClubActivityComment(ClubActivityComment clubActivityComment);

	/**
	 * 查询活动信息
	 * 
	 * @param id
	 * @return
	 */
	ClubActivity selectClubActivityById(Long id);

	/**
	 * 删除活动
	 * 
	 * @param id
	 */
	void deleteClubActivityById(Long id);

	/**
	 * 查询活动评论
	 * 
	 * @param id
	 * @return
	 */
	ClubActivityComment selectClubActivityCommentById(Long id);

	/**
	 * 删除活动评论
	 * 
	 * @param id
	 */
	void deleteClubActivityCommentById(Long id,Long aid);

	/**
	 * 查询用户是否参加过活动
	 * 
	 * @param id
	 * @param id2
	 * @return
	 */
	ClubActivityApplicant selectClubActivityApplicantById(Long activityId,
			Integer uid);

	/**
	 * 报名/取消报名活动
	 * 
	 * @param applicant
	 * @param op
	 */
	void updateClubActivityApplicant(ClubActivityApplicant applicant, Integer op);

	/**
	 * 活动分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<ClubActivityVo> selectByPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map);

	/**
	 * 查询活动评论分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<CommentVo> selectCommentByPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map);

	/**
	 * 查询活动详情
	 * 
	 * @param id
	 * @return
	 */
	ClubActivityVo selectClubActivityDetailById(Long id, Integer uid);

	/****
	 * 查询热门活动
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<ClubActivityVo> selectHotByPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map);

	/**
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<AclubActivityVo> selectActivityList(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map);

	/**
	 * @param id
	 * @return
	 */
	AclubActivityInfoVo selectActivityInfo(Long id);

	/****
	 * 查询活动参与人员
	 * @param id
	 * @return
	 */
	List<LikeVo> selectClubActivityMember(Long id);

	/**
	 * 公众号热门活动
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<ClubActivityVo> selectHotByGzhPage(Integer pageCurrent, Integer pageSize, Map<String, Object> map);

	/**
	 * 公众号活动详情
	 * @param id
	 * @return
	 */
	ClubActivityVo selectGzhClubActivityDetailById(Long id) throws Exception;

}
