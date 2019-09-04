package com.chinamobile.hnu.xiangyu.ask.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskSpeakLike;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubSpeakLike;
import com.chinamobile.hnu.xiangyu.club.vo.LikeVo;

public interface AskSpeakLikeMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int deleteByPrimaryKey(AskSpeakLike key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int insert(AskSpeakLike record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int insertSelective(AskSpeakLike record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	ClubSpeakLike selectByPrimaryKey(AskSpeakLike key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int updateByPrimaryKeySelective(AskSpeakLike record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_like
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int updateByPrimaryKey(AskSpeakLike record);

	/**
	 * 查询团言点赞
	 * 
	 * @param integer
	 * @return
	 */
	List<LikeVo> selectBySpeakIdList(@Param("set") Set<Long> keySet);

	/**
	 * 查询我是否点过赞
	 * 
	 * @param userId
	 * @param askId
	 * @return
	 */
	AskSpeakLike selectByAskId(Map<String, Object> map);

	/**
	 * 查询指定问问点赞数xx
	 * 
	 * @param askId
	 * @return
	 */
	int selectCountByAskId(Long askId);

	/**
	 * 查询我点过的赞
	 * 
	 * @param userId
	 * @return
	 */
	List<AskSpeakLike> selectByUserId(Integer userId);
	
	/***
	 * 查询问问点赞者信息
	 * @param id
	 * @return
	 */
	List<LikeVo> selectVoByAskId(Long id);
	
}