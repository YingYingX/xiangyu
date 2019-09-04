package com.chinamobile.hnu.xiangyu.common.dao;

import java.util.List;
import java.util.Map;

import com.chinamobile.hnu.xiangyu.common.pojo.AppVersion;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-09-21 09:35:44
 */
public interface AppVersionMapper {

	/**
	 * 保存版本信息
	 * @param version
	 */
	void insert(AppVersion version);

	/**
	 * 查询最新版本信息
	 * @param type
	 * @return
	 */
	AppVersion selectAppVersionNow(int type);

	/**
	 * 版本分页
	 * @param map
	 * @return
	 */
	List<AppVersion> selectByPage(Map<String, Object> map);

}
