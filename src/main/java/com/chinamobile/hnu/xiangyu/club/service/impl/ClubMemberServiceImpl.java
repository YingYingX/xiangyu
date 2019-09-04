/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinamobile.hnu.xiangyu.club.dao.ClubInfoMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubMemberMapper;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubMember;
import com.chinamobile.hnu.xiangyu.club.service.ClubMemberService;
import com.chinamobile.hnu.xiangyu.common.dao.SysUserNewsMapper;
import com.chinamobile.hnu.xiangyu.common.enums.ExtidType;
import com.chinamobile.hnu.xiangyu.common.enums.PointRuleType;
import com.chinamobile.hnu.xiangyu.common.pojo.SysUserNews;
import com.chinamobile.hnu.xiangyu.common.service.PubPointService;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.um.pojo.Alarm;
import com.um.service.UmengService;

/**
 * @author The Old Man and the Sea
 *
 *         2018年6月5日
 */
@Service
public class ClubMemberServiceImpl implements ClubMemberService {
	private final Logger logger = Logger.getLogger(ClubMemberService.class);
	@Autowired
	private ClubInfoMapper mapper;

	@Autowired
	private ClubMemberMapper memberMapper;

	@Autowired
	protected SysUserNewsMapper userNewsMapper;

	@Autowired
	private PubPointService pointService;

