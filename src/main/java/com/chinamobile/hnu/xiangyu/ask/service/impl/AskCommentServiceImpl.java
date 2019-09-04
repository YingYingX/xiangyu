package com.chinamobile.hnu.xiangyu.ask.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.ask.dao.AskCommentMapper;
import com.chinamobile.hnu.xiangyu.ask.dao.AskCommentReplyMapper;
import com.chinamobile.hnu.xiangyu.ask.dao.AskInfoMapper;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskComment;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskInfo;
import com.chinamobile.hnu.xiangyu.ask.service.IAskCommentService;
import com.chinamobile.hnu.xiangyu.util.Utils;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.web.api.vo.AskComentReplyVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.AskComentVo;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-05 16:00:37
 */
@Service("askCommentService")
public class AskCommentServiceImpl implements IAskCommentService {

	private final Logger logger = Logger.getLogger(AskCommentServiceImpl.class);
	@Autowired
	protected AskCommentMapper mapper;

	@Autowired
	protected AskInfoMapper askInfomapper;

	@Autowired
	protected AskCommentReplyMapper commentReplyMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskCommentService#deleteByPrimaryKey
	 * (java.lang.Long)
	 */
	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskCommentService#insert(com
	 * .chinamobile.hnu.xiangyu.ask.pojo.AskComment)
	 */
	@Override
	public int insert(AskComment record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskCommentService#insertSelective
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskComment)
	 */
	@Override
	public int insertSelective(AskComment record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskCommentService#selectByPrimaryKey
	 * (java.lang.Long)
	 */
	@Override
	public AskComment selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskCommentService#
	 * updateByPrimaryKeySelective
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskComment)
	 */
	@Override
	public int updateByPrimaryKeySelective(AskComment record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskCommentService#updateByPrimaryKey
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskComment)
	 */
	@Override
	public int updateByPrimaryKey(AskComment record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskCommentService#deleteCommentById
	 * (java.lang.Long)
	 */
	@Override
	public int deleteCommentById(Long id) {
		AskComment comment = mapper.selectByPrimaryKey(id);
		mapper.deleteByPrimaryKey(id);
		commentReplyMapper.deleteByCommentId(id);
		// 更新问问的评论数
		if (comment.getAskId() != null) {
			int commentAmount = mapper.selectCountByAskId(comment.getAskId());
			AskInfo info = new AskInfo();
			info.setCommentAmount(commentAmount);
			info.setGmtModified(new Date());
			info.setId(id);
			askInfomapper.updateByPrimaryKeySelective(info);
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskCommentService#
	 * selectAskComentByAskId(java.lang.Integer, java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public Page<AskComentVo> selectAskComentByAskId(Integer pageCurrent,
			Integer pageSize, Integer askId) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<AskComentVo> vo = mapper.selectAskCommentByAskId(askId);

		if (vo.size() > 0) {
			Map<Long, AskComentVo> voMap = new HashMap<>();
			for (AskComentVo askComentVo : vo) {
				voMap.put(askComentVo.getId(), askComentVo);
				askComentVo
						.setTime(Utils.formatAgo(askComentVo.getGmtCreate()));
				askComentVo.setNsw("");
			}

			// 二级评论
			List<AskComentReplyVo> replyVo = commentReplyMapper
					.selectBycomentId(voMap.keySet());
			if (replyVo.size() > 0) {
				for (AskComentReplyVo askComentReplyVo : replyVo) {
					askComentReplyVo.setTime(Utils.formatAgo(askComentReplyVo
							.getGmtCreate()));

					List<AskComentReplyVo> comm = voMap.get(
							askComentReplyVo.getCommentId())
							.getAskComentReplyVos();
					if (comm == null) {
						comm = new ArrayList<AskComentReplyVo>();
						comm.add(askComentReplyVo);
						voMap.get(askComentReplyVo.getCommentId())
								.setAskComentReplyVos(comm);
					} else {
						comm.add(askComentReplyVo);
					}
				}
			}
		}
		return new Page<AskComentVo>(vo);
	}

}
