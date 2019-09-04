package com.chinamobile.hnu.xiangyu.auth.dao;

import java.util.List;

import com.chinamobile.hnu.xiangyu.auth.pojo.AuthRoleResources;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-15 14:52:48
 */
public interface AuthRoleResourcesMapper {

	int deleteByPrimaryKey(Integer roleId);

	int insertSelective(AuthRoleResources record);

	AuthRoleResources selectByPrimaryKey(AuthRoleResources key);

	int updateByPrimaryKeySelective(AuthRoleResources record);

	/**
	 * 保存角色权限
	 * 
	 * @param roleRes
	 */
	int insertBatchRoleRes(List<AuthRoleResources> roleRes);
}
