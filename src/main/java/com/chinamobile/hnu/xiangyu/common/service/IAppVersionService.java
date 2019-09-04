package com.chinamobile.hnu.xiangyu.common.service;

import java.util.Map;

import com.chinamobile.hnu.xiangyu.common.pojo.AppVersion;
import com.chinamobile.hnu.xiangyu.util.dto.Page;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-09-21 09:35:44
 */
public interface IAppVersionService {

	/**
	 * 保存版本信息
	 * @param version
	 */
	void insert(AppVersion version);

	/**
	 * 查询版本信息
	 * @param type 1.Android；2.Ios
	 * @return
	 */
	AppVersion selectAppVersionNow(int type);

	/**
	 * 版本分页
	 * @param pn
	 * @param ps
	 * @param map
	 * @return
	 */
	Page<AppVersion> selectAppVersionByPage(Integer pn, Integer ps, Map<String, Object> map);

}
