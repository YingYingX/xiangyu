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

import com.chinamobile.hnu.xiangyu.club.dao.ClubTeamBuildingCommentMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubTeamBuildingLikeMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubTeamBuildingMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubTeamBuildingPhotoMapper;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubTeamBuilding;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubTeamBuildingComment;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubTeamBuildingLike;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubTeamBuildingLikeKey;
import com.chinamobile.hnu.xiangyu.club.pojo.Photo;
import com.chinamobile.hnu.xiangyu.club.service.TeamBuildService;
import com.chinamobile.hnu.xiangyu.club.vo.CommentVo;
import com.chinamobile.hnu.xiangyu.club.vo.LikeVo;
import com.chinamobile.hnu.xiangyu.club.vo.TeamBuildVo;
import com.chinamobile.hnu.xiangyu.common.dao.SysUserNewsMapper;
import com.chinamobile.hnu.xiangyu.common.pojo.SysUserNews;
import com.chinamobile.hnu.xiangyu.user.dao.UserAccountMapper;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.Utils;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.web.api.vo.TeamBuildingVo;
import com.github.pagehelper.PageHelper;

/**
 * @author The Old Man and the Sea
 *
 *         2018年5月31日
 */
@Service
public class TeamBuildServiceImpl implements TeamBuildService {

	@Autowired
	private ClubTeamBuildingMapper buildMapper;

	@Autowired
	private ClubTeamBuildingPhotoMapper photoMapper;

	@Autowired
	private ClubTeamBuildingCommentMapper commentMapper;

	@Autowired
	private ClubTeamBuildingLikeMapper likeMapper;

	@Autowired
	protected SysUserNewsMapper userNewsMapper;

