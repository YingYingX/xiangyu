package com.chinamobile.hnu.xiangyu.ask.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.ask.dao.AskPhotoMapper;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskPhoto;
import com.chinamobile.hnu.xiangyu.ask.service.IAskPhotoService;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-05 16:00:38
 */
@Service("askPhotoService")
public class AskPhotoServiceImpl implements IAskPhotoService {

	private final Logger logger = Logger.getLogger(AskPhotoServiceImpl.class);
	@Autowired
	protected AskPhotoMapper mapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskPhotoService#deleteByPrimaryKey
	 * (java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String photoId) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(photoId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskPhotoService#insert(com.
	 * chinamobile.hnu.xiangyu.ask.pojo.AskPhoto)
	 */
	@Override
	public int insert(AskPhoto record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskPhotoService#insertSelective
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskPhoto)
	 */
	@Override
	public int insertSelective(AskPhoto record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskPhotoService#selectByPrimaryKey
	 * (java.lang.String)
	 */
	@Override
	public AskPhoto selectByPrimaryKey(String photoId) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(photoId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskPhotoService#
	 * updateByPrimaryKeySelective
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskPhoto)
	 */
	@Override
	public int updateByPrimaryKeySelective(AskPhoto record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskPhotoService#updateByPrimaryKey
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskPhoto)
	 */
	@Override
	public int updateByPrimaryKey(AskPhoto record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

}
