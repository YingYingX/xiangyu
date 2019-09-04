package com.chinamobile.hnu.xiangyu.auth.service;

import java.util.List;

import net.sf.json.JSONArray;

import com.chinamobile.hnu.xiangyu.auth.pojo.AuthResources;
import com.chinamobile.hnu.xiangyu.util.dto.Page;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-15 14:52:47
 */
public interface IAuthResourcesService {

	int deleteByPrimaryKey(AuthResources key);

	int insertSelective(AuthResources record);

	AuthResources selectByPrimaryKey(AuthResources key);

	int updateByPrimaryKeySelective(AuthResources record);

	Page<AuthResources> selectPage(Integer pageCurrent, Integer pageSize);

	/**
	 * 资源树
	 * 
	 * @return
	 */
	JSONArray selectResourceList();

	/**
	 * 通过登录用户获取资源树
	 * 
	 * @return
	 */
	JSONArray selectUserResourceList(Integer roleId);

	/**
	 * 通过登录用户获取资源树
	 * 
	 * @return
	 */
	List<String> selectUserResourceLists(Integer roleId);

}
