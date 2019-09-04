/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinamobile.hnu.xiangyu.club.dao.ClubReleaseMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubVoteInfoMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubVoteOptionMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubVoteQuestionMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClueVoteRecordMapper;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubRelease;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubVoteInfo;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubVoteOption;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubVoteQuestion;
import com.chinamobile.hnu.xiangyu.club.pojo.ClueVoteRecord;
import com.chinamobile.hnu.xiangyu.club.pojo.ClueVoteRecordKey;
import com.chinamobile.hnu.xiangyu.club.service.ClubVoteService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubVoteVo;
import com.chinamobile.hnu.xiangyu.common.enums.ExtidType;
import com.chinamobile.hnu.xiangyu.common.enums.PointRuleType;
import com.chinamobile.hnu.xiangyu.common.service.PubPointService;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.web.api.vo.ClubSpeackVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.VoteInfoVo;
import com.github.pagehelper.PageHelper;

/**
 * @author The Old Man and the Sea
 *
 *         2018年6月4日
 */
@Service
public class ClubVoteServiceImpl implements ClubVoteService {

	@Autowired
	private ClubVoteInfoMapper voteMapper;

	@Autowired
	private ClubVoteQuestionMapper questionMapper;

	@Autowired
	private ClubVoteOptionMapper optionMapper;

	@Autowired
	private ClueVoteRecordMapper recordMapper;

	@Autowired
	private ClubReleaseMapper releaseMapper;
	
