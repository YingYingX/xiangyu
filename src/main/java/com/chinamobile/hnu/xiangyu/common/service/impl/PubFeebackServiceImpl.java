package com.chinamobile.hnu.xiangyu.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.common.dao.PubFeedbackMapper;
import com.chinamobile.hnu.xiangyu.common.service.IPubFeebackService;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.web.api.vo.FeebackVo;
import com.github.pagehelper.PageHelper;

/**
 * 
 * <p>
 * Title: PubFeebackServiceImpl.java
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author lh
 * 
 * @date 2018年8月31日
 * 
 * @version 1.0
 */
@Service
public class PubFeebackServiceImpl implements IPubFeebackService {

	@Autowired
	private PubFeedbackMapper dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.common.service.IPubFeebackService#
	 * selectListPageFeeback(java.lang.Integer, java.lang.Integer,
	 * java.util.Map)
	 */
	@Override
	public Page<FeebackVo> selectListPageFeeback(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<FeebackVo> vo = dao.selectListPageFeeback(map);
		return new Page<>(vo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.common.service.IPubFeebackService#deleteById
	 * (java.lang.Integer)
	 */
	@Override
	public int deleteById(Integer id) {
		return dao.deleteByPrimaryKey(id);
	}

}
