/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinamobile.hnu.xiangyu.club.dao.ClubActivityApplicantMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubActivityMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubActivityPhotoMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubInfoMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubLabelMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubMemberMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubNoticeMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubSpeakLikeMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubSpeakMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubSpeakPhotoMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubVisitHistoryMapper;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubInfo;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubLabel;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubMember;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubNotice;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubVisitHistory;
import com.chinamobile.hnu.xiangyu.club.pojo.Photo;
import com.chinamobile.hnu.xiangyu.club.service.ClubBaseService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubActivityVo;
import com.chinamobile.hnu.xiangyu.club.vo.ClubSpeakVo;
import com.chinamobile.hnu.xiangyu.club.vo.ClubVo;
import com.chinamobile.hnu.xiangyu.club.vo.LikeVo;
import com.chinamobile.hnu.xiangyu.club.vo.Visibility;
import com.chinamobile.hnu.xiangyu.common.dao.SysUserNewsMapper;
import com.chinamobile.hnu.xiangyu.common.enums.ExtidType;
import com.chinamobile.hnu.xiangyu.common.enums.PointRuleType;
import com.chinamobile.hnu.xiangyu.common.pojo.SysUserNews;
import com.chinamobile.hnu.xiangyu.common.service.PubPointService;
import com.chinamobile.hnu.xiangyu.user.dao.UserAccountMapper;
import com.chinamobile.hnu.xiangyu.user.dao.UserFavoriteMapper;
import com.chinamobile.hnu.xiangyu.user.dao.UserHistoricalFootprintMapper;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.pojo.UserFavorite;
import com.chinamobile.hnu.xiangyu.user.pojo.UserHistoricalFootprint;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.Utils;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.web.api.vo.AclubInfoVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.AclubVo;
import com.github.pagehelper.PageHelper;
import com.um.pojo.Alarm;
import com.um.service.UmengService;

/**
 * @author The Old Man and the Sea
 *
 *         2018年5月28日
 */
@Service
public class ClubBaseServiceImpl implements ClubBaseService {
	private final Logger logger = Logger.getLogger(ClubBaseService.class);
	@Autowired
	private ClubActivityApplicantMapper applicantMapper;

	@Autowired
	private ClubInfoMapper mapper;

	@Autowired
	private ClubLabelMapper labelMapper;

	@Autowired
	private ClubMemberMapper memberMapper;

	@Autowired
	private ClubNoticeMapper noticeMapper;

	@Autowired
	private ClubVisitHistoryMapper visitMapper;

	@Autowired
	private UserFavoriteMapper favoriteMapper;

	@Autowired
	private ClubSpeakMapper speakMapper;

	@Autowired
	private ClubSpeakPhotoMapper sPhotoMapper;

	@Autowired
	private ClubActivityMapper activityMapper;

	@Autowired
	private ClubActivityPhotoMapper aPhotoMapper;

	@Autowired
	private UserHistoricalFootprintMapper footprintMapper;

	@Autowired
	private ClubSpeakLikeMapper likeMapper;

	@Autowired
	protected SysUserNewsMapper userNewsMapper;

	@Autowired
	private PubPointService pointService;

	@Autowired
	private UserAccountMapper accoutMapper;

