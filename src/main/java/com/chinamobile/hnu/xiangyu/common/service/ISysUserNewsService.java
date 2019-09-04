package com.chinamobile.hnu.xiangyu.common.service;

import java.util.Map;

import com.chinamobile.hnu.xiangyu.common.pojo.SysUserNews;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.web.api.vo.NewsVo;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-27 10:58:49
 */
public interface ISysUserNewsService {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table pub_advertisement
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * fad This method was generated by MyBatis Generator. This method
	 * corresponds to the database table pub_advertisement
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int insertSelective(SysUserNews record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table pub_advertisement
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	SysUserNews selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table pub_advertisement
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int updateByPrimaryKeySelective(SysUserNews record);

	/**
	 * 消息列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<NewsVo> selectListPageNews(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map, Integer userId);

	/**
	 * 批量清理消息
	 * 
	 * @param map
	 */
	int updateNewsById(Map<String, Object> map);

}