	@Autowired
	private UserAccountMapper accoutMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.TeamBuildService#insertClubSpeak
	 * (com.chinamobile.hnu.xiangyu.club.pojo.ClubTeamBuilding, java.util.List)
	 */
	@Transactional
	@Override
	public void insertClubTeamBuilding(ClubTeamBuilding clubTeamBuild) {
		// TODO Auto-generated method stub

		clubTeamBuild.setGmtCreate(new Date());
		buildMapper.insertSelective(clubTeamBuild);
		List<Photo> photos = clubTeamBuild.getPhotos();
		if (photos != null && photos.size() > 0) {
			for (Photo photo : photos) {
				photo.setBzId(clubTeamBuild.getId());
			}
			photoMapper.inserList(photos);
			photos.forEach(x -> {
				x.setPhotoId(UploadDocumentUtil.buildPublicPath(x.getPhotoId()));
			});
			clubTeamBuild.setPhotos(photos);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.TeamBuildService#updateClubSpeak
	 * (com.chinamobile.hnu.xiangyu.club.pojo.ClubTeamBuilding, java.util.List,
	 * java.lang.String)
	 */
	@Transactional
	@Override
	public void updateClubTeamBuilding(ClubTeamBuilding clubTeamBuild, String delFileIds) {
		// TODO Auto-generated method stub
		clubTeamBuild.setGmtModified(new Date());
		buildMapper.updateByPrimaryKeySelective(clubTeamBuild);
		List<Photo> photos = clubTeamBuild.getPhotos();
		if (photos != null && photos.size() > 0) {
			for (Photo photo : photos) {
				photo.setBzId(clubTeamBuild.getId());
			}
			photoMapper.inserList(photos);
			photos.forEach(x -> {
				x.setPhotoId(UploadDocumentUtil.buildPublicPath(x.getPhotoId()));
			});
			clubTeamBuild.setPhotos(photos);
		}

		if (StringUtils.isNotBlank(delFileIds)) {
			photoMapper.deleteByIdList(Arrays.asList(delFileIds.split(",")));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.TeamBuildService#
	 * insertTeamBuildComment
	 * (com.chinamobile.hnu.xiangyu.club.pojo.ClubTeamBuildingComment)
	 */
	@Transactional
	@Override
	public void insertTeamBuildComment(ClubTeamBuildingComment buildComment) {
		// TODO Auto-generated method stub
		buildComment.setGmtCreate(new Date());
		commentMapper.insertSelective(buildComment);
		buildMapper.updateComment(buildComment.getTbId(), 1);

		// 保存评论消息记录
		ClubTeamBuilding ai = buildMapper.selectByPrimaryKey(buildComment.getTbId());
		if (ai.getPresentor() != null) {
			if (!ai.getPresentor().equals(buildComment.getPresentor())) {
				SysUserNews userNews = new SysUserNews();
				userNews.setBizId(buildComment.getTbId());
				userNews.setReplyId(buildComment.getPresentor());
				userNews.setReceiveId(ai.getPresentor());
				userNews.setContent(buildComment.getContent());
				userNews.setGmtCreate(new Date());
				// 1-团队消息；2-团言消息；3-问题消息；
				userNews.setCategory(1);
				userNewsMapper.insertSelective(userNews);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.TeamBuildService#
	 * selectClubTeamBuildingById(java.lang.Long)
	 */
	@Override
	public ClubTeamBuilding selectClubTeamBuildingById(Long tbId) {
		// TODO Auto-generated method stub
		return buildMapper.selectByPrimaryKey(tbId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.TeamBuildService#
	 * deleteClubTeamBuildingById(java.lang.Long)
	 */
	@Override
	public void deleteClubTeamBuildingById(Long tbId) {
		// TODO Auto-generated method stub
		buildMapper.deleteByPrimaryKey(tbId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.TeamBuildService#
	 * selectClubTeamBuildingCommentById(java.lang.Long)
	 */
	@Override
	public ClubTeamBuildingComment selectClubTeamBuildingCommentById(Long id) {
		// TODO Auto-generated method stub
		return commentMapper.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.TeamBuildService#
	 * deleteClubTeamBuildingCommentById(java.lang.Long)
	 */
	@Transactional
	@Override
	public void deleteClubTeamBuildingCommentById(Long id, Long tid) {
		// TODO Auto-generated method stub
		commentMapper.deleteByPrimaryKey(id);
		buildMapper.updateComment(tid, 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.TeamBuildService#
	 * updateClubTeamBuildingRecommend(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public void updateClubTeamBuildingRecommend(Long tbId, Integer op) {
		// TODO Auto-generated method stub
		ClubTeamBuilding record = new ClubTeamBuilding();
		record.setId(tbId);
		record.setRecommend(Byte.valueOf(op.toString()));
		buildMapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.TeamBuildService#
	 * selectClubTeamBuildingLikeById(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public ClubTeamBuildingLike selectClubTeamBuildingLikeById(Long tbId, Integer uid) {
		// TODO Auto-generated method stub
		ClubTeamBuildingLikeKey key = new ClubTeamBuildingLikeKey();
		key.setMemberId(uid);
		key.setTbId(tbId);
		return likeMapper.selectByPrimaryKey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.TeamBuildService#
	 * updateClubTeamBuildingLike
	 * (com.chinamobile.hnu.xiangyu.club.pojo.ClubTeamBuildingLike,
	 * java.lang.Integer)
	 */
	@Override
	public void updateClubTeamBuildingLike(ClubTeamBuildingLike like, Integer op) {
		// TODO Auto-generated method stub
		if (op == 1) {
			like.setGmtCreate(new Date());
			likeMapper.insertSelective(like);
		} else {
			ClubTeamBuildingLikeKey key = new ClubTeamBuildingLikeKey();
			key.setTbId(like.getTbId());
			key.setMemberId(like.getMemberId());
			likeMapper.deleteByPrimaryKey(key);
		}
		buildMapper.updateLike(like.getTbId(), op);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.TeamBuildService#selectByPage
	 * (java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<TeamBuildVo> selectByPage(Integer pageCurrent, Integer pageSize, Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<TeamBuildVo> list = buildMapper.selectByPage(map);

		if (list.size() > 0) {
			Map<Long, TeamBuildVo> voMap = new HashMap<>();
			for (TeamBuildVo teamBuildVo : list) {
				if (StringUtils.isNotBlank(teamBuildVo.getHeader())) {
					teamBuildVo.setHeader(UploadDocumentUtil.buildPublicPath(teamBuildVo.getHeader()));
				}
				voMap.put(teamBuildVo.getId(), teamBuildVo);
			}
			// 团建的照片
			List<Photo> photos = photoMapper.selectByTbIdList(voMap.keySet());
			for (Photo photo : photos) {
				List<Photo> photoes = voMap.get(photo.getBzId()).getPhotos();
				photo.setPhotoId(UploadDocumentUtil.buildPublicPath(photo.getPhotoId()));
				if (photoes == null) {
					photoes = new ArrayList<>();
					photoes.add(photo);
					voMap.get(photo.getBzId()).setPhotos(photoes);
				} else {
					photoes.add(photo);
				}

			}

			Integer uid = (Integer) map.get("uid");
			// 点赞人
			List<LikeVo> likes = likeMapper.selectByTbIdList(voMap.keySet());
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
	 * @see com.chinamobile.hnu.xiangyu.club.service.TeamBuildService#
	 * selectCommentByPage (java.lang.Integer, java.lang.Integer, java.util.Map)
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
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.TeamBuildService#selectTbPage
	 * (java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<TeamBuildingVo> selectTbPage(Integer pageCurrent, Integer pageSize, Map<String, Object> map) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<TeamBuildingVo> list = buildMapper.selectTbPage(map);
		return new Page<TeamBuildingVo>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.TeamBuildService#selectById(
	 * java.lang.Integer)
	 */
	@Override
	public TeamBuildingVo selectById(Integer id) {
		TeamBuildingVo list = buildMapper.selectById(id);
		if (StringUtils.isNoneBlank(list.getHeader())) {
			list.setHeader(UploadDocumentUtil.buildPublicPath(list.getHeader()));
		}
		list.setTime(Utils.formatAgo(list.getGmtCreate()));
		// 团建的照片
		List<Photo> photos = photoMapper.selectByTbList(id);
		if (photos.size() > 0) {
			for (Photo photo : photos) {
				photo.setPhotoId(UploadDocumentUtil.buildPublicPath(photo.getPhotoId()));
			}
			list.setPhotos(photos);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.club.service.TeamBuildService#selectByGzhPage
	 * (java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<TeamBuildVo> selectByGzhPage(Integer pageCurrent, Integer pageSize, Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<TeamBuildVo> list = buildMapper.selectByPage(map);
		if (list.size() > 0) {
			Map<Long, TeamBuildVo> voMap = new HashMap<>();
			for (TeamBuildVo teamBuildVo : list) {
				if (StringUtils.isNotBlank(teamBuildVo.getHeader())) {
					teamBuildVo.setHeader(UploadDocumentUtil.buildPublicPath(teamBuildVo.getHeader()));
				}
				voMap.put(teamBuildVo.getId(), teamBuildVo);
			}
			// 团建的照片
			List<Photo> photos = photoMapper.selectByTbIdList(voMap.keySet());
			for (Photo photo : photos) {
				List<Photo> photoes = voMap.get(photo.getBzId()).getPhotos();
				photo.setPhotoId(UploadDocumentUtil.buildPublicPath(photo.getPhotoId()));
				if (photoes == null) {
					photoes = new ArrayList<>();
					photoes.add(photo);
					voMap.get(photo.getBzId()).setPhotos(photoes);
				} else {
					photoes.add(photo);
				}
			}

		}
		return new Page<>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.club.service.TeamBuildService#
	 * selectGzhDetailById(java.lang.Long)
	 */
	@Override
	public TeamBuildVo selectGzhDetailById(Long id) throws Exception {
		// TODO Auto-generated method stub
		ClubTeamBuilding tb = buildMapper.selectByPrimaryKey(id);
		TeamBuildVo vo = new TeamBuildVo();
		if (tb != null) {
			BeanUtils.copyProperties(vo, tb);
			// 创建者信息
			UserAccount userInfo = accoutMapper.selectByPrimaryKey(tb.getPresentor());
			vo.setPresentorName(userInfo.getNickname());
			if (StringUtils.isNotBlank(userInfo.getHeader())) {
				if (StringUtils.isNotBlank(userInfo.getHeader())) {
					vo.setHeader(UploadDocumentUtil.buildPublicPath(userInfo.getHeader()));
				}
			}
			Set<Long> set = new HashSet<>();
			set.add(vo.getId());
			// 团建的照片
			List<Photo> photos = photoMapper.selectByTbIdList(set);
			for (Photo photo : photos) {
				photo.setPhotoId(UploadDocumentUtil.buildPublicPath(photo.getPhotoId()));
			}
			vo.setPhotos(photos);
			
			// 点赞人
			List<LikeVo> likes = likeMapper.selectByTbIdList(set);
			vo.setLikes(likes);
			
		}
		return vo;
	}

}
