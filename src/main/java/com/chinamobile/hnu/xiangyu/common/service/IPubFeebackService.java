package com.chinamobile.hnu.xiangyu.common.service;

import java.util.Map;

import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.web.api.vo.FeebackVo;

/**
 * 
 * <p>
 * Title: IPubFeebackService.java
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
public interface IPubFeebackService {

	/**
	 * 反馈列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<FeebackVo> selectListPageFeeback(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map);

	int deleteById(Integer id);
}
