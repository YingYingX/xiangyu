package com.chinamobile.hnu.xiangyu.ask.service;

import java.util.List;
import java.util.Set;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskLabel;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-05 16:00:38
 */
public interface IAskLabelService {
	int deleteByPrimaryKey(Long id);

	int insert(AskLabel record);

	int insertSelective(AskLabel record);

	AskLabel selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(AskLabel record);

	int updateByPrimaryKey(AskLabel record);

	List<AskLabel> selectByAskIdList(Set<Integer> keySet);

}
