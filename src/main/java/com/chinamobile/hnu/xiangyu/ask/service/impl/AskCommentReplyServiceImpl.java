package com.chinamobile.hnu.xiangyu.ask.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.ask.dao.AskCommentMapper;
import com.chinamobile.hnu.xiangyu.ask.dao.AskCommentReplyMapper;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskCommentReply;
import com.chinamobile.hnu.xiangyu.ask.service.IAskCommentReplyService;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-05 16:00:38
 */
@Service("askCommentReplyService")
public class AskCommentReplyServiceImpl implements IAskCommentReplyService {

	private final Logger logger = Logger
			.getLogger(AskCommentReplyServiceImpl.class);
	@Autowired
	protected AskCommentReplyMapper mapper;

	@Autowired
	protected AskCommentMapper commentMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskCommentReplyService#
	 * deleteByPrimaryKey(java.lang.Long)
	 */
	@Override
	public int deleteByPrimaryKey(Long id) {
		AskCommentReply reply = mapper.selectByPrimaryKey(id);
		mapper.deleteByPrimaryKey(id);
		// 更新一级评论的评论数
		if (reply.getCommentId() != null) {
			commentMapper.updateAskCommentSpeak(reply.getCommentId());
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskCommentReplyService#insert
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskCommentReply)
	 */
	@Override
	public int insert(AskCommentReply record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskCommentReplyService#
	 * insertSelective(com.chinamobile.hnu.xiangyu.ask.pojo.AskCommentReply)
	 */
	@Override
	public int insertSelective(AskCommentReply record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskCommentReplyService#
	 * selectByPrimaryKey(java.lang.Long)
	 */
	@Override
	public AskCommentReply selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskCommentReplyService#
	 * updateByPrimaryKeySelective
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskCommentReply)
	 */
	@Override
	public int updateByPrimaryKeySelective(AskCommentReply record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskCommentReplyService#
	 * updateByPrimaryKey(com.chinamobile.hnu.xiangyu.ask.pojo.AskCommentReply)
	 */
	@Override
	public int updateByPrimaryKey(AskCommentReply record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

}
