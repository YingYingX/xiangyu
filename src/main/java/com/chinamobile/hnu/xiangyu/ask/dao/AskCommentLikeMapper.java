package com.chinamobile.hnu.xiangyu.ask.dao;

import java.util.List;
import java.util.Map;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskCommentLike;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubSpeakLike;

public interface AskCommentLikeMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table fdasfaclub_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int deleteByPrimaryKey(AskCommentLike key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int insert(AskCommentLike record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int insertSelective(AskCommentLike record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	ClubSpeakLike selectByPrimaryKey(AskCommentLike key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int updateByPrimaryKeySelective(AskCommentLike record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int updateByPrimaryKey(AskCommentLike record);

	/**
	 * 查询我是否点过赞xx
	 * 
	 * @param userId
	 * @param askId
	 * @return
	 */
	AskCommentLike selectBycommentId(Map<String, Object> map);

	/**
	 * 查询指定问问点赞数xx
	 * 
	 * @param askId
	 * @return
	 */
	int selectCountBycommentId(Long commentId);

	/**
	 * 查询我点过的赞
	 * 
	 * @param userId
	 * @return
	 */
	List<AskCommentLike> selectByUserId(Integer userId);
}