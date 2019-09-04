package com.chinamobile.hnu.xiangyu.ask.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskPhoto;

public interface AskPhotoMapper {

	int deleteByPrimaryKey(String photoId);

	int insert(AskPhoto record);

	int insertSelective(AskPhoto record);

	AskPhoto selectByPrimaryKey(String photoId);

	int updateByPrimaryKeySelective(AskPhoto record);

	int updateByPrimaryKey(AskPhoto record);

	/**
	 * 批量插入图片xx
	 * 
	 * @param photos
	 */
	int inserList(List<AskPhoto> photos);

	/**
	 * 查询图片
	 * 
	 * @param map
	 * @return
	 */
	List<AskPhoto> selectPhotos(Map<String, Object> map);

	/**
	 * @return
	 */
	List<AskPhoto> selectByAskIdList(@Param("set") Set<Long> keySet);
}