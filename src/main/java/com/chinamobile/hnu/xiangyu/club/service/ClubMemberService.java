/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.service;

import java.util.List;
import java.util.Map;

import com.chinamobile.hnu.xiangyu.club.pojo.ClubMember;

/**
 * @author The Old Man and the Sea
 *
 *         2018年6月5日
 */
public interface ClubMemberService {

	/**
	 * 查询群成员
	 * 
	 * @param clubId
	 * @return
	 */
	List<ClubMember> selectClubMember(Integer clubId);

	/**
	 * 修改群成员昵称
	 * 
	 * @param clubMember
	 */
	void updateClubMember(ClubMember clubMember);

	/**
	 * 查询团员
	 * 
	 * @param id
	 * @return
	 */
	ClubMember selectClubMemberById(Long id);

	/****
	 * 查询用户是否是群管理
	 * 
	 * @param clubId
	 * @param uid
	 * @return true 是
	 */
	boolean userIsClubAdmin(Integer clubId, Integer uid);

	/**
	 * 查询团队管理员
	 * 
	 * @param id
	 * @return
	 */
	List<ClubMember> selectClubAdmin(Integer clubId);

	/**
	 * 是否加入过团队
	 * 
	 * @param clubId
	 * @param id
	 * @return
	 */
	ClubMember selectUserJoin(Integer clubId, Integer uid);

	/**
	 * 移除群成员
	 * 
	 * @param clubId
	 * @param uid
	 */
	void deleteClubMember(Integer clubId, Integer uid, Integer aid);

	/**
	 * 新增团队成员
	 * 
	 * @param member
	 * @param op
	 *            1-加入此团队；2-退出团队
	 */
	void inertClubMember(ClubMember member, Integer op, Integer userId,
			Integer nid);

	/**
	 * 查询群成员（后台）
	 * 
	 * @param clubId
	 * @return
	 */
	List<ClubMember> selectClubMemberByClubId(Map<String, Object> map);

}
