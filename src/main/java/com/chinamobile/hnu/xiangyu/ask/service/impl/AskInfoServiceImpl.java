package com.chinamobile.hnu.xiangyu.ask.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.ask.dao.AskCommentLikeMapper;
import com.chinamobile.hnu.xiangyu.ask.dao.AskCommentMapper;
import com.chinamobile.hnu.xiangyu.ask.dao.AskCommentReplyMapper;
import com.chinamobile.hnu.xiangyu.ask.dao.AskInfoMapper;
import com.chinamobile.hnu.xiangyu.ask.dao.AskLabelMapper;
import com.chinamobile.hnu.xiangyu.ask.dao.AskPhotoMapper;
import com.chinamobile.hnu.xiangyu.ask.dao.AskSpeakLikeMapper;
import com.chinamobile.hnu.xiangyu.ask.dao.AskVisitHistoryMapper;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskComment;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskCommentLike;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskCommentReply;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskInfo;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskLabel;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskPhoto;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskSpeakLike;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskVisitHistory;
import com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService;
import com.chinamobile.hnu.xiangyu.ask.vo.AskCommentReplyVo;
import com.chinamobile.hnu.xiangyu.ask.vo.AskCommentVo;
import com.chinamobile.hnu.xiangyu.ask.vo.AskInfoVo;
import com.chinamobile.hnu.xiangyu.club.vo.LikeVo;
import com.chinamobile.hnu.xiangyu.common.dao.SysUserNewsMapper;
import com.chinamobile.hnu.xiangyu.common.pojo.SysUserNews;
import com.chinamobile.hnu.xiangyu.user.dao.UserAccountMapper;
import com.chinamobile.hnu.xiangyu.user.dao.UserCertificationMapper;
import com.chinamobile.hnu.xiangyu.user.dao.UserFavoriteMapper;
import com.chinamobile.hnu.xiangyu.user.dao.UserHistoricalFootprintMapper;
import com.chinamobile.hnu.xiangyu.user.dao.UserReportMapper;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.pojo.UserFavorite;
import com.chinamobile.hnu.xiangyu.user.pojo.UserHistoricalFootprint;
import com.chinamobile.hnu.xiangyu.user.pojo.UserReport;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.Utils;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;
import com.chinamobile.hnu.xiangyu.web.api.vo.ClubSpeackVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.UserCertificationInfoVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.UserCertificationVo;
import com.github.pagehelper.PageHelper;
import com.um.pojo.Alarm;
import com.um.service.UmengService;

/**
 * 
 * @author lh <自动生成> @联系QQ：2465613689
 * @version 2018-06-05 16:00:38
 */
@Service("askInfoService")
public class AskInfoServiceImpl implements IAskInfoService {

	private final Logger logger = Logger.getLogger(AskInfoServiceImpl.class);
	@Autowired
	protected AskInfoMapper mapper;

	@Autowired
	protected UserHistoricalFootprintMapper footmapper;

	@Autowired
	protected AskPhotoMapper photoMapper;

	@Autowired
	protected AskLabelMapper labelMapper;

	@Autowired
	protected AskCommentMapper commentMapper;

	@Autowired
	protected UserCertificationMapper certificationMapper;

	@Autowired
	protected UserFavoriteMapper favoriteMapper;

	@Autowired
	protected UserReportMapper reportMapper;

	@Autowired
	protected AskSpeakLikeMapper likeMapper;

	@Autowired
	protected AskVisitHistoryMapper visitMapper;

	@Autowired
	protected AskCommentLikeMapper commlikeMapper;

	@Autowired
	protected AskCommentReplyMapper replyMapper;

	@Autowired
	protected SysUserNewsMapper userNewsMapper;

	@Autowired
	private UserAccountMapper accoutMapper;

