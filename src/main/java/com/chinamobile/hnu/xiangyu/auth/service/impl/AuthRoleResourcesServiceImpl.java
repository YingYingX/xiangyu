package com.chinamobile.hnu.xiangyu.auth.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.auth.dao.AuthRoleResourcesMapper;
import com.chinamobile.hnu.xiangyu.auth.pojo.AuthRoleResources;
import com.chinamobile.hnu.xiangyu.auth.service.IAuthRoleResourcesService;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-15 14:52:48
 */
@Service("authRoleResourcesService")
public class AuthRoleResourcesServiceImpl implements IAuthRoleResourcesService {

	private final Logger logger = Logger
			.getLogger(AuthRoleResourcesServiceImpl.class);
	@Autowired
	protected AuthRoleResourcesMapper mapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.auth.service.IAuthRoleResourcesService#
	 * deleteByPrimaryKey
	 * (com.chinamobile.hnu.xiangyu.auth.pojo.AuthRoleResources)
	 */
	@Override
	public int deleteByPrimaryKey(Integer roleId) {
		return mapper.deleteByPrimaryKey(roleId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.auth.service.IAuthRoleResourcesService#
	 * insertSelective(com.chinamobile.hnu.xiangyu.auth.pojo.AuthRoleResources)
	 */
	@Override
	public int insertSelective(AuthRoleResources record) {
		return mapper.insertSelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.auth.service.IAuthRoleResourcesService#
	 * selectByPrimaryKey
	 * (com.chinamobile.hnu.xiangyu.auth.pojo.AuthRoleResources)
	 */
	@Override
	public AuthRoleResources selectByPrimaryKey(AuthRoleResources key) {
		return mapper.selectByPrimaryKey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.auth.service.IAuthRoleResourcesService#
	 * updateByPrimaryKeySelective
	 * (com.chinamobile.hnu.xiangyu.auth.pojo.AuthRoleResources)
	 */
	@Override
	public int updateByPrimaryKeySelective(AuthRoleResources record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.auth.service.IAuthRoleResourcesService#
	 * insertBatchRoleRes(java.util.List)
	 */
	@Override
	public int insertBatchRoleRes(List<AuthRoleResources> roleRes,
			Integer roleId) {
		// 删除原来的角色资源
		mapper.deleteByPrimaryKey(roleId);
		return mapper.insertBatchRoleRes(roleRes);
	}
}
