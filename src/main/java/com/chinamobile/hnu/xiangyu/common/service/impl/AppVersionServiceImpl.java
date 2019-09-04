package com.chinamobile.hnu.xiangyu.common.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.common.dao.AppVersionMapper;
import com.chinamobile.hnu.xiangyu.common.pojo.AppVersion;
import com.chinamobile.hnu.xiangyu.common.service.IAppVersionService;
import com.chinamobile.hnu.xiangyu.user.vo.UserHistoricalFootprintVo;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-09-21 09:35:44
 */
@Service("appVersionService")
public class AppVersionServiceImpl implements IAppVersionService {

	private final Logger logger = Logger.getLogger(AppVersionServiceImpl.class);
	@Autowired
	protected AppVersionMapper mapper;
	
	/* (non-Javadoc)
	 * @see com.chinamobile.hnu.xiangyu.common.service.IAppVersionService#insert(com.chinamobile.hnu.xiangyu.common.pojo.AppVersion)
	 */
	@Override
	public void insert(AppVersion version) {
		// TODO Auto-generated method stub
		mapper.insert(version);
	}

	/* (non-Javadoc)
	 * @see com.chinamobile.hnu.xiangyu.common.service.IAppVersionService#selectAppVersionNow(int)
	 */
	@Override
	public AppVersion selectAppVersionNow(int type) {
		// TODO Auto-generated method stub
		return mapper.selectAppVersionNow(type);
	}

	/* (non-Javadoc)
	 * @see com.chinamobile.hnu.xiangyu.common.service.IAppVersionService#selectAppVersionByPage(java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<AppVersion> selectAppVersionByPage(Integer pageCurrent, Integer pageSize, Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<AppVersion> list = mapper.selectByPage(map);
		return new Page<AppVersion>(list);
	}

}