	@Autowired
	private UmengService umengService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#
	 * deleteByPrimaryKey (java.lang.Long)
	 */
	@Override
	public int deleteByPrimaryKey(Long id) {
		return mapper.deleteByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#insert(com.
	 * chinamobile.hnu.xiangyu.ask.pojo.AskInfo)
	 */
	@Override
	public int insert(AskInfo record) {
		return mapper.insert(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#insertSelective
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskInfo)
	 */
	@Override
	public int insertSelective(AskInfo record) {
		return mapper.insertSelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#
	 * selectByPrimaryKey (java.lang.Long)
	 */
	@Override
	public AskInfo selectByPrimaryKey(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#
	 * updateByPrimaryKeySelective(com.chinamobile.hnu.xiangyu.ask.pojo.AskInfo)
	 */
	@Override
	public int updateByPrimaryKeySelective(AskInfo record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#
	 * updateByPrimaryKey (com.chinamobile.hnu.xiangyu.ask.pojo.AskInfo)
	 */
	@Override
	public int updateByPrimaryKey(AskInfo record) {
		return mapper.updateByPrimaryKey(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#insertAskInfo
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskInfo, java.util.List)
	 */
	@Override
	public Long insertAskInfo(AskInfo info, List<AskPhoto> photos,
			List<AskLabel> labels) {
		int i = 0;
		info.setGmtCreate(new Date());
		mapper.insertSelective(info);

		if (photos != null && photos.size() > 0) {
			for (AskPhoto photo : photos) {
				photo.setAskId(info.getId());
			}
			i = photoMapper.inserList(photos);
		}
		if (labels != null && labels.size() > 0) {
			for (AskLabel lable : labels) {
				lable.setAskId(info.getId());
			}
			i = labelMapper.inserList(labels);
		}
		return info.getId();
	}

	/**
	 * 分页查询问问列表
	 */
	@Override
	public Page<AskInfoVo> selectPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map, Integer userId) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<AskInfoVo> askInfoVo = mapper.selectPage(map);
		if (askInfoVo.size() > 0) {
			Map<Long, AskInfoVo> voMap = new HashMap<>();
			askInfoVo.forEach(vo -> {
				voMap.put(vo.getId(), vo);
				if (StringUtils.isNotBlank(vo.getHeader())) {
					vo.setHeader(UploadDocumentUtil.buildPublicPath(vo
							.getHeader()));
				}
				vo.setTime(Utils.formatAgo(vo.getGmtCreate()));
			});

			// 图片
			List<AskPhoto> photos = photoMapper.selectByAskIdList(voMap
					.keySet());
			if (photos.size() > 0) {
				for (AskPhoto ph : photos) {
					List<AskPhoto> askPhoto = voMap.get(ph.getAskId())
							.getPhotos();
					if (StringUtils.isNotBlank(ph.getPhotoId())) {
						ph.setPhotoId(UploadDocumentUtil.buildPublicPath(ph
								.getPhotoId()));
					}
					if (askPhoto == null) {
						askPhoto = new ArrayList<AskPhoto>();
						askPhoto.add(ph);
						voMap.get(ph.getAskId()).setPhotos(askPhoto);
					} else {
						askPhoto.add(ph);
					}
				}
			}

			// 标签
			List<AskLabel> label = labelMapper
					.selectByAskIdList(voMap.keySet());
			if (label.size() > 0) {
				for (AskLabel askLabel : label) {
					List<AskLabel> labels = voMap.get(askLabel.getAskId())
							.getLabels();
					if (labels == null) {
						labels = new ArrayList<AskLabel>();
						labels.add(askLabel);
						voMap.get(askLabel.getAskId()).setLabels(labels);
					} else {
						labels.add(askLabel);
					}
				}
			}

			// 我的点赞;
			List<AskSpeakLike> like = likeMapper.selectByUserId(userId);
			if (like.size() > 0) {
				for (AskInfoVo info : askInfoVo) {
					for (AskSpeakLike ali : like) {
						if (ali.getAskId().equals(info.getId())) {
							info.setUserLike(1);
						}
					}
				}
			}
		}

		// 我的收藏
		List<UserFavorite> uf = favoriteMapper.selectByUserId(userId);
		if (uf.size() > 0) {
			for (AskInfoVo info : askInfoVo) {
				for (UserFavorite ali : uf) {
					if (ali.getBizId().equals(info.getId())) {
						info.setIsCollect(1);
					}
				}
			}
		}

		return new Page<>(askInfoVo);
	}

	/**
	 * 分页查询指定专场的记录
	 */
	@Override
	public Page<AskInfoVo> pgspecial(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<AskInfoVo> askInfoVo = mapper.pgspecial(map);
		if (askInfoVo.size() > 0) {
			askInfoVo.forEach(vo -> {
				if (StringUtils.isNotBlank(vo.getHeader())) {
					vo.setHeader(UploadDocumentUtil.buildPublicPath(vo
							.getHeader()));
				}
				vo.setTime(Utils.formatAgo(vo.getGmtCreate()));
			});
		}
		return new Page<>(askInfoVo);
	}

	/**
	 * 分页查询问问的一级评论
	 */
	@Override
	public Page<AskCommentVo> pgfirst(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map, Integer userId, Long askid) {
		if (pageCurrent.equals(1)) {
			// 保存用户历史访问记录
			UserHistoricalFootprint foot = new UserHistoricalFootprint();
			foot.setUserId(userId);
			foot.setAcrossId(1);
			foot.setHistoricalFootprintId(askid);
			foot.setInterviewTime(new Date());
			footmapper.insertSelective(foot);
		}
		// 评论
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<AskCommentVo> commentVo = commentMapper.selectAskComment(map);
		if (commentVo.size() > 0) {
			commentVo.forEach(vo -> {
				if (StringUtils.isNotBlank(vo.getHeader())) {
					vo.setHeader(UploadDocumentUtil.buildPublicPath(vo
							.getHeader()));
				}
				vo.setTime(Utils.formatAgo(vo.getGmtCreate()));
			});
		}

		// 我的点赞;
		List<AskCommentLike> like = commlikeMapper.selectByUserId(userId);
		if (like.size() > 0) {
			for (AskCommentVo info : commentVo) {
				for (AskCommentLike ali : like) {
					if (ali.getCommentId().equals(info.getId())) {
						info.setIsTop(1);
					}
				}
			}
		}
		return new Page<>(commentVo);
	}

	/**
	 * 问问详情
	 */
	@Override
	public AskInfoVo askInfo(Map<String, Object> map, Integer userId) {
		AskInfoVo infoVo = mapper.pgfirst(map);
		if (infoVo != null) {
			if (StringUtils.isNotBlank(infoVo.getHeader())) {
				infoVo.setHeader(UploadDocumentUtil.buildPublicPath(infoVo
						.getHeader()));
			}
			infoVo.setTime(Utils.formatAgo(infoVo.getGmtCreate()));

			// 标签
			List<AskLabel> label = labelMapper.selectLabel(map);
			if (label.size() > 0) {
				infoVo.setLabels(label);
			}
			// 图片
			List<AskPhoto> photo = photoMapper.selectPhotos(map);
			if (photo.size() > 0) {
				for (AskPhoto ph : photo) {
					if (StringUtils.isNotBlank(ph.getPhotoId())) {
						ph.setPhotoId(UploadDocumentUtil.buildPublicPath(ph
								.getPhotoId()));
					}
				}
				infoVo.setPhotos(photo);
			}

			// 我的点赞;
			List<AskSpeakLike> like = likeMapper.selectByUserId(userId);
			if (like.size() > 0) {
				for (AskSpeakLike ali : like) {
					if (ali.getAskId().equals(infoVo.getId())) {
						infoVo.setUserLike(1);
					}
				}
			}
		}
		return infoVo;
	}

	/**
	 * 一级评论详情
	 */
	@Override
	public AskCommentVo selectCommentById(Map<String, Object> map) {
		AskCommentVo commentVo = commentMapper.selectCommentById(map);
		if (commentVo != null) {
			if (StringUtils.isNotBlank(commentVo.getHeader())) {
				commentVo.setHeader(UploadDocumentUtil
						.buildPublicPath(commentVo.getHeader()));
			}
			commentVo.setTime(Utils.formatAgo(commentVo.getGmtCreate()));
		}
		return commentVo;
	}

	/**
	 * 分页查询二级评论
	 */
	@Override
	public Page<AskCommentReplyVo> selectReplyByFirstId(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<AskCommentReplyVo> replyVo = replyMapper.selectReplyByFirstId(map);
		if (replyVo.size() > 0) {
			replyVo.forEach(vo -> {
				if (StringUtils.isNotBlank(vo.getHeader())) {
					vo.setHeader(UploadDocumentUtil.buildPublicPath(vo
							.getHeader()));
				}
				vo.setTime(Utils.formatAgo(vo.getGmtCreate()));
			});
		}
		return new Page<>(replyVo);
	}

	/**
	 * 评论
	 */
	@Override
	public Long saveComment(String content, Long id, Integer type,
			Integer presentor) {
		// 保存1级评论
		Long ids = null;
		if (type.equals(1)) {
			AskComment record = new AskComment();
			record.setAskId(id);
			record.setGmtCreate(new Date());
			record.setContent(content);
			record.setPresentor(presentor);
			int i = commentMapper.insertSelective(record);
			ids = record.getId();
			if (i > 0) {
				// 更新问题评论数量
				int commentAmount = commentMapper.selectCountByAskId(id);
				AskInfo info = new AskInfo();
				info.setCommentAmount(commentAmount);
				info.setGmtModified(new Date());
				info.setId(id);
				mapper.updateByPrimaryKeySelective(info);

				// 保存评论消息记录
				AskInfo ai = mapper.selectByPrimaryKey(id);
				if (ai.getPresentor() != null) {
					if (!ai.getPresentor().equals(presentor)) {
						SysUserNews userNews = new SysUserNews();
						userNews.setBizId(id);
						userNews.setReplyId(presentor);
						userNews.setReceiveId(ai.getPresentor());
						userNews.setContent(content);
						userNews.setGmtCreate(new Date());
						userNews.setCategory(3);
						userNewsMapper.insertSelective(userNews);
						// 友盟推送消息
						Alarm alarm = new Alarm();
						alarm.setTitle("有人评论了你的问问");
						alarm.setType(3);
						alarm.setMsg("有人评论了你的问问:" + content);
						alarm.setReceiveId(ai.getPresentor());
						try {
							umengService.sendAndroidBroadcast(alarm);
							umengService.sendIOSCustomizedcast(alarm);
						} catch (Exception e) {
							logger.error("send umeng msg  is fail", e);
						}
					}
				}
			}
		}
		// 保存2级评论
		if (type.equals(2)) {
			AskCommentReply record = new AskCommentReply();
			record.setPresentor(presentor);
			record.setGmtCreate(new Date());
			record.setCommentId(id);
			record.setContent(content);
			int i = replyMapper.insertSelective(record);
			ids = record.getId();
			if (i > 0) {
				// 更新1级评论数量
				int commentAmount = replyMapper.selectCountByCId(id);
				AskComment comment = new AskComment();
				comment.setCommentAmount(commentAmount);
				comment.setId(id);
				comment.setGmtModified(new Date());
				commentMapper.updateByPrimaryKeySelective(comment);
			}
		}
		return ids;
	}

	/**
	 * 收藏 or 取消
	 */
	@Override
	public ResponseDto favorite(Long askid, Integer op, Integer userId) {
		UserFavorite favorite = favoriteMapper.selectByAskId(askid, userId);
		// 收藏问问
		if (op.equals(1)) {
			if (favorite != null) {
				return ResultUtil.result(3, "当前问问你已经收藏了，请勿重复收藏！");
			}
			UserFavorite record = new UserFavorite();
			record.setBizId(askid);
			record.setCategory((byte) 1);
			record.setGmtCreate(new Date());
			record.setUserId(userId);
			int i = favoriteMapper.insertSelective(record);
			if (i > 0) {
				return ResultUtil.result(0, "操作成功", record.getId());
			}
		}
		// 取消收藏问问
		if (op.equals(2)) {
			if (favorite == null) {
				return ResultUtil.result(3, "当前问问你还没收藏呢！");
			}
			UserFavorite uf = new UserFavorite();
			uf.setBizId(askid);
			uf.setUserId(userId);
			favoriteMapper.delete(uf);
			return ResultUtil.result(0, "操作成功");
		}
		return null;
	}

	/**
	 * 举报问题inform
	 */
	@Override
	public ResponseDto inform(Long askid, Integer userId, UserReport userReport) {
		if (userReport != null) {
			if (userReport.getCategory().equals(1)) {
				UserReport report = reportMapper.selectByAskId(askid, userId);
				if (report != null) {
					return ResultUtil.result(3, "当前问问你已经举报了，请勿重复举报！");
				}
			}
		}
		int i = reportMapper.insertSelective(userReport);
		if (i > 0) {
			return ResultUtil.result(0, "举报成功");
		}
		return null;
	}

	/**
	 * 点赞
	 */
	@Override
	public ResponseDto likes(Integer userId, Long askid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("askId", askid);
		AskSpeakLike like = likeMapper.selectByAskId(map);
		// 如果用户已点赞，则直接取消点赞
		if (like != null) {
			AskSpeakLike key = new AskSpeakLike();
			key.setAskId(askid);
			key.setMemberId(userId);
			int i = likeMapper.deleteByPrimaryKey(key);
			if (i > 0) {
				// 更新点赞数量
				int likeAmount = likeMapper.selectCountByAskId(askid);
				AskInfo info = new AskInfo();
				info.setId(askid);
				info.setLikeAmount(likeAmount);
				mapper.updateByPrimaryKeySelective(info);
				return ResultUtil.result(0, "取消点赞成功！");
			}
		}
		AskSpeakLike record = new AskSpeakLike();
		record.setAskId(askid);
		record.setGmtCreate(new Date());
		record.setMemberId(userId);
		int i = likeMapper.insertSelective(record);
		if (i > 0) {
			int likeAmount = likeMapper.selectCountByAskId(askid);
			AskInfo info = new AskInfo();
			info.setId(askid);
			info.setLikeAmount(likeAmount);
			mapper.updateByPrimaryKeySelective(info);
			return ResultUtil.result(0, "点赞成功！", record.getId());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#updateVisitAmount
	 * (java.lang.Long)
	 */
	@Override
	public int updateVisitAmount(Long askid) {
		// TODO Auto-generated method stub
		return mapper.updateVisitAmount(askid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#top(java.lang
	 * .Integer, java.lang.Long)
	 */
	@Override
	public ResponseDto top(Integer userId, Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("commentId", id);
		AskCommentLike like = commlikeMapper.selectBycommentId(map);
		// 如果用户已点赞，则直接取消点赞
		if (like != null) {
			AskCommentLike key = new AskCommentLike();
			key.setCommentId(id);
			key.setMemberId(userId);
			int i = commlikeMapper.deleteByPrimaryKey(key);
			if (i > 0) {
				// 更新点赞数量
				int likeAmount = commlikeMapper.selectCountBycommentId(id);
				AskComment info = new AskComment();
				info.setId(id);
				info.setLikeAmount(likeAmount);
				commentMapper.updateByPrimaryKeySelective(info);
				return ResultUtil.result(0, "取消顶成功！");
			}
		}
		AskCommentLike record = new AskCommentLike();
		record.setCommentId(id);
		record.setGmtCreate(new Date());
		record.setMemberId(userId);
		int i = commlikeMapper.insertSelective(record);
		if (i > 0) {
			int likeAmount = commlikeMapper.selectCountBycommentId(id);
			AskComment info = new AskComment();
			info.setId(id);
			info.setLikeAmount(likeAmount);
			commentMapper.updateByPrimaryKeySelective(info);
			return ResultUtil.result(0, "操作成功！");
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#
	 * updateAskYesterdayTraffic()
	 */
	@Override
	public void updateAskYesterdayTraffic() {
		mapper.updateYesterdayAskToZero();
		Date today = new Date();
		Date yesterday = new Date(today.getTime() - 24 * 60 * 60 * 1000);
		List<AskVisitHistory> list = visitMapper.selectAllYesterdayVisit(
				Utils.formatSimple(today), Utils.formatSimple(yesterday));
		if (list.size() > 0) {
			mapper.updateYesterdayAskByList(list);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#selectMyListPage
	 * (java.util.Map)
	 */
	@Override
	public Page<AskInfoVo> selectMyListPage(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map, Integer userId) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<AskInfoVo> askInfoVo = mapper.selectMyListPage(map);
		if (askInfoVo.size() > 0) {
			Map<Long, AskInfoVo> voMap = new HashMap<>();
			askInfoVo.forEach(vo -> {
				voMap.put(vo.getId(), vo);
				if (StringUtils.isNotBlank(vo.getHeader())) {
					vo.setHeader(UploadDocumentUtil.buildPublicPath(vo
							.getHeader()));
				}
				vo.setTime(Utils.formatAgo(vo.getGmtCreate()));
			});

			// 图片
			List<AskPhoto> photos = photoMapper.selectByAskIdList(voMap
					.keySet());
			if (photos.size() > 0) {
				for (AskPhoto ph : photos) {
					List<AskPhoto> askPhoto = voMap.get(ph.getAskId())
							.getPhotos();
					if (StringUtils.isNotBlank(ph.getPhotoId())) {
						ph.setPhotoId(UploadDocumentUtil.buildPublicPath(ph
								.getPhotoId()));
					}
					if (askPhoto == null) {
						askPhoto = new ArrayList<AskPhoto>();
						askPhoto.add(ph);
						voMap.get(ph.getAskId()).setPhotos(askPhoto);
					} else {
						askPhoto.add(ph);
					}
				}
			}

			// 标签
			List<AskLabel> label = labelMapper
					.selectByAskIdList(voMap.keySet());
			if (label.size() > 0) {
				for (AskLabel askLabel : label) {
					List<AskLabel> labels = voMap.get(askLabel.getAskId())
							.getLabels();
					if (labels == null) {
						labels = new ArrayList<AskLabel>();
						labels.add(askLabel);
						voMap.get(askLabel.getAskId()).setLabels(labels);
					} else {
						labels.add(askLabel);
					}
				}
			}
			// 我的点赞;
			List<AskSpeakLike> like = likeMapper.selectByUserId(userId);
			if (like.size() > 0) {
				for (AskInfoVo info : askInfoVo) {
					for (AskSpeakLike ali : like) {
						if (ali.getAskId().equals(info.getId())) {
							info.setUserLike(1);
						}
					}
				}
			}
		}

		// 我的收藏
		List<UserFavorite> uf = favoriteMapper.selectByUserId(userId);
		if (uf.size() > 0) {
			for (AskInfoVo info : askInfoVo) {
				for (UserFavorite ali : uf) {
					if (ali.getBizId().equals(info.getId())) {
						info.setIsCollect(1);
					}
				}
			}
		}
		return new Page<>(askInfoVo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#commentfirst(
	 * java.lang.Integer, java.lang.Integer, java.lang.Long)
	 */
	@Override
	public Page<AskCommentVo> commentfirst(Integer pageCurrent,
			Integer pageSize, Long askid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("askid", askid);
		// 评论
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<AskCommentVo> commentVo = commentMapper.selectAskComment(map);
		if (commentVo.size() > 0) {
			commentVo.forEach(vo -> {
				if (StringUtils.isNotBlank(vo.getHeader())) {
					vo.setHeader(UploadDocumentUtil.buildPublicPath(vo
							.getHeader()));
				}
				vo.setTime(Utils.formatAgo(vo.getGmtCreate()));
			});
		}

		return new Page<AskCommentVo>(commentVo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#selectAskInfoList
	 * (java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<ClubSpeackVo> selectAskInfoList(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<ClubSpeackVo> vo = mapper.selectAskInfoList(map);
		return new Page<ClubSpeackVo>(vo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#
	 * selectUserCertificationList()
	 */
	@Override
	public Page<UserCertificationVo> selectUserCertificationList(
			Integer pageCurrent, Integer pageSize, Map<String, Object> map) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<UserCertificationVo> vo = mapper.selectUserCertificationList(map);
		return new Page<UserCertificationVo>(vo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#
	 * selectUserCertificationInfo(java.lang.Integer)
	 */
	@Override
	public UserCertificationInfoVo selectUserCertificationInfo(Integer userId) {
		UserCertificationInfoVo uc = certificationMapper
				.selectUserInfoByUserId(userId);
		if (uc != null) {
			if (StringUtils.isNotBlank(uc.getIdcardNegative())) {
				uc.setIdcardNegative(UploadDocumentUtil.buildPublicPath(uc
						.getIdcardNegative()));
			}
			if (StringUtils.isNotBlank(uc.getIdcardPositive())) {
				uc.setIdcardPositive(UploadDocumentUtil.buildPublicPath(uc
						.getIdcardPositive()));
			}
			if (StringUtils.isNotBlank(uc.getHeader())) {
				uc.setHeader(UploadDocumentUtil.buildPublicPath(uc.getHeader()));
			}
		}

		return uc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#selectLablePage
	 * (java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<AskInfoVo> selectLabelPage(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<AskInfoVo> askInfoVo = mapper.selectByLabelPage(map);
		int userId = (int) map.get("uid");
		if (askInfoVo.size() > 0) {
			Map<Long, AskInfoVo> voMap = new HashMap<>();
			askInfoVo.forEach(vo -> {
				voMap.put(vo.getId(), vo);
				if (StringUtils.isNotBlank(vo.getHeader())) {
					vo.setHeader(UploadDocumentUtil.buildPublicPath(vo
							.getHeader()));
				}
				vo.setTime(Utils.formatAgo(vo.getGmtCreate()));
			});

			// 图片
			List<AskPhoto> photos = photoMapper.selectByAskIdList(voMap
					.keySet());
			if (photos.size() > 0) {
				for (AskPhoto ph : photos) {
					List<AskPhoto> askPhoto = voMap.get(ph.getAskId())
							.getPhotos();
					if (StringUtils.isNotBlank(ph.getPhotoId())) {
						ph.setPhotoId(UploadDocumentUtil.buildPublicPath(ph
								.getPhotoId()));
					}
					if (askPhoto == null) {
						askPhoto = new ArrayList<AskPhoto>();
						askPhoto.add(ph);
						voMap.get(ph.getAskId()).setPhotos(askPhoto);
					} else {
						askPhoto.add(ph);
					}
				}
			}

			// 标签
			List<AskLabel> label = labelMapper
					.selectByAskIdList(voMap.keySet());
			if (label.size() > 0) {
				for (AskLabel askLabel : label) {
					List<AskLabel> labels = voMap.get(askLabel.getAskId())
							.getLabels();
					if (labels == null) {
						labels = new ArrayList<AskLabel>();
						labels.add(askLabel);
						voMap.get(askLabel.getAskId()).setLabels(labels);
					} else {
						labels.add(askLabel);
					}
				}
			}

			// 我的点赞;
			List<AskSpeakLike> like = likeMapper.selectByUserId(userId);
			if (like.size() > 0) {
				for (AskInfoVo info : askInfoVo) {
					for (AskSpeakLike ali : like) {
						if (ali.getAskId().equals(info.getId())) {
							info.setUserLike(1);
						}
					}
				}
			}
		}

		// 我的收藏
		List<UserFavorite> uf = favoriteMapper.selectByUserId(userId);
		if (uf.size() > 0) {
			for (AskInfoVo info : askInfoVo) {
				for (UserFavorite ali : uf) {
					if (ali.getBizId().equals(info.getId())) {
						info.setIsCollect(1);
					}
				}
			}
		}
		return new Page<>(askInfoVo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#selectGzhPage(
	 * java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<AskInfoVo> selectGzhPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<AskInfoVo> askInfoVo = mapper.selectPage(map);
		if (askInfoVo.size() > 0) {
			Map<Long, AskInfoVo> voMap = new HashMap<>();
			askInfoVo.forEach(vo -> {
				voMap.put(vo.getId(), vo);
				if (StringUtils.isNotBlank(vo.getHeader())) {
					vo.setHeader(UploadDocumentUtil.buildPublicPath(vo
							.getHeader()));
				}
				vo.setTime(Utils.formatAgo(vo.getGmtCreate()));
			});

			// 图片
			List<AskPhoto> photos = photoMapper.selectByAskIdList(voMap
					.keySet());
			if (photos.size() > 0) {
				for (AskPhoto ph : photos) {
					List<AskPhoto> askPhoto = voMap.get(ph.getAskId())
							.getPhotos();
					if (StringUtils.isNotBlank(ph.getPhotoId())) {
						ph.setPhotoId(UploadDocumentUtil.buildPublicPath(ph
								.getPhotoId()));
					}
					if (askPhoto == null) {
						askPhoto = new ArrayList<AskPhoto>();
						askPhoto.add(ph);
						voMap.get(ph.getAskId()).setPhotos(askPhoto);
					} else {
						askPhoto.add(ph);
					}
				}
			}

			// 标签
			List<AskLabel> label = labelMapper
					.selectByAskIdList(voMap.keySet());
			if (label.size() > 0) {
				for (AskLabel askLabel : label) {
					List<AskLabel> labels = voMap.get(askLabel.getAskId())
							.getLabels();
					if (labels == null) {
						labels = new ArrayList<AskLabel>();
						labels.add(askLabel);
						voMap.get(askLabel.getAskId()).setLabels(labels);
					} else {
						labels.add(askLabel);
					}
				}
			}

		}

		return new Page<>(askInfoVo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#GzhAskInfo(java.
	 * util.Map)
	 */
	@Override
	public AskInfoVo GzhAskInfo(Long id) throws Exception {
		// TODO Auto-generated method stub
		AskInfo askInfo = mapper.selectByPrimaryKey(id);
		AskInfoVo vo = new AskInfoVo();
		if (askInfo != null) {
			BeanUtils.copyProperties(vo, askInfo);
			// 创建者信息
			UserAccount userInfo = accoutMapper.selectByPrimaryKey(askInfo
					.getPresentor());
			vo.setNn(userInfo.getNickname());
			if (StringUtils.isNotBlank(userInfo.getHeader())) {
				if (StringUtils.isNotBlank(userInfo.getHeader())) {
					vo.setHeader(UploadDocumentUtil.buildPublicPath(userInfo
							.getHeader()));
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("askid", id);
			// 标签
			List<AskLabel> label = labelMapper.selectLabel(map);
			if (label.size() > 0) {
				vo.setLabels(label);
			}
			// 图片
			List<AskPhoto> photo = photoMapper.selectPhotos(map);
			if (photo.size() > 0) {
				for (AskPhoto ph : photo) {
					if (StringUtils.isNotBlank(ph.getPhotoId())) {
						ph.setPhotoId(UploadDocumentUtil.buildPublicPath(ph
								.getPhotoId()));
					}
				}
				vo.setPhotos(photo);
			}
			// 点赞用户
			List<LikeVo> likes = likeMapper.selectVoByAskId(id);
			vo.setLikes(likes);
		}
		return vo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#GzhPgfirst(java.
	 * lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<AskCommentVo> GzhPgfirst(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		// 评论
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<AskCommentVo> commentVo = commentMapper.selectAskComment(map);
		if (commentVo.size() > 0) {
			commentVo.forEach(vo -> {
				if (StringUtils.isNotBlank(vo.getHeader())) {
					vo.setHeader(UploadDocumentUtil.buildPublicPath(vo
							.getHeader()));
				}
				vo.setTime(Utils.formatAgo(vo.getGmtCreate()));
			});
		}
		return new Page<>(commentVo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskInfoService#selectByExample
	 * (java.util.Map)
	 */
	@Override
	public List<AskInfo> selectByExample(Map<String, Object> map) {
		return mapper.selectByExample(map);
	}

}
