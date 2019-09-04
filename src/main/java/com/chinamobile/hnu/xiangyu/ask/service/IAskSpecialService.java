package com.chinamobile.hnu.xiangyu.ask.service;

import java.util.List;
import java.util.Map;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskSpecial;
import com.chinamobile.hnu.xiangyu.ask.vo.AskInfoVo;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.util.dto.ResponseDto;
import com.chinamobile.hnu.xiangyu.web.api.vo.AaskInfoVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.AskSpecialVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.ClubSpeackVo;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-05 16:00:38
 */
public interface IAskSpecialService {

	int deleteByPrimaryKey(Integer id);

	int insert(AskSpecial record);

	int insertSelective(AskSpecial record);

	AskSpecial selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(AskSpecial record);

	int updateByPrimaryKeyWithBLOBs(AskSpecial record);

	int updateByPrimaryKey(AskSpecial record);

	/**
	 * 分页查询指定专场信息
	 * 
	 * @param type
	 *            ：专场信息
	 * @return
	 */
	Page<AskSpecial> selectPage(Integer pageCurrent, Integer pageSize,
			Integer type, Integer userId);

	/**
	 * 分页查询文章评论
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param id
	 *            :专场id
	 * @return
	 */
	Page<AskInfoVo> selectPgComment(Integer pageCurrent, Integer pageSize,
			Integer id);

	/**
	 * 专场热评分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param id
	 *            :专场id
	 * @return
	 */
	Page<AskInfoVo> selectPgHotReview(Integer pageCurrent, Integer pageSize,
			Integer id, Integer userId);

	/**
	 * 评论
	 * 
	 * @param content
	 * @param id
	 * @param askid
	 *            :问问id
	 * @param type
	 * @return
	 */
	Map<String, Object> saveComment(Byte anonymity, String content, Long askid,
			Integer id, Integer type, Integer presentor);

	/**
	 * 文章点赞
	 * 
	 * @param userId
	 * @param askid
	 * @return
	 */
	ResponseDto likes(Integer userId, Integer specialId);

	/**
	 * 专题or文章列表
	 * 
	 * @param map
	 * @param pageCurrent
	 * @param pageSize
	 * @return
	 */
	Page<ClubSpeackVo> selectAskSpecialByList(Map<String, Object> map,
			Integer pageCurrent, Integer pageSize, Integer type);

	/**
	 * 专题详情
	 * 
	 * @param id
	 * @return
	 */
	AskSpecialVo selectSpecialInfoById(Integer id);

	/**
	 * 批量删除专场
	 * 
	 * @param idList
	 */
	int deleteBatchSpecial(List<Integer> idList);

	/**
	 * @param pageCurrent
	 * @param pageSize
	 * @return
	 */
	Page<ClubSpeackVo> selectreportList(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map);

	/**
	 * @param askId
	 * @return
	 */
	AaskInfoVo selectReportAskInfo(Long askId);

	/**
	 * @param askId
	 */
	int deleteReportAskInfo(Long askId);
}
