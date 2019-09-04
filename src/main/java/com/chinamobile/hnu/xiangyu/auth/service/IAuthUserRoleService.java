package com.chinamobile.hnu.xiangyu.auth.service;

import com.chinamobile.hnu.xiangyu.auth.pojo.AuthUserRole;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-15 14:52:48
 */
public interface IAuthUserRoleService {

	int deleteByPrimaryKey(AuthUserRole key);

	int insertSelective(AuthUserRole record);

	AuthUserRole selectByPrimaryKey(AuthUserRole key);

	int updateByPrimaryKeySelective(AuthUserRole record);

}
