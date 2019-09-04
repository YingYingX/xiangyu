package com.chinamobile.hnu.xiangyu.common.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.common.dao.SysUserNewsMapper;
import com.chinamobile.hnu.xiangyu.common.pojo.SysUserNews;
import com.chinamobile.hnu.xiangyu.common.service.ISysUserNewsService;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.web.api.vo.NewsVo;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-27 10:58:49
 */
@Service("sysUserNewsService")
public class SysUserNewsServiceImpl implements ISysUserNewsService {

	private final Logger logger = Logger
			.getLogger(SysUserNewsServiceImpl.class);
	@Autowired
	protected SysUserNewsMapper mapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.common.service.ISysUserNewsService#
	 * deleteByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return mapper.deleteByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.common.service.ISysUserNewsService#
	 * insertSelective(com.chinamobile.hnu.xiangyu.common.pojo.SysUserNews)
	 */
	@Override
	public int insertSelective(SysUserNews record) {
		return mapper.insertSelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.common.service.ISysUserNewsService#
	 * selectByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public SysUserNews selectByPrimaryKey(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.common.service.ISysUserNewsService#
	 * updateByPrimaryKeySelective
	 * (com.chinamobile.hnu.xiangyu.common.pojo.SysUserNews)
	 */
	@Override
	public int updateByPrimaryKeySelective(SysUserNews record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.common.service.ISysUserNewsService#
	 * selectListPageNews(java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<NewsVo> selectListPageNews(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map, Integer userId) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<NewsVo> nv = mapper.selectListPageNews(map);
		if (nv.size() > 0) {
			for (NewsVo vo : nv) {
				if (StringUtils.isNotBlank(vo.getHeader())) {
					vo.setHeader(UploadDocumentUtil.buildPublicPath(vo
							.getHeader()));
				}
			}
		}
		// Integer type = (Integer) map.get("type");
		// if (pageCurrent.equals(1) && !type.equals(2)) {
		// SysUserNews record = new SysUserNews();
		// record.setCategory(type);
		// record.setReceiveId(userId);
		// mapper.updateByIds(record);
		// }
		return new Page<NewsVo>(nv);
	}

	/*
	 * (non-Javadoc) fasdf
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.common.service.ISysUserNewsService#updateNewsById
	 * (java.util.Map)
	 */
	@Override
	public int updateNewsById(Map<String, Object> map) {
		return mapper.updateNewsById(map);
	}
}