	@Autowired
	private UmengService umengService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#insertClubInfo(
	 * com.chinamobile.hnu.xiangyu.club.pojo.ClubInfo)
	 */
	@Transactional
	@Override
	public void insertClubInfo(ClubInfo clubInfo) {
		// TODO Auto-generated method stub
		Date date = new Date();
		clubInfo.setGmtCreate(date);
		clubInfo.setGmtModified(date);
		// 新增团队信息，并返回id
		mapper.insertSelective(clubInfo);
		// 新增团队标签
		String[] lables = clubInfo.getLabels().split(",");
		if (lables.length > 0) {
			List<ClubLabel> list = new ArrayList<>();
			for (String lable : lables) {
				if (StringUtils.isNotBlank(lable)) {
					ClubLabel clable = new ClubLabel(clubInfo.getId(), lable);
					list.add(clable);
				}
			}
			if (list.size() > 0) {
				labelMapper.insertList(list);
			}
		}
		// 新增团队成员
		ClubMember member = new ClubMember();
		member.setClubId(clubInfo.getId());
		member.setJoinTime(date);
		member.setMemberId(clubInfo.getUser().getId());
		member.setNickname(clubInfo.getUser().getNickname());
		member.setRole((byte) 1);
		memberMapper.insertSelective(member);
		// 增加经验值
		pointService.addPoint(clubInfo.getCreator(),
				PointRuleType.TYPE1.getValue(), clubInfo.getId().toString(),
				ExtidType.TYPE2.getValue());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#updateClubInfo(
	 * com.chinamobile.hnu.xiangyu.club.pojo.ClubInfo)
	 */
	@Transactional
	@Override
	public void updateClubInfo(ClubInfo clubInfo) {
		// TODO Auto-generated method stub
		clubInfo.setGmtModified(new Date());
		mapper.updateByPrimaryKeySelective(clubInfo);
		//2018/11/1修改内容，增加修改团队标签功能
		if(StringUtils.isNotBlank(clubInfo.getLabels())){
			String[] lables = clubInfo.getLabels().split(",");
			if (lables.length > 0) {
				List<ClubLabel> list = new ArrayList<>();
				for (String lable : lables) {
					if (StringUtils.isNotBlank(lable)) {
						ClubLabel clable = new ClubLabel(clubInfo.getId(), lable);
						list.add(clable);
					}
				}
				if (list.size() > 0) {
					labelMapper.deleteByClubId(clubInfo.getId());
					labelMapper.insertList(list);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#selectDetailById
	 * (java.lang.Integer)
	 */
	@Override
	public ClubVo selectDetailById(Integer id, Integer uid) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();
		// 团队基础信息
		ClubInfo clubInfo = mapper.selectByPrimaryKey(id);

		if (clubInfo != null) {
			// 查询用户今日是否访问过该团队
			ClubVisitHistory visit = visitMapper.selectDayVisitToUser(uid, id,
					Utils.formatSimple(date));
			if (visit == null) {
				// 访问记录
				visit = new ClubVisitHistory();
				visit.setClubId(id);
				visit.setMemberId(uid);
				visit.setVisitTime(date);
				visitMapper.insertSelective(visit);
				// 访问足迹
				UserHistoricalFootprint footprint = new UserHistoricalFootprint();
				footprint.setUserId(uid);
				footprint.setAcrossId(2);
				footprint.setHistoricalFootprintId(Long.valueOf(id));
				footprint.setInterviewTime(date);
				footprintMapper.insertSelective(footprint);
			}

			ClubVo vo = new ClubVo();
			BeanUtils.copyProperties(vo, clubInfo);
			if (StringUtils.isNotBlank(vo.getLogoImage())) {
				vo.setLogoImage(UploadDocumentUtil.buildPublicPath(vo
						.getLogoImage()));
			}
			if (StringUtils.isNotBlank(vo.getCoverImage())) {
				vo.setCoverImage(UploadDocumentUtil.buildPublicPath(vo
						.getCoverImage()));
			}
			if (StringUtils.isNotBlank(vo.getQrcodeImage())) {
				vo.setQrcodeImage(UploadDocumentUtil.buildPublicPath(vo
						.getQrcodeImage()));
			}
			// 团队标签
			List<ClubLabel> labels = labelMapper.selectByClubId(clubInfo
					.getId());
			vo.setLabels(labels);
			// 团队最上级成员，默认前6位
			List<ClubMember> users = memberMapper.selectTopByClubId(clubInfo
					.getId());
			// 查询用户是否是群成员
			ClubMember member = memberMapper.selectUserJoin(id, uid);
			for (ClubMember clubMember : users) {
				if (StringUtils.isNotBlank(clubMember.getHeader())) {
					clubMember.setHeader(UploadDocumentUtil
							.buildPublicPath(clubMember.getHeader()));
				}
				if (member == null
						|| StringUtils.isBlank(clubMember.getNickname())) {
					clubMember.setNickname(clubMember.getUserName());
				}
			}
			vo.setUsers(users);
			// 最新公告
			ClubNotice notice = noticeMapper
					.selectTopByClubId(clubInfo.getId());
			vo.setNotice(notice);
			vo.setIsJoin(member == null ? (byte) 0 : (byte) 1);
			UserFavorite favorite = favoriteMapper.selectByClubId(
					clubInfo.getId(), uid);
			vo.setIsFavorite(Byte.valueOf(favorite == null ? "0" : "1"));
			return vo;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#favorite(com.
	 * chinamobile.hnu.xiangyu.user.pojo.UserFavorite, java.lang.Integer)
	 */
	@Override
	public void favorite(UserFavorite favorite, Integer op) {
		// TODO Auto-generated method stub
		if (op == 1) {
			favorite.setGmtCreate(new Date());
			favoriteMapper.insertSelective(favorite);
		} else if (op == 2) {
			favoriteMapper.delete(favorite);
		} else {
			return;
		}
		mapper.updateCollection(favorite.getBizId(), op);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#selectClubInfoId
	 * (java.lang.Integer)
	 */
	@Override
	public Visibility selectVisibilityId(Integer id) throws Exception {
		// TODO Auto-generated method stub
		ClubInfo clubInfo = mapper.selectByPrimaryKey(id);
		Visibility vi = null;
		if (clubInfo != null) {
			vi = new Visibility();
			BeanUtils.copyProperties(vi, clubInfo);
		}
		return vi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#insertVisibility
	 * (com.chinamobile.hnu.xiangyu.club.vo.Visibility)
	 */
	@Override
	public void insertVisibility(Visibility vi) throws Exception {
		// TODO Auto-generated method stub
		ClubInfo record = new ClubInfo();
		record.setId(vi.getId());
		if (vi.getSettingActivity() != null) {
			record.setSettingActivity(vi.getSettingActivity());
		}
		if (vi.getSettingNotice() != null) {
			record.setSettingNotice(vi.getSettingNotice());
		}
		if (vi.getSettingSpeak() != null) {
			record.setSettingSpeak(vi.getSettingSpeak());
		}
		if (vi.getSettingVisible() != null) {
			record.setSettingVisible(vi.getSettingVisible());
		}
		if (vi.getSettingVote() != null) {
			record.setSettingVote(vi.getSettingVote());
		}

		mapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#selectByPage(
	 * java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<ClubVo> selectByPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer uid = (Integer) map.get("uid");
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<ClubVo> list = mapper.selectByPage(map);

		if (list.size() > 0) {
			Map<Integer, ClubVo> voMap = new HashMap<>();
			// 团言团队keyset
			Set<Integer> speakSet = new HashSet<>();
			// 活动团队keyset
			Set<Integer> activitySet = new HashSet<>();
			for (ClubVo clubVo : list) {
				voMap.put(clubVo.getId(), clubVo);
				if (StringUtils.isNotBlank(clubVo.getLogoImage())) {
					clubVo.setLogoImage(UploadDocumentUtil
							.buildPublicPath(clubVo.getLogoImage()));
				}
				// 如果设置了团言或活动不可见
				if (1 != clubVo.getSettingSpeak()) {
					speakSet.add(clubVo.getId());
				}
				if (1 != clubVo.getSettingActivity()) {
					activitySet.add(clubVo.getId());
				}
			}

			if (((int) map.get("type") != 3 && (int) map.get("type") != 5)
					|| map.get("my") != null) {
				return new Page<>(list);
			}

			Map<Integer, ClubSpeakVo> speakMap = new HashMap<>();
			if (speakSet.size() > 0) {
				// 团队的最新团言
				List<ClubSpeakVo> speakVo = speakMapper
						.selectTopClubSpeakBySet(speakSet);
				for (ClubSpeakVo clubSpeakVo : speakVo) {
					speakMap.put(clubSpeakVo.getClubId(), clubSpeakVo);
				}
			}

			Map<Integer, ClubActivityVo> activityMap = new HashMap<>();
			if (activitySet.size() > 0) {
				// 团队最新的活动
				List<ClubActivityVo> activityVo = activityMapper
						.selectTopClubActivityBySet(activitySet);
				for (ClubActivityVo clubActivityVo : activityVo) {
					activityMap.put(clubActivityVo.getClubId(), clubActivityVo);
				}
			}

			// 判断团言和活动的发布时间哪个为最新发布的
			// 团言id
			Map<Long, ClubVo> speakIdSet = new HashMap<Long, ClubVo>();
			// 活动id
			Map<Long, ClubVo> activityIdSet = new HashMap<Long, ClubVo>();
			// 需要查询团队标签的团队
			Set<Integer> clubIdSet = new HashSet<Integer>();
			if (speakMap.size() > 0 || activityMap.size() > 0) {
				for (ClubVo clubVo : list) {
					ClubSpeakVo speak = speakMap.get(clubVo.getId());
					ClubActivityVo activity = activityMap.get(clubVo.getId());
					if (speak != null && activity != null) {
						if (speak.getGmtCreate().getTime() >= activity
								.getGmtCreate().getTime()) {
							if (StringUtils.isNotBlank(speak.getHeader())) {
								speak.setHeader(UploadDocumentUtil
										.buildPublicPath(speak.getHeader()));
							}
							clubVo.setSpeak(speak);
							speakIdSet.put(speak.getId(), clubVo);
						} else {
							if (StringUtils.isNotBlank(activity.getHeader())) {
								activity.setHeader(UploadDocumentUtil
										.buildPublicPath(activity.getHeader()));
							}
							clubVo.setActivity(activity);
							activityIdSet.put(activity.getId(), clubVo);
						}
					} else if (speak != null && activity == null) {
						clubVo.setSpeak(speak);
						speakIdSet.put(speak.getId(), clubVo);
					} else if (speak == null && activity != null) {
						clubVo.setActivity(activity);
						activityIdSet.put(activity.getId(), clubVo);
					}
					if (clubVo.getSpeak() != null
							|| clubVo.getActivity() != null) {
						clubIdSet.add(clubVo.getId());
					}
				}
			}

			// 查询团队标签
			if (clubIdSet.size() > 0) {
				List<ClubLabel> labeles = labelMapper
						.selectByClubIdList(clubIdSet);
				for (ClubLabel clubLabel : labeles) {
					if (null == voMap.get(clubLabel.getClubId())) {
						break;
					}
					List<ClubLabel> labels = voMap.get(clubLabel.getClubId())
							.getLabels();
					if (labels == null) {
						labels = new ArrayList<>();
						labels.add(clubLabel);
						voMap.get(clubLabel.getClubId()).setLabels(labels);
					} else {
						labels.add(clubLabel);
					}
				}
			}

			// 查询团言照片信息
			if (speakIdSet.size() > 0) {
				List<Photo> photoes = sPhotoMapper
						.selectBySpeakIdList(speakIdSet.keySet());
				for (Photo clubSpeakPhoto : photoes) {
					if (StringUtils.isNotBlank(clubSpeakPhoto.getPhotoId())) {
						clubSpeakPhoto.setPhotoId(UploadDocumentUtil
								.buildPublicPath(clubSpeakPhoto.getPhotoId()));
					}
					List<Photo> photos = speakIdSet
							.get(clubSpeakPhoto.getBzId()).getSpeak()
							.getPhotos();
					if (photos == null) {
						photos = new ArrayList<>();
						photos.add(clubSpeakPhoto);
						speakIdSet.get(clubSpeakPhoto.getBzId()).getSpeak()
								.setPhotos(photos);
					} else {
						photos.add(clubSpeakPhoto);
					}
				}

				// 点赞人
				List<LikeVo> likes = likeMapper.selectBySpeakIdList(speakIdSet
						.keySet());
				for (LikeVo likeVo : likes) {
					List<LikeVo> like = speakIdSet.get(likeVo.getBzId())
							.getSpeak().getLikes();
					if (like == null) {
						like = new ArrayList<LikeVo>();
						like.add(likeVo);
						speakIdSet.get(likeVo.getBzId()).getSpeak()
								.setLikes(like);
					} else {
						like.add(likeVo);
					}
					// 我是否点过赞
					if (likeVo.getUid().intValue() == uid.intValue()) {
						speakIdSet.get(likeVo.getBzId()).getSpeak()
								.setIsLike((byte) 1);
					}
				}
			}

			if (activityIdSet.size() > 0) {
				// 查询活动照片
				List<Photo> photoes = aPhotoMapper
						.selectByActivityIdList(activityIdSet.keySet());
				for (Photo clubActivityPhoto : photoes) {
					if (StringUtils.isNotBlank(clubActivityPhoto.getPhotoId())) {
						clubActivityPhoto
								.setPhotoId(UploadDocumentUtil
										.buildPublicPath(clubActivityPhoto
												.getPhotoId()));
					}
					List<Photo> photos = activityIdSet
							.get(clubActivityPhoto.getBzId()).getActivity()
							.getPhotos();
					if (photos == null) {
						photos = new ArrayList<>();
						photos.add(clubActivityPhoto);
						activityIdSet.get(clubActivityPhoto.getBzId())
								.getActivity().setPhotos(photos);
					} else {
						photos.add(clubActivityPhoto);
					}
				}

				// 参加人
				List<LikeVo> likes = applicantMapper
						.selectByActivityIdList(activityIdSet.keySet());
				for (LikeVo likeVo : likes) {
					if (StringUtils.isNotBlank(likeVo.getHeader())) {
						likeVo.setHeader(UploadDocumentUtil
								.buildPublicPath(likeVo.getHeader()));
					}
					List<LikeVo> like = activityIdSet.get(likeVo.getBzId())
							.getActivity().getLikes();
					if (like == null) {
						like = new ArrayList<LikeVo>();
						like.add(likeVo);
						activityIdSet.get(likeVo.getBzId()).getActivity()
								.setLikes(like);
					} else {
						like.add(likeVo);
					}
					// 我是否参加过
					if (likeVo.getUid().intValue() == uid) {
						activityIdSet.get(likeVo.getBzId()).getActivity()
								.setIsLike((byte) 1);
					}
				}
			}

		}

		return new Page<>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#
	 * selectFavoriteByClubId(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public UserFavorite selectFavoriteByClubId(Integer clubId, Integer uid) {
		// TODO Auto-generated method stub
		return favoriteMapper.selectByClubId(clubId, uid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#
	 * updateClubYesterdayTraffic()
	 */
	@Transactional
	@Override
	public void updateClubYesterdayTraffic() {
		// TODO Auto-generated method stub
		mapper.updateYesterdayTrafficToZero();
		Date today = new Date();
		Date yesterday = new Date(today.getTime() - 24 * 60 * 60 * 1000);
		List<ClubVisitHistory> list = visitMapper.selectAllYesterdayVisit(
				Utils.formatSimple(today), Utils.formatSimple(yesterday));
		if (list.size() > 0) {
			mapper.updateYesterdayTrafficByList(list);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#
	 * selectClubInfoById(java.lang.Integer)
	 */
	@Override
	public ClubInfo selectClubInfoById(Integer clubId) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(clubId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#selectByclubList
	 * (java.util.Map)
	 */
	@Override
	public Page<AclubVo> selectByclubList(Map<String, Object> map,
			Integer pageCurrent, Integer pageSize) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<AclubVo> clubVo = mapper.selectByclubList(map);
		return new Page<>(clubVo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#
	 * selectClubInfoByclubId(java.lang.Integer)
	 */
	@Override
	public AclubInfoVo selectClubInfoByclubId(Integer clubId) {
		AclubInfoVo aif = mapper.selectClubInfoByclubId(clubId);
		if (StringUtils.isNoneBlank(aif.getLogoImage())) {
			aif.setLogoImage(UploadDocumentUtil.buildPublicPath(aif
					.getLogoImage()));
		}
		if (StringUtils.isNoneBlank(aif.getCoverImage())) {
			aif.setCoverImage(UploadDocumentUtil.buildPublicPath(aif
					.getCoverImage()));
		}
		if (StringUtils.isNoneBlank(aif.getQrcodeImage())) {
			aif.setQrcodeImage(UploadDocumentUtil.buildPublicPath(aif
					.getQrcodeImage()));
		}
		return aif;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#savaSysUserNews
	 * (com.chinamobile.hnu.xiangyu.common.pojo.SysUserNews)
	 */
	@Override
	public void savaSysUserNews(SysUserNews userNews, Integer id,
			Map<String, Object> map) {
		ClubInfo ci = mapper.selectByPrimaryKey(id);
		if (ci.getCreator() != null) {
			userNews.setReceiveId(ci.getCreator());
			userNewsMapper.insertSelective(userNews);
			// 友盟推送消息
			try {
				Alarm alarm = new Alarm();
				alarm.setTitle("有人申请加入团队");
				alarm.setType(1);
				alarm.setMsg("有人申请加入团队:" + userNews.getContent());
				alarm.setReceiveId(ci.getCreator());
				umengService.sendAndroidBroadcast(alarm);
				umengService.sendIOSCustomizedcast(alarm);
			} catch (Exception e) {
				logger.error("send umeng msg  is fail", e);
			}
		}
		// SysUserNews news = userNewsMapper.selectUserByBizId(map);
		// if (news == null) {
		// } else {
		// map.put("gmtCreate", new Date());
		// userNewsMapper.updateUserStatus(map);
		// }

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#
	 * selectByLablePage (java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<ClubVo> selectByLabelPage(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<ClubVo> list = mapper.selectByLabelPage(map);
		for (ClubVo clubVo : list) {
			if (StringUtils.isNotBlank(clubVo.getLogoImage())) {
				clubVo.setLogoImage(UploadDocumentUtil.buildPublicPath(clubVo
						.getLogoImage()));
			}
		}
		return new Page<>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#selectByGzhPage(
	 * java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<ClubVo> selectByGzhPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<ClubVo> list = mapper.selectByGzhPage(map);
		for (ClubVo clubVo : list) {
			if (StringUtils.isNotBlank(clubVo.getLogoImage())) {
				clubVo.setLogoImage(UploadDocumentUtil.buildPublicPath(clubVo
						.getLogoImage()));
			}
		}
		return new Page<>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#
	 * selectGzhDetailById(java.lang.Integer)
	 */
	@Override
	public ClubVo selectGzhDetailById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		// 团队基础信息
		ClubInfo clubInfo = mapper.selectByPrimaryKey(id);
		ClubVo vo = new ClubVo();
		if (clubInfo != null) {
			BeanUtils.copyProperties(vo, clubInfo);
			if (StringUtils.isNotBlank(vo.getLogoImage())) {
				vo.setLogoImage(UploadDocumentUtil.buildPublicPath(vo
						.getLogoImage()));
			}
			if (StringUtils.isNotBlank(vo.getCoverImage())) {
				vo.setCoverImage(UploadDocumentUtil.buildPublicPath(vo
						.getCoverImage()));
			}
			// 最新公告
			ClubNotice notice = noticeMapper
					.selectTopByClubId(clubInfo.getId());
			vo.setNotice(notice);
			// 创建者信息
			UserAccount userInfo = accoutMapper.selectByPrimaryKey(clubInfo
					.getCreator());
			vo.setCreatorName(userInfo.getNickname());
		}
		return vo;
	}

	/* (non-Javadoc)
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubBaseService#selectClubInfoByMap(java.util.Map)
	 */
	@Override
	public List<ClubInfo> selectClubInfoByMap(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return mapper.selectClubInfoByMap(params);
	}
}
