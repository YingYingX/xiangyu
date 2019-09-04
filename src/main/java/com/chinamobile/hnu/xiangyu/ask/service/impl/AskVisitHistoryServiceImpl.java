package com.chinamobile.hnu.xiangyu.ask.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.ask.dao.AskVisitHistoryMapper;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskVisitHistory;
import com.chinamobile.hnu.xiangyu.ask.service.IAskVisitHistoryService;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-05 16:00:38
 */
@Service("askVisitHistoryService")
public class AskVisitHistoryServiceImpl implements IAskVisitHistoryService {

	private final Logger logger = Logger
			.getLogger(AskVisitHistoryServiceImpl.class);
	@Autowired
	protected AskVisitHistoryMapper mapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskVisitHistoryService#
	 * deleteByPrimaryKey(java.lang.Long)
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
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskVisitHistoryService#insert
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskVisitHistory)
	 */
	@Override
	public int insert(AskVisitHistory record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskVisitHistoryService#
	 * insertSelective(com.chinamobile.hnu.xiangyu.ask.pojo.AskVisitHistory)
	 */
	@Override
	public int insertSelective(AskVisitHistory record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskVisitHistoryService#
	 * selectByPrimaryKey(java.lang.Long)
	 */
	@Override
	public AskVisitHistory selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskVisitHistoryService#
	 * updateByPrimaryKeySelective
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskVisitHistory)
	 */
	@Override
	public int updateByPrimaryKeySelective(AskVisitHistory record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskVisitHistoryService#
	 * updateByPrimaryKey(com.chinamobile.hnu.xiangyu.ask.pojo.AskVisitHistory)
	 */
	@Override
	public int updateByPrimaryKey(AskVisitHistory record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

}
