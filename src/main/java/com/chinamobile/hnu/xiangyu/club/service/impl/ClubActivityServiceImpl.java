/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinamobile.hnu.xiangyu.club.dao.ClubActivityApplicantMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubActivityCommentMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubActivityMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubActivityPhotoMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubInfoMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubReleaseMapper;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubActivity;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubActivityApplicant;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubActivityComment;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubRelease;
import com.chinamobile.hnu.xiangyu.club.pojo.Photo;
import com.chinamobile.hnu.xiangyu.club.service.ClubActivityService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubActivityVo;
import com.chinamobile.hnu.xiangyu.club.vo.ClubVo;
import com.chinamobile.hnu.xiangyu.club.vo.CommentVo;
import com.chinamobile.hnu.xiangyu.club.vo.LikeVo;
import com.chinamobile.hnu.xiangyu.common.dao.SysUserNewsMapper;
import com.chinamobile.hnu.xiangyu.common.enums.ExtidType;
import com.chinamobile.hnu.xiangyu.common.enums.PointRuleType;
import com.chinamobile.hnu.xiangyu.common.pojo.SysUserNews;
import com.chinamobile.hnu.xiangyu.common.service.PubPointService;
import com.chinamobile.hnu.xiangyu.user.dao.UserAccountMapper;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.service.UserService;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.web.api.vo.AclubActivityInfoVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.AclubActivityVo;
import com.github.pagehelper.PageHelper;

/**
 * @author The Old Man and the Sea
 *
 *         2018年6月1日
 */
@Service
public class ClubActivityServiceImpl implements ClubActivityService {

	@Autowired
	private ClubActivityMapper activityMapper;

	@Autowired
	private ClubActivityPhotoMapper photoMapper;

	@Autowired
	private ClubActivityCommentMapper commentMapper;

	@Autowired
	private ClubActivityApplicantMapper applicantMapper;

	@Autowired
	private ClubReleaseMapper releaseMapper;

	@Autowired
	private ClubInfoMapper clubMapper;

	@Autowired
	protected SysUserNewsMapper userNewsMapper;

