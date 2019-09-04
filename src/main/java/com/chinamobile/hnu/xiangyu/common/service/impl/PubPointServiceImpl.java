/**
 * 
 */
package com.chinamobile.hnu.xiangyu.common.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinamobile.hnu.xiangyu.common.dao.PubGradeRuleMapper;
import com.chinamobile.hnu.xiangyu.common.dao.PubPointRuleMapper;
import com.chinamobile.hnu.xiangyu.common.pojo.PubGradeRule;
import com.chinamobile.hnu.xiangyu.common.pojo.PubPointRule;
import com.chinamobile.hnu.xiangyu.common.service.PubPointService;
import com.chinamobile.hnu.xiangyu.user.dao.UserAccountMapper;
import com.chinamobile.hnu.xiangyu.user.dao.UserPointRecordMapper;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.pojo.UserPointRecord;

/**
 * @ClassName: PubPointServiceImpl.java
 * @Description: TODO(用一句话描述该文件做什么)
 * 
 * @author dev31
 * @version V1.0
 * @Date 2018年7月30日 下午4:11:01
 */
@Service
public class PubPointServiceImpl implements PubPointService {

	@Autowired
	private UserPointRecordMapper pointRecordMapper;

	@Autowired
	private UserAccountMapper acctMapper;

	@Autowired
	private PubGradeRuleMapper gradeRuleMapper;

	@Autowired
	private PubPointRuleMapper pointRuleMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.common.service.PubPointService#addPoint()
	 */
	@Transactional
	@Override
	public void addPoint(int uid, int ruleid, String bizid, String extid) {
		// TODO Auto-generated method stub

		// 查询业务规则
		PubPointRule pubPointRule = pointRuleMapper.selectByPrimaryKey(ruleid);
		UserPointRecord userPointRecord = null;
		if (pubPointRule.getFrequency() != 2) {
			userPointRecord = pointRecordMapper.selectUserPointRecord(uid, pubPointRule.getFrequency(), ruleid);
		}
		if (userPointRecord == null) {
			// 查询用户今日获取该类型总经验值
			int sum = pointRecordMapper.selectUserPointRecordToDay(uid, ruleid);
			if (sum >= pubPointRule.getUpperAmount()) {
				return;
			}
			// 查询用户当前等级和经验值
			UserAccount user = acctMapper.selectByPrimaryKey(uid);
			// 修改用户经验值
			UserAccount updateUser = new UserAccount();
			updateUser.setId(uid);
			// 查询升级所需经验值
			PubGradeRule pubGradeRule = gradeRuleMapper.selectByPrimaryKey(user.getGrade()+1);
			if (user.getExperience() + pubPointRule.getAmount() >= pubGradeRule.getLower().intValue()) {
				updateUser.setGrade(user.getGrade() + 1);
				updateUser.setExperience(user.getExperience() + pubPointRule.getAmount());
			} else {
				updateUser.setExperience(user.getExperience() + pubPointRule.getAmount());
			}

			acctMapper.updateByPrimaryKeySelective(updateUser);

			userPointRecord = new UserPointRecord();
			userPointRecord.setType(1);
			userPointRecord.setAmount(pubPointRule.getAmount());
			userPointRecord.setBizid(bizid);
			userPointRecord.setExtid(extid);
			userPointRecord.setGentime(new Date());
			userPointRecord.setRuleid(ruleid);
			userPointRecord.setRulename(pubPointRule.getName());
			userPointRecord.setUid(uid);

			pointRecordMapper.insertSelective(userPointRecord);

		}
	}

}
