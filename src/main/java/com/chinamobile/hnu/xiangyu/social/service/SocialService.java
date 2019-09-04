/**
 * 
 */
package com.chinamobile.hnu.xiangyu.social.service;

import com.chinamobile.hnu.xiangyu.social.pojo.Photo;
import com.chinamobile.hnu.xiangyu.social.pojo.Social;
import com.chinamobile.hnu.xiangyu.social.vo.SocialVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author songcl
 * @date 2018年5月18日
 */
@Service
public interface SocialService {
	/**
	 * 添加用户姓名或标签
	 *
	 * @param record
	 */
	int insert(Social record);
	/**
	 * 获取用户信息
	 *
	 * @param user_id
	 */
	SocialVo getUserInfo(int user_id);
	/**
	 * 是否已关注
	 *
	 * @param user_id
	 * @param his_id
	 */
	int isMyConcern(int user_id,int his_id);
	/**
	 * 查看粉丝数
	 *
	 * @param user_id
	 */
    int myFollow(int user_id);
	/**
	 * 获取我的相册
	 *
	 * @param user_id
	 */
	List<Photo> getMyAlbum(int user_id);

	/**
	 * 获取与我兴趣相同的人
	 *
	 * @param user_id
	 */
	List<Integer> getSameLabel(int user_id);

	/**
	 * 获取某个年龄段的人
	 *
	 * @param min
	 * @param max
	 */
	List<Integer> selectByAge(int min,int max);

	/**
	 * 获取全部用户的UID
	 *
	 */
	List<Integer> getAll();

	/**
	 * 获取某一性别的用户
	 *
	 */
	List<Integer> selectBySex(int sex);

	/**
	 * 获取某一学校的用户
	 *
	 */
	List<Integer> selectByCollege(String college);

	/**
	 * 获取我的关注
	 *
	 */
	List<Integer> getMyConcern(int user_id);

}