	@Autowired
	private PubPointService pointService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserAccountMapper accoutMapper;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubActivityService#
	 * insertClubActivity(com.chinamobile.hnu.xiangyu.club.pojo.ClubActivity,
	 * java.util.List)
	 */
	@Transactional
	@Override
	public void insertClubActivity(ClubActivity clubActivity) {
		// TODO Auto-generated method stub
		Date date = new Date();
		clubActivity.setGmtCreate(date);
		activityMapper.insertSelective(clubActivity);

		// 发布记录
		ClubRelease release = new ClubRelease();
		release.setBzid(clubActivity.getId());
		release.setType((byte) 2);
		release.setClubId(clubActivity.getClubId());
		release.setGmtCreate(date);
		releaseMapper.insert(release);

		List<Photo> photos = clubActivity.getPhotos();
		if (photos != null && photos.size() > 0) {
			for (Photo clubActivityPhoto : photos) {
				clubActivityPhoto.setBzId(clubActivity.getId());
			}
			photoMapper.inserList(photos);

			photos.forEach(x -> {
				x.setPhotoId(UploadDocumentUtil.buildPublicPath(x.getPhotoId()));
			});
			clubActivity.setPhotos(photos);
		}

		// 加经验值
		pointService.addPoint(clubActivity.getPresentor(), PointRuleType.TYPE5.getValue(),
				clubActivity.getId().toString(), ExtidType.TYPE2.getValue());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubActivityService#
	 * updateClubActivity(com.chinamobile.hnu.xiangyu.club.pojo.ClubActivity,
	 * java.util.List, java.lang.String)
	 */
	@Transactional
	@Override
	public void updateClubActivity(ClubActivity clubActivity, String delFileIds) {
		// TODO Auto-generated method stub
		clubActivity.setGmtModified(new Date());
		activityMapper.updateByPrimaryKeySelective(clubActivity);
		List<Photo> photos = clubActivity.getPhotos();
		if (photos != null && photos.size() > 0) {
			for (Photo clubActivityPhoto : photos) {
				clubActivityPhoto.setBzId(clubActivity.getId());
			}
			photoMapper.inserList(photos);
			photos.forEach(x -> {
				x.setPhotoId(UploadDocumentUtil.buildPublicPath(x.getPhotoId()));
			});
			clubActivity.setPhotos(photos);
		}
		if (StringUtils.isNotBlank(delFileIds)) {
			photoMapper.deleteByIdList(Arrays.asList(delFileIds.split(",")));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubActivityService#
	 * insertClubActivityComment(com.chinamobile.hnu.xiangyu.club.pojo.
	 * ClubActivityComment)
	 */
	@Transactional
	@Override
	public void insertClubActivityComment(ClubActivityComment clubActivityComment) {
		// TODO Auto-generated method stub
		clubActivityComment.setGmtCreate(new Date());
		commentMapper.insertSelective(clubActivityComment);
		activityMapper.updateComment(clubActivityComment.getActivityId(), 1);

		// 保存评论消息记录
		ClubActivity ai = activityMapper.selectByPrimaryKey(clubActivityComment.getActivityId());
		if (ai.getPresentor() != null) {
			if (!ai.getPresentor().equals(clubActivityComment.getPresentor())) {
				SysUserNews userNews = new SysUserNews();
				userNews.setBizId(clubActivityComment.getActivityId());
				userNews.setReplyId(clubActivityComment.getPresentor());
				userNews.setReceiveId(ai.getPresentor());
				userNews.setContent(clubActivityComment.getContent());
				userNews.setGmtCreate(new Date());
				userNews.setCategory(1);
				userNewsMapper.insertSelective(userNews);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubActivityService#
	 * selectClubActivityById(java.lang.Long)
	 */
	@Override
	public ClubActivity selectClubActivityById(Long id) {
		// TODO Auto-generated method stub
		return activityMapper.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubActivityService#
	 * deleteClubActivityById(java.lang.Long)
	 */
	@Transactional
	@Override
	public void deleteClubActivityById(Long id) {
		// TODO Auto-generated method stub
		activityMapper.deleteByPrimaryKey(id);
		releaseMapper.delete(id, 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubActivityService#
	 * selectClubActivityCommentById(java.lang.Long)
	 */
	@Override
	public ClubActivityComment selectClubActivityCommentById(Long id) {
		// TODO Auto-generated method stub
		return commentMapper.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubActivityService#
	 * deleteClubActivityCommentById(java.lang.Long)
	 */
	@Transactional
	@Override
	public void deleteClubActivityCommentById(Long id, Long aid) {
		// TODO Auto-generated method stub
		commentMapper.deleteByPrimaryKey(id);
		activityMapper.updateComment(aid, 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubActivityService#
	 * selectClubActivityApplicantById(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public ClubActivityApplicant selectClubActivityApplicantById(Long activityId, Integer uid) {
		// TODO Auto-generated method stub
		return applicantMapper.selectByPrimaryKey(activityId, uid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubActivityService#
	 * updateClubActivityApplicant(com.chinamobile.hnu.xiangyu.club.pojo.
	 * ClubActivityApplicant, java.lang.Integer)
	 */
	@Transactional
	@Override
	public void updateClubActivityApplicant(ClubActivityApplicant applicant, Integer op) {
		// TODO Auto-generated method stub
		if (op == 1) {
			applicant.setGmtCreate(new Date());
			applicantMapper.insertSelective(applicant);
			// 加经验值
			pointService.addPoint(applicant.getMemberId(), PointRuleType.TYPE5.getValue(), applicant.getId().toString(),
					ExtidType.TYPE1.getValue());
		} else {
			applicantMapper.deleteByPrimaryKey(applicant.getActivityId(), applicant.getMemberId());
		}
		activityMapper.updateLike(applicant.getActivityId(), op);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubActivityService#selectByPage
	 * (java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<ClubActivityVo> selectByPage(Integer pageCurrent, Integer pageSize, Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<ClubActivityVo> list = activityMapper.selectByPage(map);
		this.setList(list, (int) map.get("uid"));
		return new Page<>(list);
	}

	private void setList(List<ClubActivityVo> list, int uid) {
		if (list.size() > 0) {
			userService.setFriendNickName(list, uid);
			Map<Long, ClubActivityVo> voMap = new HashMap<>();
			for (ClubActivityVo clubActivityVo : list) {
				voMap.put(clubActivityVo.getId(), clubActivityVo);
				if (StringUtils.isNotBlank(clubActivityVo.getHeader())) {
					clubActivityVo.setHeader(UploadDocumentUtil.buildPublicPath(clubActivityVo.getHeader()));
				}
			}

			// 活动的照片
			List<Photo> photos = photoMapper.selectByActivityIdList(voMap.keySet());
			for (Photo clubActivityPhoto : photos) {
				List<Photo> photoes = voMap.get(clubActivityPhoto.getBzId()).getPhotos();
				clubActivityPhoto.setPhotoId(UploadDocumentUtil.buildPublicPath(clubActivityPhoto.getPhotoId()));
				if (photoes == null) {
					photoes = new ArrayList<>();
					photoes.add(clubActivityPhoto);
					voMap.get(clubActivityPhoto.getBzId()).setPhotos(photoes);
				} else {
					photoes.add(clubActivityPhoto);
				}

			}

			// 参加人
			List<LikeVo> likes = applicantMapper.selectByActivityIdList(voMap.keySet());
			if (likes.size() > 0) {
				userService.setFriendNickName(likes, uid);
				for (LikeVo likeVo : likes) {
					if (StringUtils.isNotBlank(likeVo.getHeader())) {
						likeVo.setHeader(UploadDocumentUtil.buildPublicPath(likeVo.getHeader()));
					}
					List<LikeVo> like = voMap.get(likeVo.getBzId()).getLikes();
					if (like == null) {
						like = new ArrayList<LikeVo>();
						like.add(likeVo);
						voMap.get(likeVo.getBzId()).setLikes(like);
					} else {
						like.add(likeVo);
					}
					// 我是否参加过
					if (likeVo.getUid().intValue() == uid) {
						voMap.get(likeVo.getBzId()).setIsLike((byte) 1);
					}
				}
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubActivityService#
	 * selectCommentByPage(java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<CommentVo> selectCommentByPage(Integer pageCurrent, Integer pageSize, Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<CommentVo> list = commentMapper.selectByPage(map);
		for (CommentVo vo : list) {
			if (StringUtils.isNotBlank(vo.getHeader())) {
				vo.setHeader(UploadDocumentUtil.buildPublicPath(vo.getHeader()));
			}
		}
		return new Page<>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubActivityService#
	 * selectClubActivityDetailById(java.lang.Long)
	 */
	@Override
	public ClubActivityVo selectClubActivityDetailById(Long id, Integer uid) {
		// TODO Auto-generated method stub
		ClubActivityVo clubActivityVo = activityMapper.selectDetail(id);
		if (clubActivityVo != null) {
			userService.setFriendNickName(clubActivityVo, uid);
			if (StringUtils.isNotBlank(clubActivityVo.getHeader())) {
				clubActivityVo.setHeader(UploadDocumentUtil.buildPublicPath(clubActivityVo.getHeader()));
			}
			// 活动的照片
			Set<Long> set = new HashSet<>();
			set.add(clubActivityVo.getId());
			List<Photo> photos = photoMapper.selectByActivityIdList(set);
			for (Photo clubActivityPhoto : photos) {
				clubActivityPhoto.setPhotoId(UploadDocumentUtil.buildPublicPath(clubActivityPhoto.getPhotoId()));
			}
			if (photos.size() > 0) {
				clubActivityVo.setPhotos(photos);
			}
			// 参加人
			List<LikeVo> likes = applicantMapper.selectByActivityIdList(set);
			if (likes.size() > 0) {
				userService.setFriendNickName(likes, uid);
				for (LikeVo likeVo : likes) {
					if (StringUtils.isNotBlank(likeVo.getHeader())) {
						likeVo.setHeader(UploadDocumentUtil.buildPublicPath(likeVo.getHeader()));
					}
					// 我是否参加过
					if (likeVo.getUid().intValue() == uid.intValue()) {
						clubActivityVo.setIsLike((byte) 1);
					}
				}
				clubActivityVo.setLikes(likes);
			}
		}
		return clubActivityVo;
	}

	@Override
	public Page<ClubActivityVo> selectHotByPage(Integer pageCurrent, Integer pageSize, Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<ClubActivityVo> list = activityMapper.selectByPage(map);
		this.setList(list, (int) map.get("uid"));
		if (list.size() > 0) {
			Set<Long> activitySet = new HashSet<>();
			for (ClubActivityVo clubActivityVo : list) {
				if (!activitySet.contains(Long.valueOf(clubActivityVo.getClubId()))) {
					activitySet.add(Long.valueOf(clubActivityVo.getClubId()));
				}
			}
			// 俱乐部信息
			List<ClubVo> clubList = clubMapper.selectByIdList(activitySet);
			if (clubList.size() > 0) {
				userService.setFriendNickName(clubList, (int) map.get("uid"));
				Map<Integer, ClubVo> clubMap = new HashMap<>();
				for (ClubVo clubVo : clubList) {
					clubMap.put(clubVo.getId(), clubVo);
					if (StringUtils.isNotBlank(clubVo.getLogoImage())) {
						clubVo.setLogoImage(UploadDocumentUtil.buildPublicPath(clubVo.getLogoImage()));
					}
				}
				// 赋值团队信息
				for (ClubActivityVo clubActivityVo : list) {
					clubActivityVo.setClubInfo(clubMap.get(clubActivityVo.getClubId()));
				}
			}
		}
		return new Page<>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubActivityService#
	 * selectActivityList(java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<AclubActivityVo> selectActivityList(Integer pageCurrent, Integer pageSize, Map<String, Object> map) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<AclubActivityVo> activity = activityMapper.selectActivityList(map);
		return new Page<AclubActivityVo>(activity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubActivityService#
	 * selectActivityInfo(java.lang.Long)
	 */
	@Override
	public AclubActivityInfoVo selectActivityInfo(Long id) {
		AclubActivityInfoVo ai = activityMapper.selectActivityInfo(id);
		if (StringUtils.isNotBlank(ai.getHeader())) {
			ai.setHeader(UploadDocumentUtil.buildPublicPath(ai.getHeader()));
		}
		List<Photo> photos = photoMapper.selectByActivitydList(id);
		if (photos.size() > 0) {
			for (Photo clubActivityPhoto : photos) {
				clubActivityPhoto.setPhotoId(UploadDocumentUtil.buildPublicPath(clubActivityPhoto.getPhotoId()));
			}
			ai.setPhotos(photos);
		}

		return ai;
	}

	@Override
	public List<LikeVo> selectClubActivityMember(Long id) {
		// TODO Auto-generated method stub
		List<LikeVo> list = applicantMapper.selectList(id);
		for (LikeVo likeVo : list) {
			if (StringUtils.isNotBlank(likeVo.getHeader())) {
				likeVo.setHeader(UploadDocumentUtil.buildPublicPath(likeVo.getHeader()));
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubActivityService#
	 * selectHotByGzhPage(java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<ClubActivityVo> selectHotByGzhPage(Integer pageCurrent, Integer pageSize, Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<ClubActivityVo> list = activityMapper.selectByPage(map);
		if (list.size() > 0) {
			Map<Long, ClubActivityVo> voMap = new HashMap<>();
			for (ClubActivityVo clubActivityVo : list) {
				voMap.put(clubActivityVo.getId(), clubActivityVo);
				if (StringUtils.isNotBlank(clubActivityVo.getHeader())) {
					clubActivityVo.setHeader(UploadDocumentUtil.buildPublicPath(clubActivityVo.getHeader()));
				}
			}

			// 活动的照片
			List<Photo> photos = photoMapper.selectByActivityIdList(voMap.keySet());
			for (Photo clubActivityPhoto : photos) {
				List<Photo> photoes = voMap.get(clubActivityPhoto.getBzId()).getPhotos();
				clubActivityPhoto.setPhotoId(UploadDocumentUtil.buildPublicPath(clubActivityPhoto.getPhotoId()));
				if (photoes == null) {
					photoes = new ArrayList<>();
					photoes.add(clubActivityPhoto);
					voMap.get(clubActivityPhoto.getBzId()).setPhotos(photoes);
				} else {
					photoes.add(clubActivityPhoto);
				}
			}

		}
		return new Page<>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubActivityService#
	 * selectGzhClubActivityDetailById(java.lang.Long)
	 */
	@Override
	public ClubActivityVo selectGzhClubActivityDetailById(Long id) throws Exception {
		// TODO Auto-generated method stub
		ClubActivity clubActivity = activityMapper.selectByPrimaryKey(id);
		ClubActivityVo clubActivityVo = new ClubActivityVo();
		if (clubActivity != null) {
			BeanUtils.copyProperties(clubActivityVo, clubActivity);
			// 创建者信息
			UserAccount userInfo = accoutMapper.selectByPrimaryKey(clubActivityVo.getPresentor());
			clubActivityVo.setPresentorName(userInfo.getNickname());
			if (StringUtils.isNotBlank(userInfo.getHeader())) {
				if (StringUtils.isNotBlank(userInfo.getHeader())) {
					clubActivityVo.setHeader(UploadDocumentUtil.buildPublicPath(userInfo.getHeader()));
				}
			}
			// 活动的照片
			Set<Long> set = new HashSet<>();
			set.add(clubActivity.getId());
			List<Photo> photos = photoMapper.selectByActivityIdList(set);
			for (Photo clubActivityPhoto : photos) {
				clubActivityPhoto.setPhotoId(UploadDocumentUtil.buildPublicPath(clubActivityPhoto.getPhotoId()));
			}
			if (photos.size() > 0) {
				clubActivityVo.setPhotos(photos);
			}
		}
		return clubActivityVo;
	}
}
