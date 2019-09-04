/**
 * Copyright ©2016, 长沙豆子信息技术有限公司, All rights reserved.
 */
package com.chinamobile.hnu.xiangyu.common.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.common.dao.PubAdvertisementMapper;
import com.chinamobile.hnu.xiangyu.common.dao.PubConstPlaneMapper;
import com.chinamobile.hnu.xiangyu.common.pojo.PubAdvertisement;
import com.chinamobile.hnu.xiangyu.common.pojo.PubConstPlane;
import com.chinamobile.hnu.xiangyu.common.service.PubConstPlaneService;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author douzisong
 * @date 2016年12月22日
 */
@Service
public class PubConstPlaneServiceImpl implements PubConstPlaneService {
	@Autowired
	private PubConstPlaneMapper dao;

	@Autowired
	private PubAdvertisementMapper advertisementMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.common.service.PubConstPlaneService#
	 * deleteByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return dao.deleteByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.common.service.PubConstPlaneService#insert
	 * (com.chinamobile.hnu.xiangyu.common.pojo.PubConstPlane)
	 */
	@Override
	public int insert(PubConstPlane record) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.common.service.PubConstPlaneService#
	 * insertSelective(com.chinamobile.hnu.xiangyu.common.pojo.PubConstPlane)
	 */
	@Override
	public int insertSelective(PubConstPlane record) {
		// TODO Auto-generated method stub
		return dao.insertSelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.common.service.PubConstPlaneService#
	 * selectByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public PubConstPlane selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return dao.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.common.service.PubConstPlaneService#
	 * updateByPrimaryKeySelective
	 * (com.chinamobile.hnu.xiangyu.common.pojo.PubConstPlane)
	 */
	@Override
	public int updateByPrimaryKeySelective(PubConstPlane record) {
		// TODO Auto-generated method stub
		return dao.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.common.service.PubConstPlaneService#
	 * updateByPrimaryKey(com.chinamobile.hnu.xiangyu.common.pojo.PubConstPlane)
	 */
	@Override
	public int updateByPrimaryKey(PubConstPlane record) {
		// TODO Auto-generated method stub
		return dao.updateByPrimaryKey(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.common.service.PubConstPlaneService#
	 * selectPlanesByType(java.lang.String)
	 */
	@Override
	public List<PubConstPlane> selectPlanesByType(String type) {
		// TODO Auto-generated method stub
		return dao.selectPlanesByType(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.common.service.PubConstPlaneService#
	 * selectPubAdvertisementByType(java.lang.String)
	 */
	@Override
	public List<PubAdvertisement> selectPubAdvertisementByType(Integer category) {
		// TODO Auto-generated method stub
		List<PubAdvertisement> list = advertisementMapper
				.selectByCategory(category);
		for (PubAdvertisement pubAdvertisement : list) {
			pubAdvertisement.setCoverImage(UploadDocumentUtil
					.buildPublicPath(pubAdvertisement.getCoverImage()));
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.common.service.PubConstPlaneService#selectAdvAll
	 * (java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Page<PubAdvertisement> selectAdvAll(Integer pageCurrent,
			Integer pageSize) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<PubAdvertisement> pub = advertisementMapper.selectAdvAll();
		if (pub.size() > 0) {
			for (PubAdvertisement pubAdvertisement : pub) {
				if (StringUtils.isNotBlank(pubAdvertisement.getCoverImage())) {
					pubAdvertisement.setCoverImage(UploadDocumentUtil
							.buildPublicPath(pubAdvertisement.getCoverImage()));
				}
			}
		}
		return new Page<PubAdvertisement>(pub);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.common.service.PubConstPlaneService#
	 * insertSelective(com.chinamobile.hnu.xiangyu.common.pojo.PubAdvertisement)
	 */
	@Override
	public int insertSelective(PubAdvertisement record) {
		return advertisementMapper.insertSelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.common.service.PubConstPlaneService#
	 * updateByPrimaryKeySelective
	 * (com.chinamobile.hnu.xiangyu.common.pojo.PubAdvertisement)
	 */
	@Override
	public int updateByPrimaryKeySelective(PubAdvertisement record) {
		return advertisementMapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.common.service.PubConstPlaneService#
	 * selectPlanesByList(java.lang.String, java.lang.String)
	 */
	@Override
	public Page<PubConstPlane> selectPlanesByList(Integer pageCurrent,
			Integer pageSize, String type, String name) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<PubConstPlane> con = dao.selectPlanesByList(type, name);
		return new Page<PubConstPlane>(con);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.common.service.PubConstPlaneService#
	 * deleteBatchDisableById(java.util.List)
	 */
	@Override
	public int deleteBatchDisableById(List<Integer> idList) {
		return dao.deleteBatchDisableById(idList);
	}

}
