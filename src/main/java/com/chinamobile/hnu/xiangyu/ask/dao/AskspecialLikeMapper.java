package com.chinamobile.hnu.xiangyu.ask.dao;

import java.util.List;
import java.util.Map;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskspecialLike;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubSpeakLike;

public interface AskspecialLikeMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int deleteByPrimaryKey(AskspecialLike key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int insert(AskspecialLike record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int insertSelective(AskspecialLike record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	ClubSpeakLike selectByPrimaryKey(AskspecialLike key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int updateByPrimaryKeySelective(AskspecialLike record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int updateByPrimaryKey(AskspecialLike record);

	/**
	 * 查询我是否点过赞
	 * 
	 * @param userId
	 * @param askId
	 * @return
	 */
	AskspecialLike selectByspecialId(Map<String, Object> map);

	/**
	 * 查询指定问问点赞数xx
	 * 
	 * @param askId
	 * @return
	 */
	int selectCountBySpecialId(Integer specialId);

	/**
	 * 查询我点过的赞
	 * 
	 * @param userId
	 * @return
	 */
	List<AskspecialLike> selectByUserId(Integer userId);
}