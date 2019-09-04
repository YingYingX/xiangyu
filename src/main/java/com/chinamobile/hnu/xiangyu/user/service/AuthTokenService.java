/**
 * 
 */
package com.chinamobile.hnu.xiangyu.user.service;

import javax.servlet.http.HttpServletRequest;

import com.chinamobile.hnu.xiangyu.user.pojo.AuthToken;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;

/**
 * 
 * @author douzisong
 * @date 2018年4月16日
 */
public interface AuthTokenService {

	/**
	 * 插入token值
	 * 
	 * @param item
	 */
	void insert(AuthToken item);

	/**
	 * 通过token查询
	 * 
	 * @param token
	 * @return
	 */
	int selectByToken(String token);

	/**
	 * 通过userid查询
	 * 
	 * @param uid
	 * @return
	 */
	String selectByUid(int uid);

	/**
	 * 通过token删除token
	 * 
	 * @param token
	 */
	void deleteByToken(String token);

	/**
	 * 通过userid删除token
	 * 
	 * @param uid
	 */
	int deleteByUid(int uid);

	/**
	 * 获取当前登录的用对象
	 * 
	 * @param request
	 * @return 如果没有登录， 则返回null；如果已经登录，则返回登录的对象
	 */
	UserAccount getLoginUser();
}
