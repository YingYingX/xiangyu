package com.chinamobile.hnu.xiangyu.ask.service;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskCommentReply;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-05 16:00:38
 */
public interface IAskCommentReplyService {
	int deleteByPrimaryKey(Long id);

	int insert(AskCommentReply record);

	int insertSelective(AskCommentReply record);

	AskCommentReply selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(AskCommentReply record);

	int updateByPrimaryKey(AskCommentReply record);

}
