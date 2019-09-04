package com.chinamobile.hnu.xiangyu.club.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.chinamobile.hnu.xiangyu.club.pojo.Photo;

public interface ClubSpeakPhotoMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_photo
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int deleteByPrimaryKey(String photoId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_photo
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int insert(Photo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_photo
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int insertSelective(Photo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_photo
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	Photo selectByPrimaryKey(String photoId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_photo
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int updateByPrimaryKeySelective(Photo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table club_speak_photo
	 *
	 * @mbg.generated Thu May 17 23:34:42 CST 2018
	 */
	int updateByPrimaryKey(Photo record);

	/**
	 * 保存团言图片
	 * 
	 * @param photos
	 */
	void inserList(List<Photo> photos);

	/**
	 * 查询团言图片
	 * 
	 * @param keySet
	 * @return
	 */
	List<Photo> selectBySpeakIdList(@Param("set") Set<Long> keySet);

	/**
	 * 团言照片(后台)
	 * 
	 * @param id
	 * @return
	 */
	List<Photo> selectBySpeakList(Long id);
}