package com.chinamobile.hnu.xiangyu.ask.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.ask.dao.AskLabelMapper;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskLabel;
import com.chinamobile.hnu.xiangyu.ask.service.IAskLabelService;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-05 16:00:38
 */
@Service("askLabelService")
public class AskLabelServiceImpl implements IAskLabelService {

	private final Logger logger = Logger.getLogger(AskLabelServiceImpl.class);
	@Autowired
	protected AskLabelMapper mapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskLabelService#deleteByPrimaryKey
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
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskLabelService#insert(com.
	 * chinamobile.hnu.xiangyu.ask.pojo.AskLabel)
	 */
	@Override
	public int insert(AskLabel record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskLabelService#insertSelective
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskLabel)
	 */
	@Override
	public int insertSelective(AskLabel record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskLabelService#selectByPrimaryKey
	 * (java.lang.Long)
	 */
	@Override
	public AskLabel selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskLabelService#
	 * updateByPrimaryKeySelective
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskLabel)
	 */
	@Override
	public int updateByPrimaryKeySelective(AskLabel record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskLabelService#updateByPrimaryKey
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskLabel)
	 */
	@Override
	public int updateByPrimaryKey(AskLabel record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskLabelService#selectByAskIdList
	 * (java.util.Set)
	 */
	@Override
	public List<AskLabel> selectByAskIdList(Set<Integer> keySet) {
		// TODO Auto-generated method stub
		return null;
	}

}
