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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinamobile.hnu.xiangyu.club.dao.ClubSpeakCommentMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubSpeakLikeMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubSpeakMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubSpeakPhotoMapper;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubSpeak;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubSpeakComment;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubSpeakLike;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubSpeakLikeKey;
import com.chinamobile.hnu.xiangyu.club.pojo.Photo;
import com.chinamobile.hnu.xiangyu.club.service.ClubBaseService;
import com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubSpeakVo;
import com.chinamobile.hnu.xiangyu.club.vo.CommentVo;
import com.chinamobile.hnu.xiangyu.club.vo.LikeVo;
import com.chinamobile.hnu.xiangyu.common.dao.SysUserNewsMapper;
import com.chinamobile.hnu.xiangyu.common.enums.ExtidType;
import com.chinamobile.hnu.xiangyu.common.enums.PointRuleType;
import com.chinamobile.hnu.xiangyu.common.pojo.SysUserNews;
import com.chinamobile.hnu.xiangyu.common.service.PubPointService;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.web.api.vo.ClubSpeackInfoVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.ClubSpeackVo;
import com.github.pagehelper.PageHelper;
import com.um.pojo.Alarm;
import com.um.service.UmengService;

/**
 * @author The Old Man and the Sea
 *
 *         2018年5月29日
 */
@Service
public class ClubSpeakServiceImpl implements ClubSpeakService {
	private final Logger logger = Logger.getLogger(ClubBaseService.class);
	@Autowired
	private ClubSpeakMapper speakMapper;

	@Autowired
	private ClubSpeakPhotoMapper photoMapper;

	@Autowired
	private ClubSpeakCommentMapper commentMapper;

