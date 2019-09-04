/**
 * 
 */
package com.chinamobile.hnu.xiangyu.user.service;

import java.util.List;
import java.util.Map;

import com.chinamobile.hnu.xiangyu.common.pojo.PubFeedback;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.pojo.UserCertification;
import com.chinamobile.hnu.xiangyu.user.pojo.UserFriend;
import com.chinamobile.hnu.xiangyu.user.pojo.UserLabel;
import com.chinamobile.hnu.xiangyu.user.pojo.UserReservedQuestion;
import com.chinamobile.hnu.xiangyu.user.vo.UserFavoriteVo;
import com.chinamobile.hnu.xiangyu.user.vo.UserHistoricalFootprintVo;
import com.chinamobile.hnu.xiangyu.util.dto.Page;

/**
 * @author The Old Man and the Sea
 *
 *         2018年6月7日
 */
public interface UserService {

	/**
	 * 插入用户预设问题
	 * 
	 * @param list
	 */
	void insertUserReservedQuestion(List<UserReservedQuestion> list);

	/**
	 * 添加用户姓名或标签
	 * 
	 * @param sex
	 * @param labels
	 */
	void insertUserLabelAndSex(Integer uid, Byte sex, String labels);

	/**
	 * 用户实名认证
	 * 
	 * @param certification
	 */
	void insertUserCertification(UserCertification certification);

	/**
	 * 查询用户预设问题
	 * 
	 * @param id
	 * @return
	 */
	List<UserReservedQuestion> selctUserReservedQuestion(Integer uid);

	/**
	 * 查询问题
	 * 
	 * @param questionId
	 * @return
	 */
	UserReservedQuestion selctUserReservedQuestionById(Integer questionId);

	/**
	 * 增加反馈内容
	 * 
	 * @param pubFeedback
	 */
	void insertPubFeedback(PubFeedback pubFeedback);

	/**
	 * 我的收藏分页
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<UserFavoriteVo> selectUserFavoriteByPage(Integer pageCurrent,
			Integer pageSize, Map<String, Object> map);

	/**
	 * 查询我的足迹
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param map
	 * @return
	 */
	Page<UserHistoricalFootprintVo> selectUserHistoricalFootprintByPage(
			Integer pageCurrent, Integer pageSize, Map<String, Object> map);

	/**
	 * 会员列表
	 * 
	 * @param pageCurrent
	 * @param pageSize
	 * @param maps
	 * @return
	 */
	Page<UserAccount> seleUserList(Integer pageCurrent, Integer pageSize,
			Map<String, Object> maps);

	/**
	 * 查询
	 * 
	 * @param id
	 * @return
	 */
	List<UserLabel> selectUserLabel(Integer uid);
	
	
	/****
	 * 社团，如果查询结果中的用户存在当前登录用户的备注，把结果用户的昵称改成备注.T必须包含属性uid(或者memberId、presentor、creator)和nickname(或者presentorName、creatorName)；
	 * @param t
	 * @param uid 当前登录用户的uid
	 * @return
	 */
	<T> T setFriendNickName(T t,int uid);
	
	/****
	 * 问问，如果查询结果中的用户存在当前登录用户的备注，把结果用户的昵称改成备注.T必须包含属性uid(或者memberId、presentor、creator)和nickname(或者presentorName、creatorName)；
	 * @param t
	 * @param uid
	 * @return
	 */
	<T> T setFriendNickNameAsk(T t,int uid);

	/**
	 * 修改用户备注
	 * @param userFriend
	 */
	void updateRemark(UserFriend userFriend);

}
