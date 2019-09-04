/**
 * 
 */
package com.chinamobile.hnu.xiangyu.user.dao;

import com.chinamobile.hnu.xiangyu.user.pojo.AuthToken;

/**
 * 
 * @author douzisong
 * @date 2018年4月16日
 */
public interface AuthTokenMapper {
	/**
	 * 插入token值
	 * 
	 * @param item
	 */
	void insert(AuthToken token);

	/**
	 * 
	 * @param token
	 * @return
	 */
	Integer selectByToken(String token);

	/**
	 * 
	 * @param uid
	 * @return
	 */
	String selectByUid(Integer uid);

	/**
	 * 
	 * @param token
	 */
	void deleteByToken(String token);

	/**
	 * 通过userid删除token
	 * 
	 * @param uid
	 */
	int deleteByUid(Integer uid);
}
