package com.chinamobile.hnu.xiangyu.auth.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.auth.dao.AuthUserRoleMapper;
import com.chinamobile.hnu.xiangyu.auth.pojo.AuthUserRole;
import com.chinamobile.hnu.xiangyu.auth.service.IAuthUserRoleService;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-15 14:52:48
 */
@Service("authUserRoleService")
public class AuthUserRoleServiceImpl implements IAuthUserRoleService {

	private final Logger logger = Logger
			.getLogger(AuthUserRoleServiceImpl.class);
	@Autowired
	protected AuthUserRoleMapper mapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.auth.service.IAuthUserRoleService#
	 * deleteByPrimaryKey(com.chinamobile.hnu.xiangyu.auth.pojo.AuthUserRole)
	 */
	@Override
	public int deleteByPrimaryKey(AuthUserRole key) {
		return mapper.deleteByPrimaryKey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.auth.service.IAuthUserRoleService#insertSelective
	 * (com.chinamobile.hnu.xiangyu.auth.pojo.AuthUserRole)
	 */
	@Override
	public int insertSelective(AuthUserRole record) {
		return mapper.insertSelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.auth.service.IAuthUserRoleService#
	 * selectByPrimaryKey(com.chinamobile.hnu.xiangyu.auth.pojo.AuthUserRole)
	 */
	@Override
	public AuthUserRole selectByPrimaryKey(AuthUserRole key) {
		return mapper.selectByPrimaryKey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.auth.service.IAuthUserRoleService#
	 * updateByPrimaryKeySelective
	 * (com.chinamobile.hnu.xiangyu.auth.pojo.AuthUserRole)
	 */
	@Override
	public int updateByPrimaryKeySelective(AuthUserRole record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

}
