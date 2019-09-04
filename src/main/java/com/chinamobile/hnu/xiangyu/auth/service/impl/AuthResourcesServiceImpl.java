package com.chinamobile.hnu.xiangyu.auth.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.auth.dao.AuthResourcesMapper;
import com.chinamobile.hnu.xiangyu.auth.pojo.AuthResources;
import com.chinamobile.hnu.xiangyu.auth.service.IAuthResourcesService;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.tree.ZtreeUtil;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-15 14:52:47
 */
@Service("authResourcesService")
public class AuthResourcesServiceImpl implements IAuthResourcesService {

	private final Logger logger = Logger
			.getLogger(AuthResourcesServiceImpl.class);
	@Autowired
	protected AuthResourcesMapper mapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.auth.service.IAuthResourcesService#
	 * deleteByPrimaryKey(com.chinamobile.hnu.xiangyu.auth.pojo.AuthResources)
	 */
	@Override
	public int deleteByPrimaryKey(AuthResources key) {
		return mapper.deleteByPrimaryKey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.auth.service.IAuthResourcesService#
	 * insertSelective(com.chinamobile.hnu.xiangyu.auth.pojo.AuthResources)
	 */
	@Override
	public int insertSelective(AuthResources record) {
		return mapper.insertSelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.auth.service.IAuthResourcesService#
	 * selectByPrimaryKey(com.chinamobile.hnu.xiangyu.auth.pojo.AuthResources)
	 */
	@Override
	public AuthResources selectByPrimaryKey(AuthResources key) {
		return mapper.selectByPrimaryKey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.auth.service.IAuthResourcesService#
	 * updateByPrimaryKeySelective
	 * (com.chinamobile.hnu.xiangyu.auth.pojo.AuthResources)
	 */
	@Override
	public int updateByPrimaryKeySelective(AuthResources record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.auth.service.IAuthResourcesService#selectPage
	 * (java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Page<AuthResources> selectPage(Integer pageCurrent, Integer pageSize) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<AuthResources> resource = mapper.selectPage();
		return new Page<AuthResources>(resource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.auth.service.IAuthResourcesService#
	 * selectResourceList()
	 */
	@Override
	public JSONArray selectResourceList() {
		List<Map<String, Object>> maps = mapper.selectResourceList();
		if (maps.size() > 0) {
			ZtreeUtil tree = new ZtreeUtil("parent_id", "id");
			JSONArray jsonArray = new JSONArray();
			jsonArray = tree.listMapToJSONArray(maps, "0", "all");
			return jsonArray;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.auth.service.IAuthResourcesService#
	 * selectUserResourceList(java.lang.Integer)
	 */
	@Override
	public JSONArray selectUserResourceList(Integer roleId) {

		List<Map<String, Object>> maps = mapper.selectUserResourceList(roleId);

		List<Map<String, Object>> firstMap = mapper.selectByFirstIds();
		ListIterator<Map<String, Object>> iterator = maps.listIterator();

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result.addAll(maps);
		while (iterator.hasNext()) {
			String parentId = iterator.next().get("parent_id").toString();
			boolean temp = false;
			if (StringUtils.isNotBlank(parentId)) {
				ListIterator<Map<String, Object>> iter = result.listIterator();
				while (iter.hasNext()) {
					if (iter.next().get("id").toString().equals(parentId)) {
						temp = true;
					}
				}
			}
			if (!temp) {
				for (Map<String, Object> first : firstMap) {
					if (first.get("id").toString().equals(parentId)) {
						result.add(first);
						break;
					}
				}
			}
		}

		if (result.size() > 0) {
			ZtreeUtil tree = new ZtreeUtil("parent_id", "id");
			JSONArray jsonArray = new JSONArray();
			jsonArray = tree.listMapToJSONArray(result, "0", "all");
			return jsonArray;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.auth.service.IAuthResourcesService#
	 * selectUserResourceLists(java.lang.Integer)
	 */
	@Override
	public List<String> selectUserResourceLists(Integer roleId) {
		return mapper.selectUserResourceLists(roleId);
	}

}
