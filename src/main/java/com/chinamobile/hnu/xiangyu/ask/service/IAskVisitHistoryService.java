package com.chinamobile.hnu.xiangyu.ask.service;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskVisitHistory;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-05 16:00:38
 */
public interface IAskVisitHistoryService {

	int deleteByPrimaryKey(Long id);

	int insert(AskVisitHistory record);

	int insertSelective(AskVisitHistory record);

	AskVisitHistory selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(AskVisitHistory record);

	int updateByPrimaryKey(AskVisitHistory record);

}
