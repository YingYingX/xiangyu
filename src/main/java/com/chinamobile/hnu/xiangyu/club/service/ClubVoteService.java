/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.service;

import java.util.List;
import java.util.Map;

import com.chinamobile.hnu.xiangyu.club.pojo.ClubVoteInfo;
import com.chinamobile.hnu.xiangyu.club.pojo.ClueVoteRecord;
import com.chinamobile.hnu.xiangyu.club.pojo.ClueVoteRecordKey;
import com.chinamobile.hnu.xiangyu.club.vo.ClubVoteVo;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.chinamobile.hnu.xiangyu.web.api.vo.ClubSpeackVo;
import com.chinamobile.hnu.xiangyu.web.api.vo.VoteInfoVo;

/**
 * @author The Old Man and the Sea
 *
 *         2018年6月4日
 */
public interface ClubVoteService {

	/**
	 * 发布投票
	 * 
	 * @param clubVoteInfo
	 */
	void insertClubVoteInfo(ClubVoteInfo clubVoteInfo);

	/**
	 * 投票分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<ClubVoteVo> selectByPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map);

	/**
	 * 查询投票详情
	 * 
	 * @param voteId
	 * @param id
	 * @return
	 */
	ClubVoteVo selectClubVoteDetail(Long voteId, Integer uid);

	/**
	 * 查询投票基本信息
	 * 
	 * @param voteId
	 * @return
	 */
	ClubVoteInfo selectClubVoteById(Long voteId);

	/**
	 * 删除投票
	 * 
	 * @param voteId
	 */
	void deleteDeleteClubVoteById(Long voteId);

	/**
	 * 查询用户是否已经投票
	 * 
	 * @param key
	 * @return
	 */
	List<ClueVoteRecord> selectByPrimaryKey(ClueVoteRecordKey key);

	/**
	 * 修改投票数
	 * 
	 * @param voteId
	 * @param optionId
	 */
	void updateClubVoteOption(Long voteId, String optionId, Integer uid);

	/**
	 * 投票过期处理
	 */
	void updateVoteStatus();

	/**
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<ClubSpeackVo> selectVoteList(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map);

	/**
	 * 投票详情（后台）
	 * 
	 * @param voteId
	 * @return
	 */
	List<VoteInfoVo> selectVoteInfoByVoteId(Long voteId);

	int deleteById(ClubVoteInfo record);
}
