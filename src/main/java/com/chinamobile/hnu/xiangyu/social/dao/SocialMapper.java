package com.chinamobile.hnu.xiangyu.social.dao;

import com.chinamobile.hnu.xiangyu.social.pojo.Social;
import com.chinamobile.hnu.xiangyu.social.vo.SocialVo;
import com.chinamobile.hnu.xiangyu.social.pojo.Photo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SocialMapper {
	/**
	 * 添加用户姓名或标签
	 *
	 * @param record
	 */

	int insert(Social record);
	SocialVo info(int user_id);
	int isMyConcern(@Param("user_id")int user_id,@Param("his_id")int his_id);
	int myFollow(int user_id);
	List<Photo> getMyAlbum(int user_id);
	List<Integer> getSameLabel(int user_id);
	List<Integer> selectByAge(@Param("min")int min,@Param("max")int max);
	List<Integer> getAll();
	List<Integer> selectBySex(int sex);
	List<Integer> selectByCollege(String college);
	List<Integer> getMyConcern(int user_id);
}