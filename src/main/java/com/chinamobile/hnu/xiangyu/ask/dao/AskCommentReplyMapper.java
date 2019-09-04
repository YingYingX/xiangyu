package com.chinamobile.hnu.xiangyu.ask.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskCommentReply;
import com.chinamobile.hnu.xiangyu.ask.vo.AskCommentReplyVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.AskComentReplyVo;

public interface AskCommentReplyMapper {

	int deleteByPrimaryKey(Long id);

	int insert(AskCommentReply record);

	int insertSelective(AskCommentReply record);

	AskCommentReply selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(AskCommentReply record);

	int updateByPrimaryKey(AskCommentReply record);

	/**
	 * 根据一级评论id 分页查询二级评论 xx
	 * 
	 * @param map
	 * @return
	 */
	List<AskCommentReplyVo> selectReplyByFirstId(Map<String, Object> map);

	/**
	 * 统计一级评论数量
	 * 
	 * @param commentId
	 * @return
	 */
	int selectCountByCId(Long commentId);

	/**
	 * @param id
	 */
	int deleteByCommentId(Long id);

	/**
	 * 根据问问id查询二级评论
	 * 
	 * @param keySet
	 * @return
	 */
	List<AskComentReplyVo> selectBycomentId(@Param("set") Set<Long> keySet);
}