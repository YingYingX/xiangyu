package com.chinamobile.hnu.xiangyu.ask.service;

import java.util.List;
import java.util.Map;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskInfo;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskLabel;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskPhoto;
import com.chinamobile.hnu.xiangyu.ask.vo.AskCommentReplyVo;
import com.chinamobile.hnu.xiangyu.ask.vo.AskCommentVo;
import com.chinamobile.hnu.xiangyu.ask.vo.AskInfoVo;
import com.chinamobile.hnu.xiangyu.user.pojo.UserReport;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;
import com.chinamobile.hnu.xiangyu.web.api.vo.ClubSpeackVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.UserCertificationInfoVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.UserCertificationVo;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-05 16:00:38
 */
public interface IAskInfoService {

	int deleteByPrimaryKey(Long id);

	int insert(AskInfo record);

	int insertSelective(AskInfo record);

	AskInfo selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(AskInfo record);

	int updateByPrimaryKey(AskInfo record);

	/**
	 * 发布问题
	 * 
	 * @param info
	 * @param photos
	 * @return
	 */
	Long insertAskInfo(AskInfo info, List<AskPhoto> photos,
			List<AskLabel> labels);

	/**
	 * 分页查询问问列表
	 * 
	 * @param map
	 * @return
	 */
	Page<AskInfoVo> selectPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map, Integer userId);

	/**
	 * 5.1.2.查询指定专场的记录
	 * 
	 * @param map
	 * @return
	 */
	Page<AskInfoVo> pgspecial(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map);

	/**
	 * 5.1.3.分页查询问问的一级评论
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<AskCommentVo> pgfirst(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map, Integer userId, Long askid);

	/**
	 * 查询问问详情
	 * 
	 * @param map
	 * @return
	 */
	AskInfoVo askInfo(Map<String, Object> map, Integer userId);

	/**
	 * 一级评论详情
	 * 
	 * @param map
	 * @return
	 */
	AskCommentVo selectCommentById(Map<String, Object> map);

	/**
	 * 分页查询二级评论
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<AskCommentReplyVo> selectReplyByFirstId(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map);

	/**
	 * 评论
	 * 
	 * @param content
	 * @param id
	 * @param type
	 * @return
	 */
	Long saveComment(String content, Long id, Integer type, Integer presentor);

	/**
	 * 收藏or取消
	 * 
	 * @param askid
	 * @param op
	 * @param userId
	 * @return
	 */
	ResponseDto favorite(Long askid, Integer op, Integer userId);

	/**
	 * 举报问问
	 * 
	 * @param askid
	 * @param userId
	 * @return
	 */
	ResponseDto inform(Long askid, Integer userId, UserReport userReport);

	/**
	 * 问问点赞
	 * 
	 * @param userId
	 * @param askid
	 * @return
	 */
	ResponseDto likes(Integer userId, Long askid);

	/**
	 * 更新总的浏览记录数
	 * 
	 * @param askid
	 * @return
	 */
	int updateVisitAmount(Long askid);

	/**
	 * 一级评论 顶
	 * 
	 * @param userId
	 * @param id
	 * @return
	 */
	ResponseDto top(Integer userId, Long id);

	/**
	 * 修改问问昨日访问量
	 * 
	 */
	void updateAskYesterdayTraffic();

	/**
	 * 我的问题
	 * 
	 * @param map
	 * @return
	 */
	Page<AskInfoVo> selectMyListPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map, Integer userId);

	/**
	 * 5.1.3.分页查询问问的一级评论（后台）
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<AskCommentVo> commentfirst(Integer pageCurrent, Integer pageSize,
			Long askid);

	/**
	 * 分页查询问题列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<ClubSpeackVo> selectAskInfoList(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map);

	/**
	 * 认证会员列表
	 * 
	 * @return
	 */
	Page<UserCertificationVo> selectUserCertificationList(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map);

	/**
	 * 认证信息
	 * 
	 * @param userId
	 * @return
	 */
	UserCertificationInfoVo selectUserCertificationInfo(Integer userId);

	/**
	 * 通过标签查询分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<AskInfoVo> selectLabelPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map);

	/**
	 * 公众号问问列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<AskInfoVo> selectGzhPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map);

	/**
	 * 公众号问问详情
	 * 
	 * @param map
	 * @return
	 */
	AskInfoVo GzhAskInfo(Long id) throws Exception;

	/**
	 * 公众号问问评论列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<AskCommentVo> GzhPgfirst(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map);

	/**
	 * 查询是否有重复的问问
	 * 
	 * @param map
	 * @return
	 */
	List<AskInfo> selectByExample(Map<String, Object> map);
}
