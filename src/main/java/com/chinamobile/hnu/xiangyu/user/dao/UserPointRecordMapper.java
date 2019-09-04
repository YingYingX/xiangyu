package com.chinamobile.hnu.xiangyu.user.dao;

import org.apache.ibatis.annotations.Param;

import com.chinamobile.hnu.xiangyu.user.pojo.UserPointRecord;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version  2018-07-30 16:23:09
 */
public interface UserPointRecordMapper{

	/**
	 * 查询用户今日获取该类型总经验值
	 * @param uid
	 * @param bizid
	 * @return
	 */
	int selectUserPointRecordToDay(@Param("uid")int uid, @Param("ruleid")int ruleid);

	/**
	 * 查询用户是否有权获取规则
	 * @param uid
	 * @param frequency
	 * @return
	 */
	UserPointRecord selectUserPointRecord(@Param("uid")int uid, @Param("frequency")Integer frequency,@Param("ruleid") int ruleid);

	/**
	 * 插入记录
	 * @param userPointRecord
	 */
	void insertSelective(UserPointRecord userPointRecord);

}
