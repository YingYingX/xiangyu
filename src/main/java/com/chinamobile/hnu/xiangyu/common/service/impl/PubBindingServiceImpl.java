/**
 * 
 */
package com.chinamobile.hnu.xiangyu.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.common.dao.PubBindingMapper;
import com.chinamobile.hnu.xiangyu.common.pojo.PubBinding;
import com.chinamobile.hnu.xiangyu.common.service.PubBindingService;

/**
 * @author douzisong
 * @date 2018年5月15日
 */
@Service
public class PubBindingServiceImpl implements PubBindingService {
	@Autowired
	private PubBindingMapper dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecobuild.pf.base.service.BindingItemService#insert(com.ecobuild.pf.
	 * base.pojo.BindingItem)
	 */
	@Override
	public void insert(PubBinding bind) {
		dao.insert(bind);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecobuild.pf.base.service.BindingItemService#selectById(int)
	 */
	@Override
	public PubBinding selectByPrimaryKey(Long id) {
		return dao.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ecobuild.pf.base.service.BindingItemService#selectByCnd(com.ecobuild.
	 * pf.base.pojo.BindingItem)
	 */
	@Override
	public PubBinding selectByCnd(PubBinding bind) {
		return dao.selectByCnd(bind);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecobuild.pf.base.service.BindingItemService#countByCnd(com.ecobuild.
	 * pf.base.pojo.BindingItem)
	 */
	@Override
	public int countByCnd(PubBinding bind) {
		return dao.countByCnd(bind);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecobuild.pf.base.service.BindingItemService#verify(com.ecobuild.pf.
	 * base.pojo.BindingItem)
	 */
	@Override
	public boolean verify(PubBinding bind) {
		return dao.selectByCnd(bind) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecobuild.pf.base.service.BindingItemService#getBindCountByNow(java.
	 * lang.String, int)
	 */
	@Override
	public boolean getBindCountByNow(String phone, int maximum) {
		return dao.getBindCountByNow(phone) >= maximum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ecobuild.pf.base.service.BindingItemService#getBindByTimeOffset(java.
	 * lang.String, int)
	 */
	@Override
	public boolean getBindByTimeOffset(String phone, int offset) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		map.put("offset", offset);

		return dao.getBindByTimeOffset(map) > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecobuild.pf.base.service.BindingItemService#checkBindingCode(com.
	 * ecobuild.pf.base.pojo.BindingItem)
	 */
	@Override
	public boolean checkBindingCode(PubBinding bindingItem) {
		Integer count = dao.selectBindingCode(bindingItem);
		return count != null && count == 1;
	}

}
