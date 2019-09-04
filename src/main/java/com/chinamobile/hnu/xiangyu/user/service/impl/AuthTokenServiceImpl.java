/**
 * 
 */
package com.chinamobile.hnu.xiangyu.user.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.chinamobile.hnu.xiangyu.user.dao.AuthTokenMapper;
import com.chinamobile.hnu.xiangyu.user.dao.UserAccountMapper;
import com.chinamobile.hnu.xiangyu.user.pojo.AuthToken;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.AuthTokenService;

/**
 * 
 * @author douzisong
 * @date 2018年4月16日
 */
@Service
public class AuthTokenServiceImpl implements AuthTokenService {
	private static final Logger logger = LoggerFactory.getLogger(AuthTokenServiceImpl.class);

	@Autowired
	private AuthTokenMapper dao;

	@Autowired
	private UserAccountMapper userDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.douziit.service.biz.UserTokenService#insert(com.douziit.dao.pojo.
	 * UserToken)
	 */
	@Override
	public void insert(AuthToken token) {
		dao.insert(token);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.douziit.service.biz.UserTokenService#selectByToken(java.lang.String)
	 */
	@Override
	public int selectByToken(String token) {
		Integer uid = dao.selectByToken(token);
		return uid == null ? 0 : uid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.douziit.service.biz.UserTokenService#selectByUid(int)
	 */
	@Override
	public String selectByUid(int uid) {
		return dao.selectByUid(uid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.douziit.service.biz.UserTokenService#delete(java.lang.String)
	 */
	@Override
	public void deleteByToken(String token) {
		dao.deleteByToken(token);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.douziit.service.biz.UserTokenService#delete(int)
	 */
	@Override
	public int deleteByUid(int uid) {
		return dao.deleteByUid(uid);
	}

	@Override
	public UserAccount getLoginUser() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Object obj = request.getSession().getAttribute(AuthToken.ATTR_SESSION_USER);
		if (obj != null && obj instanceof UserAccount) {
			return (UserAccount) obj;
		}

		// 从请求中获取token，判断token是否有效，如果有效，则登录
		String token = request.getHeader(AuthToken.USER_ACCESS_TOKEN);
		logger.info("access token:{}", token);
//		if (StringUtils.isBlank(token)) {
//			System.out.println("toke===================null");
//			return null;
//		}

		Integer uid = dao.selectByToken(token);
		if (uid == null || uid < 1) {
			System.out.println("toke===================null2");
			return null;
		}

		// 用户已经登录
		return userDao.selectByPrimaryKey(uid);
	}
}
