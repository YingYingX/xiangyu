package com.chinamobile.hnu.xiangyu.auth.dao;

import java.util.List;
import java.util.Map;

import com.chinamobile.hnu.xiangyu.auth.pojo.AuthResources;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-15 14:52:47
 */
public interface AuthResourcesMapper {

	int deleteByPrimaryKey(AuthResources key);

	int insertSelective(AuthResources record);

	AuthResources selectByPrimaryKey(AuthResources key);

	int updateByPrimaryKeySelective(AuthResources record);

	List<AuthResources> selectPage();

	/**
	 * 资源树
	 * 
	 * @return
	 */
	List<Map<String, Object>> selectResourceList();

	/**
	 * 通过登录用户获取资源树
	 * 
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> selectUserResourceList(Integer roleId);

	/**
	 * 查询所有一级菜单
	 * 
	 * @return
	 */
	List<Map<String, Object>> selectByFirstIds();

	/**
	 * 查询用户权限列表
	 * 
	 * @param roleId
	 * @return
	 */
	List<String> selectUserResourceLists(Integer roleId);
}
