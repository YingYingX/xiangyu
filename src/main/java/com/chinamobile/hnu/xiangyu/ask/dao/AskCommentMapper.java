package com.chinamobile.hnu.xiangyu.ask.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskComment;
import com.chinamobile.hnu.xiangyu.ask.vo.AskCommentVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.AskComentVo;

public interface AskCommentMapper {

	int deleteByPrimaryKey(Long id);

	int insert(AskComment record);

	int insertSelective(AskComment record);

	AskComment selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(AskComment record);

	int updateByPrimaryKey(AskComment record);

	/**
	 * 分页查询
	 * 
	 * @param map
	 * @return
	 */
	List<AskCommentVo> selectAskComment(Map<String, Object> map);

	/**
	 * 分页查询二级评论xx
	 * 
	 * @param keySet
	 * @return
	 */
	List<AskCommentVo> selectByAskIdList(@Param("set") Set<Long> keySet);

	/**
	 * 一级问题详情
	 * 
	 * @param map
	 * @return
	 */
	AskCommentVo selectCommentById(Map<String, Object> map);

	/**
	 * 统计问题评论数量
	 * 
	 * @param askid
	 * @return
	 */
	int selectCountByAskId(Long askid);

	/**
	 * 查询我是否点过赞
	 * 
	 * @param userId
	 * @param askId
	 * @return
	 */
	AskComment selectById(Map<String, Object> map);

	/**
	 * 分页查询问问一级二级评论(后台)
	 * 
	 * @param askId
	 * @return
	 */
	List<AskComentVo> selectAskCommentByAskId(Integer askId);

	/**
	 * 更新一级评论数 -1
	 * 
	 * @param commentId
	 */
	void updateAskCommentSpeak(Long commentId);
}