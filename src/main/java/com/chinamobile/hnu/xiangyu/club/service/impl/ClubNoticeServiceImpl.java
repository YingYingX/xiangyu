/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinamobile.hnu.xiangyu.club.dao.ClubNoticeMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubReleaseMapper;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubNotice;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubRelease;
import com.chinamobile.hnu.xiangyu.club.service.ClubNoticeService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubNoticeVo;
import com.chinamobile.hnu.xiangyu.common.enums.ExtidType;
import com.chinamobile.hnu.xiangyu.common.enums.PointRuleType;
import com.chinamobile.hnu.xiangyu.common.service.PubPointService;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author The Old Man and the Sea
 *
 *         2018年6月5日
 */
@Service
public class ClubNoticeServiceImpl implements ClubNoticeService {

	@Autowired
	private ClubNoticeMapper noticeMapper;

	@Autowired
	private ClubReleaseMapper releaseMapper;

	@Autowired
	private PubPointService pointService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubNoticeService#
	 * insertClubNotice(com.chinamobile.hnu.xiangyu.club.pojo.ClubNotice)
	 */
	@Transactional
	@Override
	public void insertClubNotice(ClubNotice clubNotice) {
		// TODO Auto-generated method stub
		Date date = new Date();
		clubNotice.setGmtCreate(date);
		noticeMapper.insertSelective(clubNotice);
		// 发布记录
		ClubRelease release = new ClubRelease();
		release.setBzid(clubNotice.getId());
		release.setType((byte) 3);
		release.setClubId(clubNotice.getClubId());
		release.setGmtCreate(date);
		releaseMapper.insert(release);

		// 加经验值
		pointService.addPoint(clubNotice.getPresentor(), PointRuleType.TYPE4.getValue(), clubNotice.getId().toString(),
				ExtidType.TYPE1.getValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubNoticeService#
	 * updateClubNotice(com.chinamobile.hnu.xiangyu.club.pojo.ClubNotice)
	 */
	@Override
	public void updateClubNotice(ClubNotice clubNotice) {
		// TODO Auto-generated method stub
		clubNotice.setGmtModified(new Date());
		noticeMapper.updateByPrimaryKeySelective(clubNotice);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubNoticeService#
	 * selectClubNoticeById(java.lang.Long)
	 */
	@Override
	public ClubNotice selectClubNoticeById(Long id) {
		// TODO Auto-generated method stub
		return noticeMapper.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubNoticeService#
	 * deleteClubNoticeById(java.lang.Long)
	 */
	@Transactional
	@Override
	public void deleteClubNoticeById(Long id) {
		// TODO Auto-generated method stub
		noticeMapper.deleteByPrimaryKey(id);
		releaseMapper.delete(id, 3);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubNoticeService#selectByPage(
	 * java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<ClubNoticeVo> selectByPage(Integer pageCurrent, Integer pageSize, Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<ClubNoticeVo> list = noticeMapper.selectByPage(map);
		return new Page<>(list);
	}

}