	@Autowired
	private PubPointService pointService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubVoteService#insertClubVoteInfo
	 * (com.chinamobile.hnu.xiangyu.club.pojo.ClubVoteInfo)
	 */
	@Transactional
	@Override
	public void insertClubVoteInfo(ClubVoteInfo clubVoteInfo) {
		// TODO Auto-generated method stub
		Date date = new Date();
		clubVoteInfo.setGmtCreate(date);
		// 投票主干
		voteMapper.insertSelective(clubVoteInfo);
		// 发布记录
		ClubRelease release = new ClubRelease();
		release.setBzid(clubVoteInfo.getId());
		release.setType((byte) 1);
		release.setClubId(clubVoteInfo.getClubId());
		release.setGmtCreate(date);
		releaseMapper.insert(release);

		if (clubVoteInfo.getQuestions().size() > 0) {

			for (ClubVoteQuestion question : clubVoteInfo.getQuestions()) {
				question.setGmtCreate(date);
				question.setVoteId(clubVoteInfo.getId());
			}
			// 投票内容
			questionMapper.insertList(clubVoteInfo.getQuestions());

			// 投票选项
			List<ClubVoteOption> options = new ArrayList<>();
			for (ClubVoteQuestion question : clubVoteInfo.getQuestions()) {
				for (ClubVoteOption option : question.getOptions()) {
					option.setGmtCreate(date);
					option.setQuestionId(question.getId());
				}
				if (question.getOptions().size() > 0) {
					options.addAll(question.getOptions());
				}
			}
			if (options.size() > 0) {
				optionMapper.insertList(options);
			}
		}

		// 加经验值
		pointService.addPoint(clubVoteInfo.getPresentor(), PointRuleType.TYPE3.getValue(), clubVoteInfo.getId().toString(),
				ExtidType.TYPE2.getValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubVoteService#selectByPage
	 * (java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<ClubVoteVo> selectByPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<ClubVoteVo> list = voteMapper.selectByPage(map);
		if (list.size() > 0) {
			for (ClubVoteVo vo : list) {
				if (StringUtils.isNotBlank(vo.getHeader())) {
					vo.setHeader(UploadDocumentUtil.buildPublicPath(vo
							.getHeader()));
				}
			}
		}
		return new Page<>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubVoteService#selectClubVoteDetail
	 * (java.lang.Long, java.lang.Integer)
	 */
	@Override
	public ClubVoteVo selectClubVoteDetail(Long voteId, Integer uid) {
		// TODO Auto-generated method stub
		ClubVoteVo vo = new ClubVoteVo();
		ClueVoteRecordKey key = new ClueVoteRecordKey();
		key.setVoterId(voteId);
		key.setPresentor(uid);
		// 判断我是否参加过投票
		List<ClueVoteRecord> record = recordMapper.selectByPrimaryKey(key);
		vo.setIsVote(record.size() > 0 ? (byte) 1 : (byte) 0);
		// 查询投票内容
		List<ClubVoteQuestion> questions = questionMapper
				.selectByVoteId(voteId);
		if (questions.size() > 0) {
			// 把内容封装成map，方便后面赋值投票内容的选项
			Map<Long, ClubVoteQuestion> questionMap = questions.stream()
					.collect(
							Collectors.toMap(ClubVoteQuestion::getId,
									question -> question));
			// 投票选项
			List<ClubVoteOption> options = optionMapper
					.selectByQuestionIdList(questionMap.keySet());
			options.forEach(option -> {
				List<ClubVoteOption> optiones = questionMap.get(
						option.getQuestionId()).getOptions();
				if (optiones == null) {
					optiones = new ArrayList<>();
					optiones.add(option);
					questionMap.get(option.getQuestionId())
							.setOptions(optiones);
				} else {
					optiones.add(option);
				}
			});
			vo.setQuestions(questions);
		}

		return vo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubVoteService#
	 * selectSelectClubVoteById(java.lang.Integer)
	 */
	@Override
	public ClubVoteInfo selectClubVoteById(Long voteId) {
		// TODO Auto-generated method stub
		return voteMapper.selectByPrimaryKey(voteId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubVoteService#
	 * selectDeleteClubVoteById(java.lang.Long)
	 */
	@Transactional
	@Override
	public void deleteDeleteClubVoteById(Long voteId) {
		// TODO Auto-generated method stub
		voteMapper.deleteByPrimaryKey(voteId);
		releaseMapper.delete(voteId, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubVoteService#selectByPrimaryKey
	 * (com.chinamobile.hnu.xiangyu.club.pojo.ClueVoteRecordKey)
	 */
	@Override
	public List<ClueVoteRecord> selectByPrimaryKey(ClueVoteRecordKey key) {
		// TODO Auto-generated method stub
		return recordMapper.selectByPrimaryKey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubVoteService#updateClubVoteOption
	 * (java.lang.Long, java.lang.String)
	 */
	@Transactional
	@Override
	public void updateClubVoteOption(Long voteId, String optionId, Integer uid) {
		// TODO Auto-generated method stub
		List<String> optionIds = Arrays.asList(optionId.split(","));
		Date date = new Date();
		if (optionIds.size() > 0) {
			List<ClueVoteRecord> records = new ArrayList<>();
			for (String id : optionIds) {
				ClueVoteRecord record = new ClueVoteRecord();
				record.setGmtCreate(date);
				record.setOptionId(Long.valueOf(id));
				record.setVoterId(voteId);
				record.setPresentor(uid);
				records.add(record);
			}
			optionMapper.updateVoteAmount(optionIds);
			recordMapper.insertList(records);

		}
		// 加经验值
		pointService.addPoint(uid, PointRuleType.TYPE3.getValue(), voteId.toString(),
				ExtidType.TYPE1.getValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubVoteService#updateVoteStatus
	 * ()
	 */
	@Override
	public void updateVoteStatus() {
		// TODO Auto-generated method stub
		List<ClubVoteInfo> list = voteMapper.selectAllClubVote();
		// 需要修改状态为过期的投票
		List<Long> voteIds = new ArrayList<>();
		Date date = new Date();
		for (ClubVoteInfo clubVoteInfo : list) {
			if (date.getTime() >= clubVoteInfo.getDeadline().getTime()) {
				voteIds.add(clubVoteInfo.getId());
			}
		}
		if (voteIds.size() > 0) {
			voteMapper.updateStatus(voteIds);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubVoteService#selectVoteList
	 * (java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<ClubSpeackVo> selectVoteList(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<ClubSpeackVo> vo = voteMapper.selectVoteList(map);
		return new Page<ClubSpeackVo>(vo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubVoteService#
	 * selectVoteInfoByVoteId(java.lang.Long)
	 */
	@Override
	public List<VoteInfoVo> selectVoteInfoByVoteId(Long voteId) {
		List<VoteInfoVo> vo = voteMapper.selectVoteInfoByVoteId(voteId);
		if (vo.size() > 0) {
			for (VoteInfoVo info : vo) {
				info.setHeader(UploadDocumentUtil.buildPublicPath(info
						.getHeader()));
				List<ClubVoteOption> options = optionMapper
						.selectVoteOptionByQuestionId(info.getQuestionId());
				if (options.size() > 0) {
					info.setVoteOptions(options);
				}
				Integer voteCont = 0;
				for (ClubVoteOption op : options) {
					voteCont += op.getVoteAmount();
				}
				info.setVoteCount(voteCont);
			}
		}
		return vo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubVoteService#deleteById(java
	 * .lang.Long)
	 */
	@Override
	public int deleteById(ClubVoteInfo record) {
		return voteMapper.updateByPrimaryKeySelective(record);
	}

}
