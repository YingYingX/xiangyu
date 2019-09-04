/**
 * 
 */
package com.chinamobile.hnu.xiangyu.social.service.impl;

import com.chinamobile.hnu.xiangyu.social.pojo.Photo;
import com.chinamobile.hnu.xiangyu.social.service.SocialService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.user.dao.UserCertificationMapper;
import com.chinamobile.hnu.xiangyu.social.pojo.Social;
import com.chinamobile.hnu.xiangyu.social.vo.SocialVo;
import com.chinamobile.hnu.xiangyu.social.dao.SocialMapper;
import com.chinamobile.hnu.xiangyu.user.service.UserAccountService;
import com.um.service.UmengService;

import java.util.List;

/**
 * 账号相关的后台操作接口
 * 
 * @author songcl
 * @date 2018年5月18日
 */
@Service
public class SocialServiceImpl implements SocialService {
	private final static Logger logger = Logger.getLogger(UserAccountService.class);
	@Autowired
	private SocialMapper dao;

	@Autowired
	private UserCertificationMapper certificationMapper;

	@Autowired
	private UmengService umengService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.user.service.AccountService#insert(com.
	 * chinamobile.hnu.xiangyu.user.pojo.UserAccount)
	 */
	@Override
	public int insert(Social record) {
		return dao.insert(record);
	}
	public SocialVo getUserInfo(int user_id){
		return dao.info(user_id);
	}
	public int isMyConcern(int user_id,int his_id){return dao.isMyConcern(user_id,his_id);}
	public int myFollow(int user_id){return dao.myFollow(user_id);}
	public List<Photo> getMyAlbum(int user_id){return dao.getMyAlbum(user_id);};
	public List<Integer> getSameLabel(int user_id){return dao.getSameLabel(user_id);}
	public List<Integer> selectByAge(int min,int max){return dao.selectByAge(min,max);}
	public List<Integer> getAll(){return dao.getAll();}
	public List<Integer> selectBySex(int sex) {
		return dao.selectBySex(sex);
	}
	public List<Integer> selectByCollege(String college) {
		return dao.selectByCollege(college);
	}
	public List<Integer> getMyConcern(int user_id) {
		return dao.getMyConcern(user_id);
	}
}
