package com.chinamobile.hnu.xiangyu.auth.dao;

import java.util.List;

import com.chinamobile.hnu.xiangyu.auth.pojo.AuthRole;
import com.chinamobile.hnu.xiangyu.auth.vo.RoleVo;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-15 14:52:47
 */
public interface AuthRoleMapper {

	int deleteByPrimaryKey(AuthRole key);

	int insertSelective(AuthRole record);

	AuthRole selectByPrimaryKey(AuthRole key);

	int updateByPrimaryKeySelective(AuthRole record);

	/**
	 * 批量删除角色
	 * 
	 * @param idList
	 */
	int batchDelRolesById(List<Integer> idList);

	List<RoleVo> selectRoleList();

	String selectResById(List<Integer> resourceId);

}
