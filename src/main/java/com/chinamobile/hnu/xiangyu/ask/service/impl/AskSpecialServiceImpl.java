package com.chinamobile.hnu.xiangyu.ask.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.ask.dao.AskCommentMapper;
import com.chinamobile.hnu.xiangyu.ask.dao.AskInfoMapper;
import com.chinamobile.hnu.xiangyu.ask.dao.AskSpeakLikeMapper;
import com.chinamobile.hnu.xiangyu.ask.dao.AskSpecialMapper;
import com.chinamobile.hnu.xiangyu.ask.dao.AskspecialLikeMapper;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskComment;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskInfo;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskSpeakLike;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskSpecial;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskspecialLike;
import com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService;
import com.chinamobile.hnu.xiangyu.ask.vo.AskCommentVo;
import com.chinamobile.hnu.xiangyu.ask.vo.AskInfoVo;
import com.chinamobile.hnu.xiangyu.user.dao.UserReportMapper;
import com.chinamobile.hnu.xiangyu.util.ResultUtil;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.Utils;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;
import com.chinamobile.hnu.xiangyu.web.api.vo.AaskInfoVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.AskSpecialVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.ClubSpeackVo;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-05 16:00:38
 */
@Service("askSpecialService")
public class AskSpecialServiceImpl implements IAskSpecialService {

	private final Logger logger = Logger.getLogger(AskSpecialServiceImpl.class);
	@Autowired
	protected AskSpecialMapper mapper;

	@Autowired
	protected AskspecialLikeMapper likeMapper;

	@Autowired
	protected AskSpeakLikeMapper speakLikeMapper;

	@Autowired
	protected AskInfoMapper infoMapper;

	@Autowired
	protected AskCommentMapper commentMapper;