	@Autowired
	private UmengService umengService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubMemberService#selectClubMember
	 * (java.lang.Integer)
	 */
	@Override
	public List<ClubMember> selectClubMember(Integer clubId) {
		// TODO Auto-generated method stub
		List<ClubMember> list = memberMapper.selectClubMemberByClubId(clubId);
		for (ClubMember clubMember : list) {
			if (StringUtils.isNotBlank(clubMember.getHeader())) {
				clubMember.setHeader(UploadDocumentUtil
						.buildPublicPath(clubMember.getHeader()));
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubMemberService#updateClubMember
	 * (com.chinamobile.hnu.xiangyu.club.pojo.ClubMember)
	 */
	@Override
	public void updateClubMember(ClubMember clubMember) {
		// TODO Auto-generated method stub
		memberMapper.updateByPrimaryKeySelective(clubMember);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubMemberService#
	 * selectClubMemberById(java.lang.Long)
	 */
	@Override
	public ClubMember selectClubMemberById(Long id) {
		// TODO Auto-generated method stub
		return memberMapper.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubMemberService#
	 * selectClubMemberByClubId(java.util.Map)
	 */
	@Override
	public List<ClubMember> selectClubMemberByClubId(Map<String, Object> map) {
		List<ClubMember> list = memberMapper.selectClubMember(map);
		for (ClubMember clubMember : list) {
			if (StringUtils.isNotBlank(clubMember.getHeader())) {
				clubMember.setHeader(UploadDocumentUtil
						.buildPublicPath(clubMember.getHeader()));
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#userIsClubAdmin(
	 * java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public boolean userIsClubAdmin(Integer clubId, Integer uid) {
		// TODO Auto-generated method stub
		List<ClubMember> users = memberMapper.selectAdminByClubId(clubId);
		for (ClubMember clubMember : users) {
			if (clubMember.getMemberId().intValue() == uid.intValue()) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#selectClubAdmin(
	 * java.lang.Integer)
	 */
	@Override
	public List<ClubMember> selectClubAdmin(Integer clubId) {
		// TODO Auto-generated method stub
		return memberMapper.selectAdminByClubId(clubId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#inertClubMember(
	 * com.chinamobile.hnu.xiangyu.club.pojo.ClubMember)
	 */
	@Override
	@Transactional
	public void inertClubMember(ClubMember member, Integer op, Integer userId,
			Integer nid) {
		SysUserNews record = new SysUserNews();
		record.setBizId(Long.valueOf(member.getClubId()));
		record.setReplyId(member.getMemberId());
		record.setGmtModified(new Date());
		record.setId(nid);
		if (op == 1) {
			member.setJoinTime(new Date());
			member.setRole(Byte.valueOf("3"));
			memberMapper.insert(member);

			// 设置通知状态已同意
			record.setStatus(2);
			userNewsMapper.updateById(record);
			// 向申请人发送同意消息
			SysUserNews record1 = new SysUserNews();
			record1.setBizId(Long.valueOf(member.getClubId()));
			record1.setReplyId(userId);
			record1.setCategory(2);
			record1.setReceiveId(member.getMemberId());
			record1.setGmtCreate(new Date());
			record1.setStatus(2);
			userNewsMapper.insertSelective(record1);
			// 友盟推送消息
			Alarm alarm = new Alarm();
			alarm.setTitle("申请入团消息");
			alarm.setType(1);
			alarm.setReceiveId(member.getMemberId());
			alarm.setMsg("团队管理员同意了您的入团申请");
			try {
				umengService.sendAndroidBroadcast(alarm);
				umengService.sendIOSCustomizedcast(alarm);
			} catch (Exception e) {
				logger.error("send umeng msg  is fail", e);
			}
			mapper.updateMember(member.getClubId(), op);
			// 加经验值
			pointService.addPoint(member.getMemberId(),
					PointRuleType.TYPE1.getValue(), member.getId().toString(),
					ExtidType.TYPE1.getValue());
		} else if (op == 2) {
			memberMapper.delete(member);
			mapper.updateMember(member.getClubId(), op);
		} else if (op == 3) {
			record.setStatus(3);
			userNewsMapper.updateById(record);
			// 向申请人发送拒绝消息
			SysUserNews record1 = new SysUserNews();
			record1.setBizId(Long.valueOf(member.getClubId()));
			record1.setReplyId(userId);
			record1.setReceiveId(member.getMemberId());
			record1.setGmtCreate(new Date());
			record1.setCategory(2);
			record1.setStatus(3);
			userNewsMapper.insertSelective(record1);
			// 友盟推送消息
			Alarm alarm = new Alarm();
			alarm.setReceiveId(member.getMemberId());
			alarm.setTitle("申请入团消息");
			alarm.setType(1);
			alarm.setMsg("团队管理员拒绝了您的入团申请");
			try {
				umengService.sendAndroidBroadcast(alarm);
				umengService.sendIOSCustomizedcast(alarm);
			} catch (Exception e) {
				logger.error("send umeng msg  is fail", e);
			}
		} else {
			return;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#selectUserJoin
	 * (java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ClubMember selectUserJoin(Integer clubId, Integer uid) {
		// TODO Auto-generated method stub
		return memberMapper.selectUserJoin(clubId, uid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#deleteClubMember
	 * (java.lang.Integer, java.lang.Integer)
	 */
	@Transactional
	@Override
	public void deleteClubMember(Integer clubId, Integer uid, Integer aid) {
		// TODO Auto-generated method stub
		ClubMember member = new ClubMember();
		member.setClubId(clubId);
		member.setMemberId(uid);
		memberMapper.delete(member);
		mapper.updateMember(clubId, 2);
		// 存储消息
		SysUserNews record1 = new SysUserNews();
		record1.setBizId(Long.valueOf(member.getClubId()));
		record1.setReplyId(aid);
		record1.setReceiveId(uid);
		record1.setGmtCreate(new Date());
		record1.setCategory(2);
		record1.setStatus(5);
		record1.setContent("您已被管理员踢出了团队");
		userNewsMapper.insertSelective(record1);
		// 友盟推送消息
		Alarm alarm = new Alarm();
		alarm.setTitle("被踢出团队消息");
		alarm.setType(1);
		alarm.setReceiveId(uid);
		alarm.setMsg("团队管理员已将您踢出了团队");
		try {
			umengService.sendAndroidBroadcast(alarm);
			umengService.sendIOSCustomizedcast(alarm);
		} catch (Exception e) {
			logger.error("send umeng msg  is fail", e);
		}
	}

}
