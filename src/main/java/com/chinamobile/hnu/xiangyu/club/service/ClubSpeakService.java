/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.service;

import java.util.Map;

import com.chinamobile.hnu.xiangyu.club.pojo.ClubSpeak;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubSpeakComment;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubSpeakLike;
import com.chinamobile.hnu.xiangyu.club.vo.ClubSpeakVo;
import com.chinamobile.hnu.xiangyu.club.vo.CommentVo;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.web.api.vo.ClubSpeackInfoVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.ClubSpeackVo;

/**
 * @author The Old Man and the Sea
 *
 *         2018年5月29日
 */
public interface ClubSpeakService {

	/**
	 * 发表团言
	 * 
	 * @param clubSpeak
	 * @param request
	 */
	void insertClubSpeak(ClubSpeak clubSpeak);

	/**
	 * 团言分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<ClubSpeakVo> selectByPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map);

	/**
	 * 发表评论
	 * 
	 * @param speakComment
	 */
	void insertClubSpeakComment(ClubSpeakComment speakComment);

	/**
	 * 查询团言信息
	 * 
	 * @param speakId
	 * @return
	 */
	ClubSpeak selectClubSpeakById(Long speakId);

	/**
	 * 删除团言
	 * 
	 * @param speakId
	 */
	void deleteClubSpeakById(Long speakId);

	/**
	 * 团言分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<CommentVo> selectCommentByPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map);

	/**
	 * 查询团言评论信息
	 * 
	 * @param id
	 * @return
	 */
	ClubSpeakComment selectClubSpeakCommentById(Long id);

	/**
	 * 删除团言的评论
	 * 
	 * @param id
	 */
	void deleteClubSpeakCommentById(Long id,Long sid);

	/**
	 * 他们说的设置
	 * 
	 * @param speakId
	 * @param op
	 */
	void updateClubSpeakItsaid(Long speakId, Integer op);

	/**
	 * 查询用户是否点过赞
	 * 
	 * @param speakId
	 * @param id
	 * @return
	 */
	ClubSpeakLike selectClubSpeakLikeById(Long speakId, Integer uid);

	/**
	 * 更新团言点赞数
	 * 
	 * @param speakId
	 * @param op
	 */
	void updateClubSpeakLike(ClubSpeakLike like, Integer op);

	/**
	 * 团言列表(后台)
	 * 
	 * @param map
	 * @return
	 */
	Page<ClubSpeackVo> selectClubSpeakList(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map);

	/**
	 * 团言详情(后台)
	 * 
	 * @param id
	 * @return
	 */
	ClubSpeackInfoVo selectClubSpeakInfo(Long id);

	/****
	 * 查询团队详情
	 * @param id
	 * @return
	 */
	ClubSpeakVo selectDetailById(Long id);

	/**
	 * 公众号团言分页
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<ClubSpeakVo> selectByGzhPage(Integer pageCurrent, Integer pageSize, Map<String, Object> map);

}
