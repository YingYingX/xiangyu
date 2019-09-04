package com.chinamobile.hnu.xiangyu.ask.dao;

import java.util.List;
import java.util.Map;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskSpecial;
import com.chinamobile.hnu.xiangyu.ask.vo.AskInfoVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.AaskInfoVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.AskSpecialVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.ClubSpeackVo;

public interface AskSpecialMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(AskSpecial record);

	int insertSelective(AskSpecial record);

	AskSpecial selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(AskSpecial record);

	int updateByPrimaryKeyWithBLOBs(AskSpecial record);

	int updateByPrimaryKey(AskSpecial record);

	/**
	 * 分页查询指定专场信息xx
	 * 
	 * @param type
	 * @return
	 */
	List<AskSpecial> selectPage(Integer type);

	/**
	 * 根据专题id分页查询专题评论
	 * 
	 * @param id
	 * @return
	 */
	List<AskInfoVo> pgComment(Integer id);

	/**
	 * 专题列表（后台）
	 * 
	 * @param map
	 * @return
	 */
	List<ClubSpeackVo> selectAskSpecialByList(Map<String, Object> map);

	/**
	 * 查询专题详情
	 * 
	 * @param id
	 * @return
	 */
	AskSpecialVo selectSpecialInfoById(Integer id);

	/**
	 * 批量删除专题
	 * 
	 * @param idList
	 * @return
	 */
	int deleteBatchSpecial(List<Integer> idList);

	/**
	 * 文章列表
	 * 
	 * @param map
	 * @return
	 */
	List<ClubSpeackVo> selectSpecialByList(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<ClubSpeackVo> selectreportList(Map<String, Object> map);

	/**
	 * @param askId
	 * @return
	 */
	AaskInfoVo selectReportAskInfo(Long askId);

}