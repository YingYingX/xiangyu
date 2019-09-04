/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamobile.hnu.xiangyu.club.dao.ClubActivityApplicantMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubActivityMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubActivityPhotoMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubNoticeMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubReleaseMapper;
import com.chinamobile.hnu.xiangyu.club.dao.ClubVoteInfoMapper;
import com.chinamobile.hnu.xiangyu.club.pojo.Photo;
import com.chinamobile.hnu.xiangyu.club.service.ClubMemberService;
import com.chinamobile.hnu.xiangyu.club.service.ClubReleaseService;
import com.chinamobile.hnu.xiangyu.club.vo.ClubActivityVo;
import com.chinamobile.hnu.xiangyu.club.vo.ClubNoticeVo;
import com.chinamobile.hnu.xiangyu.club.vo.ClubReleaseVo;
import com.chinamobile.hnu.xiangyu.club.vo.ClubVoteVo;
import com.chinamobile.hnu.xiangyu.club.vo.LikeVo;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author The Old Man and the Sea
 *
 * 2018年6月6日
 */
@Service
public class ClubReleaseServiceImpl implements ClubReleaseService {
	
	@Autowired
	private ClubReleaseMapper releaseMapper;
	
	@Autowired
	private ClubVoteInfoMapper voteMapper;
	
	@Autowired
	private ClubActivityMapper activityMapper;
	
	@Autowired
	private ClubNoticeMapper noticeMapper;
	
	@Autowired
	private ClubActivityApplicantMapper applicantMapper;
	
	@Autowired
	private ClubActivityPhotoMapper photoMapper;
	

	/* (non-Javadoc)
	 * @see com.chinamobile.hnu.xiangyu.club.service.ClubReleaseService#selectByPage(java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<ClubReleaseVo> selectByPage(Integer pageCurrent, Integer pageSize, Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageCurrent <= 0?1:pageCurrent, pageSize);
		List<ClubReleaseVo> list = releaseMapper.selectByPage(map);
		//返回的vo类
		if(list.size() > 0){
			//投票
			Map<Long,ClubVoteVo> voteMap = new HashMap<>();
			//活动
			Map<Long,ClubActivityVo> activityMap = new HashMap<>();
			//公告
			Map<Long,ClubNoticeVo> noticeMap = new HashMap<>();
			
			for (ClubReleaseVo clubRelease : list) {
				if(clubRelease.getType() == 1){
					voteMap.put(clubRelease.getBzid(), null);
				}else if(clubRelease.getType() == 2){
					activityMap.put(clubRelease.getBzid(), null);
				}else if(clubRelease.getType() == 3){
					noticeMap.put(clubRelease.getBzid(), null);
				}
			}
			if(voteMap.keySet().size() > 0){
				map.put("idList", voteMap.keySet());
				List<ClubVoteVo> voteList = voteMapper.selectByPage(map);
				if(voteList.size() > 0){
					for (ClubVoteVo clubVoteVo : voteList) {
						voteMap.put(clubVoteVo.getId(), clubVoteVo);
						if(StringUtils.isNotBlank(clubVoteVo.getHeader())){
							clubVoteVo.setHeader(UploadDocumentUtil.buildPublicPath(clubVoteVo.getHeader()));
						}
					}
				}
			}
			if(activityMap.keySet().size() > 0){
				map.put("idList", activityMap.keySet());
				List<ClubActivityVo> activityList = activityMapper.selectByPage(map);
				if(activityList.size() > 0){
					for (ClubActivityVo clubActivityVo : activityList) {
						activityMap.put(clubActivityVo.getId(), clubActivityVo);
						if(StringUtils.isNotBlank(clubActivityVo.getHeader())){
							clubActivityVo.setHeader(UploadDocumentUtil.buildPublicPath(clubActivityVo.getHeader()));
						}
					}
					
					// 活动的照片
					List<Photo> photos = photoMapper.selectByActivityIdList(activityMap.keySet());
					for (Photo clubActivityPhoto : photos) {
						ClubActivityVo vo = activityMap.get(clubActivityPhoto.getBzId());
						if(vo == null){
							continue;
						}
						List<Photo> photoes = vo.getPhotos();
						if(StringUtils.isNotBlank(clubActivityPhoto.getPhotoId())){
							clubActivityPhoto.setPhotoId(UploadDocumentUtil.buildPublicPath(clubActivityPhoto.getPhotoId()));
						}
						if (photoes == null) {
							photoes = new ArrayList<>();
							photoes.add(clubActivityPhoto);
							activityMap.get(clubActivityPhoto.getBzId()).setPhotos(photoes);
						} else {
							photoes.add(clubActivityPhoto);
						}
					}
					
					// 参加人
					List<LikeVo> likes = applicantMapper.selectByActivityIdList(activityMap.keySet());
					for (LikeVo likeVo : likes) {
						if (StringUtils.isNotBlank(likeVo.getHeader())) {
							likeVo.setHeader(UploadDocumentUtil.buildPublicPath(likeVo.getHeader()));
						}
						
						ClubActivityVo vo = activityMap.get(likeVo.getBzId());
						if(vo == null ){
							continue;
						}
						List<LikeVo> like = vo.getLikes();
						if (like == null) {
							like = new ArrayList<LikeVo>();
							like.add(likeVo);
							activityMap.get(likeVo.getBzId()).setLikes(like);
						} else {
							like.add(likeVo);
						}
						// 我是否参加过
						int uid = (int)map.get("uid");
						if (likeVo.getUid().intValue() == uid) {
							activityMap.get(likeVo.getBzId()).setIsLike((byte) 1);
						}
					}
					
				}
			}
			if(noticeMap.keySet().size() > 0){
				map.put("idList", noticeMap.keySet());
				List<ClubNoticeVo> noticeList = noticeMapper.selectByPage(map);
				for (ClubNoticeVo clubNoticeVo : noticeList) {
					noticeMap.put(clubNoticeVo.getId(), clubNoticeVo);
				}
			}
			
			//赋值vo实体类
			for (ClubReleaseVo clubRelease : list) {
				if(clubRelease.getType() == 1){
					clubRelease.setObj(voteMap.get(clubRelease.getBzid()));
				}else if(clubRelease.getType() == 2){
					clubRelease.setObj(activityMap.get(clubRelease.getBzid()));
				}else if(clubRelease.getType() == 3){
					clubRelease.setObj(noticeMap.get(clubRelease.getBzid()));
				}
			}
			
		}
		
		return new Page<>(list);
	}

}
