package com.chinamobile.hnu.xiangyu.ask.service;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskPhoto;

/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version 2018-06-05 16:00:38
 */
public interface IAskPhotoService {

	int deleteByPrimaryKey(String photoId);

	int insert(AskPhoto record);

	int insertSelective(AskPhoto record);

	AskPhoto selectByPrimaryKey(String photoId);

	int updateByPrimaryKeySelective(AskPhoto record);

	int updateByPrimaryKey(AskPhoto record);

}