	@Autowired
	protected UserReportMapper reportMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService#deleteByPrimaryKey
	 * (java.lang.Integer)
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return mapper.deleteByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService#insert(com
	 * .chinamobile.hnu.xiangyu.ask.pojo.AskSpecial)
	 */
	@Override
	public int insert(AskSpecial record) {
		return mapper.insert(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService#insertSelective
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskSpecial)
	 */
	@Override
	public int insertSelective(AskSpecial record) {
		return mapper.insertSelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService#selectByPrimaryKey
	 * (java.lang.Integer)
	 */
	@Override
	public AskSpecial selectByPrimaryKey(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService#
	 * updateByPrimaryKeySelective
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskSpecial)
	 */
	@Override
	public int updateByPrimaryKeySelective(AskSpecial record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService#
	 * updateByPrimaryKeyWithBLOBs
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskSpecial)
	 */
	@Override
	public int updateByPrimaryKeyWithBLOBs(AskSpecial record) {
		return mapper.updateByPrimaryKeyWithBLOBs(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService#updateByPrimaryKey
	 * (com.chinamobile.hnu.xiangyu.ask.pojo.AskSpecial)
	 */
	@Override
	public int updateByPrimaryKey(AskSpecial record) {
		return mapper.updateByPrimaryKey(record);
	}

	/**
	 * 分页查询指定专场列表
	 */
	@Override
	public Page<AskSpecial> selectPage(Integer pageCurrent, Integer pageSize,
			Integer type, Integer userId) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<AskSpecial> spe = mapper.selectPage(type);
		if (spe.size() > 0) {
			spe.forEach(vo -> {
				if (StringUtils.isNotBlank(vo.getCoverImage())) {
					vo.setCoverImage(UploadDocumentUtil.buildPublicPath(vo
							.getCoverImage()));
				}
				vo.setTime((Utils.formatAgo(vo.getGmtCreate())));
			});
		}

		// 我的点赞;
		List<AskspecialLike> like = likeMapper.selectByUserId(userId);
		if (like.size() > 0) {
			for (AskSpecial info : spe) {
				for (AskspecialLike ali : like) {
					if (ali.getSpecialId().equals(info.getId())) {
						info.setIsLike(1);
					}
				}
			}
		}
		return new Page<>(spe);
	}

	/**
	 * 分页查询文章评论
	 */
	@Override
	public Page<AskInfoVo> selectPgComment(Integer pageCurrent,
			Integer pageSize, Integer id) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<AskInfoVo> infoVo = mapper.pgComment(id);
		if (infoVo.size() > 0) {
			infoVo.forEach(vo -> {
				if (StringUtils.isNotBlank(vo.getHeader())) {
					vo.setHeader(UploadDocumentUtil.buildPublicPath(vo
							.getHeader()));
				}
				vo.setTime((Utils.formatAgo(vo.getGmtCreate())));
			});
		}
		return new Page<>(infoVo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService#selectPgHotReview
	 * ()
	 */
	@Override
	public Page<AskInfoVo> selectPgHotReview(Integer pageCurrent,
			Integer pageSize, Integer id, Integer userId) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<AskInfoVo> infoVo = mapper.pgComment(id);
		if (infoVo.size() > 0) {
			Map<Long, AskInfoVo> voMap = new HashMap<>();
			infoVo.forEach(vo -> {
				voMap.put(vo.getId(), vo);
				vo.setAns("");
				if (StringUtils.isNotBlank(vo.getHeader())) {
					vo.setHeader(UploadDocumentUtil.buildPublicPath(vo
							.getHeader()));
				}
				vo.setTime(Utils.formatAgo(vo.getGmtCreate()));
			});

			// 二级评论
			List<AskCommentVo> commVo = commentMapper.selectByAskIdList(voMap
					.keySet());
			if (commVo.size() > 0) {
				for (AskCommentVo com : commVo) {
					if (StringUtils.isNotBlank(com.getHeader())) {
						com.setHeader(UploadDocumentUtil.buildPublicPath(com
								.getHeader()));
					}
					com.setTime(Utils.formatAgo(com.getGmtCreate()));

					List<AskCommentVo> comm = voMap.get(com.getAskId())
							.getCommentVos();
					if (comm == null) {
						comm = new ArrayList<AskCommentVo>();
						comm.add(com);
						voMap.get(com.getAskId()).setCommentVos(comm);
					} else {
						comm.add(com);
					}
				}
			}
		}

		// 是否已顶
		List<AskSpeakLike> like = speakLikeMapper.selectByUserId(userId);
		if (like.size() > 0) {
			for (AskInfoVo info : infoVo) {
				for (AskSpeakLike ali : like) {
					if (ali.getAskId().equals(info.getId())) {
						info.setUserLike(1);
					}
				}
			}
		}
		return new Page<>(infoVo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService#saveComment
	 * (java.lang.String, java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Map<String, Object> saveComment(Byte anonymity, String content,
			Long askid, Integer id, Integer type, Integer presentor) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 保存1级评论
		int i = 0;
		Long ids = null;
		if (type.equals(1)) {
			AskInfo record = new AskInfo();
			if (anonymity != null) {
				record.setAnonymity(anonymity);
			}
			record.setSpecialId(id);
			record.setGmtCreate(new Date());
			record.setName(content);
			record.setPresentor(presentor);
			infoMapper.insertSelective(record);
			ids = record.getId();
			map.put("id", ids);
			map.put("time", Utils.formatAgo(record.getGmtCreate()));
			// if (i > 0) {
			// // 更新问题评论数量
			// int commentAmount = commentMapper.selectCountByAskId(id);
			// AskInfo info = new AskInfo();
			// info.setCommentAmount(commentAmount);
			// info.setGmtModified(new Date());
			// info.setId(id);
			// mapper.updateByPrimaryKeySelective(info);
			// }
		}
		// 保存2级评论
		if (type.equals(2)) {
			if (askid != null) {
				AskComment record = new AskComment();
				record.setPresentor(presentor);
				record.setGmtCreate(new Date());
				record.setAskId(askid);
				record.setContent(content);
				record.setAnonymity(anonymity);
				commentMapper.insertSelective(record);
				ids = record.getId();
				map.put("id", ids);
				map.put("time", Utils.formatAgo(record.getGmtCreate()));
			}

			// if (i > 0) {
			// // 更新1级评论数量
			// int commentAmount = replyMapper.selectCountByCId(id);
			// AskComment comment = new AskComment();
			// comment.setCommentAmount(commentAmount);
			// comment.setId(id);
			// comment.setGmtModified(new Date());
			// commentMapper.updateByPrimaryKeySelective(comment);
			// }
		}
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService#likes(java
	 * .lang.Integer, java.lang.Long)
	 */
	@Override
	public ResponseDto likes(Integer userId, Integer specialId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("specialId", specialId);
		AskspecialLike like = likeMapper.selectByspecialId(map);
		// 如果用户已点赞，则直接取消点赞
		if (like != null) {
			AskspecialLike key = new AskspecialLike();
			key.setSpecialId(specialId);
			key.setMemberId(userId);
			int i = likeMapper.deleteByPrimaryKey(key);
			if (i > 0) {
				// 更新点赞数量
				int likeAmount = likeMapper.selectCountBySpecialId(specialId);
				AskSpecial info = new AskSpecial();
				info.setId(specialId);
				info.setLikeAmount(likeAmount);
				mapper.updateByPrimaryKeySelective(info);
				return ResultUtil.result(0, "取消点赞成功！");
			}
		}
		AskspecialLike record = new AskspecialLike();
		record.setSpecialId(specialId);
		record.setGmtCreate(new Date());
		record.setMemberId(userId);
		int i = likeMapper.insertSelective(record);
		if (i > 0) {
			int likeAmount = likeMapper.selectCountBySpecialId(specialId);
			AskSpecial info = new AskSpecial();
			info.setId(specialId);
			info.setLikeAmount(likeAmount);
			mapper.updateByPrimaryKeySelective(info);
			return ResultUtil.result(0, "点赞成功！",record.getId());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService#
	 * selectAskSpecialByList(java.util.Map, java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public Page<ClubSpeackVo> selectAskSpecialByList(Map<String, Object> map,
			Integer pageCurrent, Integer pageSize, Integer type) {
		List<ClubSpeackVo> vo = null;
		if (type.equals(1)) {
			PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
			vo = mapper.selectSpecialByList(map);
		} else {
			PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
			vo = mapper.selectAskSpecialByList(map);
		}

		return new Page<ClubSpeackVo>(vo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService#
	 * selectSpecialInfoById(java.lang.Integer)
	 */
	@Override
	public AskSpecialVo selectSpecialInfoById(Integer id) {
		AskSpecialVo vo = mapper.selectSpecialInfoById(id);
		if (StringUtils.isNotBlank(vo.getCoverImage())) {
			vo.setCoverImage(UploadDocumentUtil.buildPublicPath(vo
					.getCoverImage()));
		}
		return vo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService#deleteBatchSpecial
	 * (java.util.List)
	 */
	@Override
	public int deleteBatchSpecial(List<Integer> idList) {

		return mapper.deleteBatchSpecial(idList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService#selectreportList
	 * (java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Page<ClubSpeackVo> selectreportList(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<ClubSpeackVo> vo = mapper.selectreportList(map);
		return new Page<ClubSpeackVo>(vo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService#
	 * selectReportAskInfo(java.lang.Long)
	 */
	@Override
	public AaskInfoVo selectReportAskInfo(Long askId) {
		AaskInfoVo vo = mapper.selectReportAskInfo(askId);
		return vo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.ask.service.IAskSpecialService#
	 * deleteReportAskInfo(java.lang.Long)
	 */
	@Override
	public int deleteReportAskInfo(Long askId) {
		infoMapper.deleteByPrimaryKey(askId);
		reportMapper.deleteReportAskInfo(askId);
		return 0;
	}
}
