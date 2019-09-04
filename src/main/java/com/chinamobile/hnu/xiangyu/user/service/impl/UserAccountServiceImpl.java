/**
 * 
 */
package com.chinamobile.hnu.xiangyu.user.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinamobile.hnu.xiangyu.club.dao.ClubMemberMapper;
import com.chinamobile.hnu.xiangyu.user.dao.UserAccountMapper;
import com.chinamobile.hnu.xiangyu.user.dao.UserCertificationMapper;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.pojo.UserCertification;
import com.chinamobile.hnu.xiangyu.user.service.UserAccountService;
import com.chinamobile.hnu.xiangyu.user.vo.UserVo;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.github.pagehelper.PageHelper;
import com.um.pojo.Alarm;
import com.um.service.UmengService;

/**
 * 账号相关的后台操作接口
 * 
 * @author songcl
 * @date 2018年5月18日
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {
	private final Logger logger = Logger.getLogger(UserAccountService.class);
	@Autowired
	private UserAccountMapper dao;

	@Autowired
	private ClubMemberMapper memberMapper;

	@Autowired
	private UserCertificationMapper certificationMapper;

	@Autowired
	private UmengService umengService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.user.service.AccountService#insert(com.
	 * chinamobile.hnu.xiangyu.user.pojo.UserAccount)
	 */
	@Override
	public int insert(UserAccount record) {
		return dao.insert(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.AccountService#insertSelective
	 * (com. chinamobile.hnu.xiangyu.user.pojo.UserAccount)
	 */
	@Override
	public int insertSelective(UserAccount record) {
		return dao.insertSelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.AccountService#selectByPrimaryKey
	 * ( java.lang.Integer)
	 */
	@Override
	public UserAccount selectByPrimaryKey(Integer id) {
		return dao.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.user.service.AccountService#
	 * updateByPrimaryKeySelective(com.chinamobile.hnu.xiangyu.user.pojo.
	 * UserAccount)
	 */
	@Override
	@Transactional
	public int updateByPrimaryKeySelective(UserAccount record) {
		return dao.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.AccountService#updateByPrimaryKey
	 * ( com.chinamobile.hnu.xiangyu.user.pojo.UserAccount)
	 */
	@Override
	public int updateByPrimaryKey(UserAccount record) {
		record.setGmtModified(new Date());
		return dao.updateByPrimaryKey(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.AccountService#checkPhone(java
	 * .lang. String)
	 */
	@Override
	public boolean checkPhone(String phone) {
		UserAccount user = dao.selectByPhone(phone);
		return user != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.AccountService#findByPhone(java
	 * .lang .String)
	 */
	@Override
	public UserAccount findByPhone(String phone) {
		return dao.selectByPhone(phone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.AccountService#verifyPwd(java
	 * .lang. Integer, java.lang.String)
	 */
	@Override
	public boolean verifyPwd(Integer userId, String password) {
		if (StringUtils.isBlank(password)) {
			return false;
		}

		return password.equals(dao.selectPassword(userId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.AccountService#updatePassword
	 * (java. lang.Integer, java.lang.String)
	 */
	@Override
	public void updatePassword(Integer userId, String password) {
		UserAccount user = new UserAccount();
		user.setId(userId);
		user.setPassword(password);
		dao.updateByPrimaryKeySelective(user);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.AccountService#updatePassword
	 * (java. lang.String, java.lang.String)
	 */
	@Override
	public void updatePassword(String phone, String password) {
		UserAccount user = new UserAccount();
		user.setTelephone(phone);
		user.setPassword(password);
		dao.updateByPrimaryKeySelective(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.AccountService#checkEnabled(
	 * java. lang.Integer)
	 */
	@Override
	public boolean checkEnabled(Integer userId) {
		Integer obj = dao.selectEnable(userId);
		return obj == null || obj.intValue() == UserAccount.STATUS_DISABLED ? false
				: true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.AccountService#updateEanbled
	 * (java. lang.Integer, boolean)
	 */
	@Override
	public void updateEanbled(Integer userId, boolean enabled) {
		UserAccount user = new UserAccount();
		user.setId(userId);
		user.setEnable(enabled ? UserAccount.STATUS_ENABLED
				: UserAccount.STATUS_DISABLED);
		dao.updateByPrimaryKeySelective(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.AccountService#updateLoginTime
	 * (java. lang.Integer, java.util.Date)
	 */
	@Override
	public void updateLoginTime(Integer userId, Date loginTime) {
		UserAccount user = new UserAccount();
		user.setId(userId);
		user.setLoginTime(loginTime);
		dao.updateByPrimaryKeySelective(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.UserAccountService#selectUserList
	 * (java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Page<UserVo> selectUserList(Integer pageCurrent, Integer pageSize) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<UserVo> userVo = dao.selectUserList();
		return new Page<>(userVo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.UserAccountService#updateUserStatus
	 * (java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public int updateUserStatus(Integer userId, Integer type) {
		// 驳回
		if (type.equals(1)) {
			UserCertification record = new UserCertification();
			record.setUserId(userId);
			record.setStatus((byte) 1);
			certificationMapper.updateByPrimaryKeySelective(record);
			UserAccount user = new UserAccount();
			user.setId(userId);
			user.setCertFlag((byte) 2);
			dao.updateByPrimaryKeySelective(user);
			// 友盟推送消息
			try {
				Alarm alarm = new Alarm();
				alarm.setTitle("认证失败提醒");
				alarm.setType(4);
				alarm.setMsg("您申请的会员认证后台审核未通过，请重新申请");
				alarm.setReceiveId(userId);
				umengService.sendAndroidBroadcast(alarm);
				umengService.sendIOSCustomizedcast(alarm);
			} catch (Exception e) {
				logger.error("send umeng msg  is fail", e);
			}
		}
		// 通过
		if (type.equals(2)) {
			UserCertification record = new UserCertification();
			record.setUserId(userId);
			record.setStatus((byte) 2);
			certificationMapper.updateByPrimaryKeySelective(record);
			UserAccount user = new UserAccount();
			user.setId(userId);
			user.setCertFlag((byte) 3);
			dao.updateByPrimaryKeySelective(user);
			// 友盟推送消息
			try {
				Alarm alarm = new Alarm();
				alarm.setTitle("认证通过提醒");
				alarm.setType(4);
				alarm.setMsg("您申请的会员认证后台已审核通过");
				alarm.setReceiveId(userId);
				umengService.sendAndroidBroadcast(alarm);
				umengService.sendIOSCustomizedcast(alarm);
			} catch (Exception e) {
				logger.error("send umeng msg  is fail", e);
			}
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.UserAccountService#selectByNickname
	 * (java.lang.String)
	 */
	@Override
	public UserAccount selectByNickname(String nickname) {
		// TODO Auto-generated method stub
		return dao.selectByNickname(nickname);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.UserAccountService#selectExpertList
	 * ()
	 */
	@Override
	public List<UserAccount> selectExpertList() {
		return dao.selectExpertList();
	}

}
