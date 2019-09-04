package com.chinamobile.hnu.xiangyu.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.auth.dao.AuthRoleMapper;
import com.chinamobile.hnu.xiangyu.auth.pojo.AuthRole;
import com.chinamobile.hnu.xiangyu.auth.service.IAuthRoleService;
import com.chinamobile.hnu.xiangyu.auth.vo.RoleVo;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-15 14:52:47
 */
@Service("authRoleService")
public class AuthRoleServiceImpl implements IAuthRoleService {

	private final Logger logger = Logger.getLogger(AuthRoleServiceImpl.class);
	@Autowired
	protected AuthRoleMapper mapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.auth.service.IAuthRoleService#deleteByPrimaryKey
	 * (com.chinamobile.hnu.xiangyu.auth.pojo.AuthRole)
	 */
	@Override
	public int deleteByPrimaryKey(AuthRole key) {
		return mapper.deleteByPrimaryKey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.auth.service.IAuthRoleService#insertSelective
	 * (com.chinamobile.hnu.xiangyu.auth.pojo.AuthRole)
	 */
	@Override
	public int insertSelective(AuthRole record) {
		return mapper.insertSelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.auth.service.IAuthRoleService#selectByPrimaryKey
	 * (com.chinamobile.hnu.xiangyu.auth.pojo.AuthRole)
	 */
	@Override
	public AuthRole selectByPrimaryKey(AuthRole key) {
		return mapper.selectByPrimaryKey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.auth.service.IAuthRoleService#
	 * updateByPrimaryKeySelective
	 * (com.chinamobile.hnu.xiangyu.auth.pojo.AuthRole)
	 */
	@Override
	public int updateByPrimaryKeySelective(AuthRole record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.auth.service.IAuthRoleService#batchRolesById
	 * (java.util.List)
	 */
	@Override
	public int batchDelRolesById(List<Integer> idList) {
		// TODO Auto-generated method stub
		return mapper.batchDelRolesById(idList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.auth.service.IAuthRoleService#selectRoleList
	 * ()
	 */
	@Override
	public List<RoleVo> selectRoleList() {
		List<RoleVo> roles = mapper.selectRoleList();
		if (roles.size() > 0) {
			for (RoleVo role : roles) {
				List<Integer> resourceId = new ArrayList<Integer>();
				if (StringUtils.isNotBlank(role.getResourceId())) {
					String[] recId = role.getResourceId().split(",");
					for (String res : recId) {
						resourceId.add(Integer.valueOf(res));
					}
					String resourceName = mapper.selectResById(resourceId);
					if (StringUtils.isNotBlank(resourceName)) {
						role.setResourceName(resourceName);
					}
				} else {
					role.setResourceName("-");
				}
			}
		}
		return roles;
	}

}
