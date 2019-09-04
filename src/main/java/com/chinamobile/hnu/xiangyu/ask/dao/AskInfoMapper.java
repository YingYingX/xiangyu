package com.chinamobile.hnu.xiangyu.ask.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskInfo;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskVisitHistory;
import com.chinamobile.hnu.xiangyu.ask.vo.AskInfoVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.ClubSpeackVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.UserCertificationVo;

public interface AskInfoMapper {

	int deleteByPrimaryKey(Long id);

	int insert(AskInfo record);

	int insertSelective(AskInfo record);

	AskInfo selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(AskInfo record);

	int updateByPrimaryKey(AskInfo record);

	/**
	 * 分页查询问问列表
	 * 
	 * @param map
	 * @return
	 */
	List<AskInfoVo> selectPage(Map<String, Object> map);

	/**
	 * 5.1.2.查询指定专场的记录
	 * 
	 * @param map
	 * @return
	 */
	List<AskInfoVo> pgspecial(Map<String, Object> map);

	/**
	 * 5.1.3.分页查询问问的一级评论
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	AskInfoVo pgfirst(Map<String, Object> map);

	/**
	 * 更新总的浏览记录数
	 * 
	 * @param askid
	 * @return
	 */
	int updateVisitAmount(Long askid);

	/**
	 * 修改问问昨天访问量
	 * 
	 * @param list
	 */
	void updateYesterdayAskByList(List<AskVisitHistory> list);

	/**
	 * 设置昨天访问量为零
	 */
	void updateYesterdayAskToZero();

	/**
	 * 我的问题
	 * 
	 * @param map
	 * @return
	 */
	List<AskInfoVo> selectMyListPage(Map<String, Object> map);

	/**
	 * 查询我的足迹
	 * 
	 * @param map
	 * @return
	 */
	List<AskInfoVo> selectUserVisitHistoryByUserId(
			@Param("set") Set<Long> askSet);

	/**
	 * 问题列表
	 * 
	 * @param map
	 * @return
	 */
	List<ClubSpeackVo> selectAskInfoList(Map<String, Object> map);

	/**
	 * 会员认证列表
	 * 
	 * @param map
	 * @return
	 */
	List<UserCertificationVo> selectUserCertificationList(
			Map<String, Object> map);

	/**
	 * 通过标签查询
	 * 
	 * @param map
	 * @return
	 */
	List<AskInfoVo> selectByLabelPage(Map<String, Object> map);

	/**
	 * 查询是否发布重复的问问
	 * 
	 * @param map
	 * @return
	 */
	List<AskInfo> selectByExample(Map<String, Object> map);

}