package com.chinamobile.hnu.xiangyu.ask.service;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskComment;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.web.api.vo.AskComentVo;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-05 16:00:37
 */
public interface IAskCommentService {
	int deleteByPrimaryKey(Long id);

	int insert(AskComment record);

	int insertSelective(AskComment record);

	AskComment selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(AskComment record);

	int updateByPrimaryKey(AskComment record);

	/**
	 * 删除一级评论和二级评论
	 * 
	 * @param id
	 */
	int deleteCommentById(Long id);

	/**
	 * 分页查询问问评论（后台）
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param askId
	 * @return
	 */
	Page<AskComentVo> selectAskComentByAskId(Integer pageCurrent,
			Integer pageSize, Integer askId);

}
