package com.chinamobile.hnu.xiangyu.ask.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskLabel;

public interface AskLabelMapper {

	int deleteByPrimaryKey(Long id);

	int insert(AskLabel record);

	int insertSelective(AskLabel record);

	AskLabel selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(AskLabel record);

	int updateByPrimaryKey(AskLabel record);

	/**
	 * 批量插入标签xx
	 * 
	 * @param photos
	 */
	int inserList(List<AskLabel> labels);

	/**
	 * 查询标签
	 * 
	 * @param map
	 * @return
	 */
	List<AskLabel> selectLabel(Map<String, Object> map);

	List<AskLabel> selectByAskIdList(@Param("set") Set<Long> keySet);
}