	@Autowired
	private ClubSpeakLikeMapper likeMapper;

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
	 * com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService#insertClubSpeak
	 * (com.chinamobile.hnu.xiangyu.club.pojo.ClubSpeak,
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Transactional
	@Override
	public void insertClubSpeak(ClubSpeak clubSpeak) {
		// TODO Auto-generated method stub
		clubSpeak.setGmtCreate(new Date());
		speakMapper.insertSelective(clubSpeak);
		List<Photo> photos = clubSpeak.getPhotos();
		if (photos != null && photos.size() > 0) {
			for (Photo clubSpeakPhoto : photos) {
				clubSpeakPhoto.setBzId(clubSpeak.getId());
			}
			photoMapper.inserList(photos);
		}
		// 加经验值
		pointService.addPoint(clubSpeak.getPresentor(),
				PointRuleType.TYPE2.getValue(), clubSpeak.getId().toString(),
				ExtidType.TYPE1.getValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService#selectByPage
	 * (java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<ClubSpeakVo> selectByPage(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer uid = (Integer) map.get("uid");
		if (map.get("my") == null) {
			map.remove("uid");
		}
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<ClubSpeakVo> list = speakMapper.selectByPage(map);

		if (list.size() > 0) {
			Map<Long, ClubSpeakVo> voMap = new HashMap<>();
			for (ClubSpeakVo clubSpeakVo : list) {
				voMap.put(clubSpeakVo.getId(), clubSpeakVo);
				if (StringUtils.isNotBlank(clubSpeakVo.getHeader())) {
					clubSpeakVo.setHeader(UploadDocumentUtil
							.buildPublicPath(clubSpeakVo.getHeader()));
				}
			}
			// 团言的照片
			List<Photo> photos = photoMapper
					.selectBySpeakIdList(voMap.keySet());
			for (Photo clubSpeakPhoto : photos) {
				List<Photo> photoes = voMap.get(clubSpeakPhoto.getBzId())
						.getPhotos();
				clubSpeakPhoto.setPhotoId(UploadDocumentUtil
						.buildPublicPath(clubSpeakPhoto.getPhotoId()));
				if (photoes == null) {
					photoes = new ArrayList<>();
					photoes.add(clubSpeakPhoto);
					voMap.get(clubSpeakPhoto.getBzId()).setPhotos(photoes);
				} else {
					photoes.add(clubSpeakPhoto);
				}

			}

			// 点赞人
			List<LikeVo> likes = likeMapper.selectBySpeakIdList(voMap.keySet());
			for (LikeVo likeVo : likes) {
				List<LikeVo> like = voMap.get(likeVo.getBzId()).getLikes();
				if (like == null) {
					like = new ArrayList<LikeVo>();
					like.add(likeVo);
					voMap.get(likeVo.getBzId()).setLikes(like);
				} else {
					like.add(likeVo);
				}
				// 我是否点过赞
				if (likeVo.getUid().intValue() == uid.intValue()) {
					voMap.get(likeVo.getBzId()).setIsLike((byte) 1);
				}
			}

		}

		return new Page<>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService#
	 * insertClubSpeakComment
	 * (com.chinamobile.hnu.xiangyu.club.pojo.ClubSpeakComment)
	 */
	@Transactional
	@Override
	public void insertClubSpeakComment(ClubSpeakComment speakComment) {
		// TODO Auto-generated method stub
		speakComment.setGmtCreate(new Date());
		commentMapper.insertSelective(speakComment);
		speakMapper.updateComment(speakComment.getSpeakId(), 1);

		// 保存评论消息记录
		ClubSpeak ai = speakMapper
				.selectByPrimaryKey(speakComment.getSpeakId());
		if (ai.getPresentor() != null) {
			if (!ai.getPresentor().equals(speakComment.getPresentor())) {
				SysUserNews userNews = new SysUserNews();
				userNews.setBizId(speakComment.getSpeakId());
				userNews.setReplyId(speakComment.getPresentor());
				userNews.setReceiveId(ai.getPresentor());
				userNews.setContent(speakComment.getContent());
				userNews.setGmtCreate(new Date());
				// 1-团队消息；2-团言消息；3-问题消息；
				userNews.setCategory(1);
				userNewsMapper.insertSelective(userNews);
				// 友盟推送消息
				try {
					Alarm alarm = new Alarm();
					alarm.setTitle("有人评论了你的团言");
					alarm.setType(2);
					alarm.setMsg("有人评论了你的团言:" + speakComment.getContent());
					alarm.setReceiveId(ai.getPresentor());
					umengService.sendAndroidBroadcast(alarm);
					umengService.sendIOSCustomizedcast(alarm);
				} catch (Exception e) {
					logger.error("send umeng msg  is fail", e);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService#
	 * selectClubSpeakCommentById(java.lang.Long)
	 */
	@Override
	public ClubSpeak selectClubSpeakById(Long speakId) {
		// TODO Auto-generated method stub
		return speakMapper.selectByPrimaryKey(speakId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService#
	 * deleteClubSpeakById (java.lang.Long)
	 */
	@Override
	public void deleteClubSpeakById(Long speakId) {
		// TODO Auto-generated method stub
		speakMapper.deleteByPrimaryKey(speakId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService#
	 * selectCommentByPage (java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<CommentVo> selectCommentByPage(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map) {
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
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService#
	 * selectClubSpeakCommentById(java.lang.Long)
	 */
	@Override
	public ClubSpeakComment selectClubSpeakCommentById(Long id) {
		// TODO Auto-generated method stub
		return commentMapper.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService#
	 * deleteClubSpeakCommentById(java.lang.Long)
	 */
	@Override
	@Transactional
	public void deleteClubSpeakCommentById(Long id, Long sid) {
		// TODO Auto-generated method stub
		commentMapper.deleteByPrimaryKey(id);
		speakMapper.updateComment(sid, 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService#
	 * updateClubSpeakItsaid(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public void updateClubSpeakItsaid(Long speakId, Integer op) {
		// TODO Auto-generated method stub
		ClubSpeak record = new ClubSpeak();
		record.setId(speakId);
		record.setItsaidFlag(Byte.valueOf(op.toString()));
		speakMapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService#
	 * selectClubSpeakLikeById(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public ClubSpeakLike selectClubSpeakLikeById(Long speakId, Integer uid) {
		// TODO Auto-generated method stub
		ClubSpeakLikeKey key = new ClubSpeakLikeKey();
		key.setSpeakId(speakId);
		key.setMemberId(uid);
		return likeMapper.selectByPrimaryKey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService#
	 * updateClubSpeakLike (java.lang.Long, java.lang.Integer)
	 */
	@Transactional
	@Override
	public void updateClubSpeakLike(ClubSpeakLike like, Integer op) {
		// TODO Auto-generated method stub
		if (op == 1) {
			like.setGmtCreate(new Date());
			likeMapper.insertSelective(like);
		} else {
			ClubSpeakLikeKey key = new ClubSpeakLikeKey();
			key.setSpeakId(like.getSpeakId());
			key.setMemberId(like.getMemberId());
			likeMapper.deleteByPrimaryKey(key);
		}
		speakMapper.updateLike(like.getSpeakId(), op);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService#
	 * selectClubSpeakList (java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<ClubSpeackVo> selectClubSpeakList(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<ClubSpeackVo> speackList = speakMapper.selectClubSpeakList(map);
		return new Page<ClubSpeackVo>(speackList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService#
	 * selectClubSpeakInfo (java.lang.Long)
	 */
	@Override
	public ClubSpeackInfoVo selectClubSpeakInfo(Long id) {
		ClubSpeackInfoVo speackInfo = speakMapper.selectClubSpeakInfo(id);
		if (StringUtils.isNotBlank(speackInfo.getHeader())) {
			speackInfo.setHeader(UploadDocumentUtil.buildPublicPath(speackInfo
					.getHeader()));
		}
		List<Photo> photos = photoMapper.selectBySpeakList(id);
		if (photos.size() > 0) {
			for (Photo clubSpeakPhoto : photos) {
				clubSpeakPhoto.setPhotoId(UploadDocumentUtil
						.buildPublicPath(clubSpeakPhoto.getPhotoId()));
			}
			speackInfo.setPhotos(photos);
		}
		return speackInfo;
	}

	@Override
	public ClubSpeakVo selectDetailById(Long id) {
		// TODO Auto-generated method stub
		ClubSpeakVo clubSpeakVo = speakMapper.selectDetaiById(id);
		if (clubSpeakVo != null) {
			if (StringUtils.isNotBlank(clubSpeakVo.getHeader())) {
				clubSpeakVo.setHeader(UploadDocumentUtil
						.buildPublicPath(clubSpeakVo.getHeader()));
			}

			// 团言的照片
			Set<Long> speakId = new HashSet<>();
			speakId.add(clubSpeakVo.getId());
			List<Photo> photos = photoMapper.selectBySpeakIdList(speakId);
			for (Photo clubSpeakPhoto : photos) {
				clubSpeakPhoto.setPhotoId(UploadDocumentUtil
						.buildPublicPath(clubSpeakPhoto.getPhotoId()));
			}
			if (photos.size() > 0) {
				clubSpeakVo.setPhotos(photos);
			}
			// 点赞人
			List<LikeVo> likes = likeMapper.selectBySpeakIdList(speakId);
			clubSpeakVo.setLikes(likes);
		}

		return clubSpeakVo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.ClubSpeakService#selectByGzhPage
	 * (java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<ClubSpeakVo> selectByGzhPage(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<ClubSpeakVo> list = speakMapper.selectByPage(map);
		if (list.size() > 0) {
			Map<Long, ClubSpeakVo> voMap = new HashMap<>();
			for (ClubSpeakVo clubSpeakVo : list) {
				voMap.put(clubSpeakVo.getId(), clubSpeakVo);
				if (StringUtils.isNotBlank(clubSpeakVo.getHeader())) {
					clubSpeakVo.setHeader(UploadDocumentUtil
							.buildPublicPath(clubSpeakVo.getHeader()));
				}
			}
			// 团言的照片
			List<Photo> photos = photoMapper
					.selectBySpeakIdList(voMap.keySet());
			for (Photo clubSpeakPhoto : photos) {
				List<Photo> photoes = voMap.get(clubSpeakPhoto.getBzId())
						.getPhotos();
				clubSpeakPhoto.setPhotoId(UploadDocumentUtil
						.buildPublicPath(clubSpeakPhoto.getPhotoId()));
				if (photoes == null) {
					photoes = new ArrayList<>();
					photoes.add(clubSpeakPhoto);
					voMap.get(clubSpeakPhoto.getBzId()).setPhotos(photoes);
				} else {
					photoes.add(clubSpeakPhoto);
				}

			}
		}
		return new Page<>(list);
	}

}
