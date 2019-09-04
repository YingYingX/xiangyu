/**
 * 
 */
package com.chinamobile.hnu.xiangyu.user.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinamobile.hnu.xiangyu.ask.dao.AskInfoMapper;
import com.chinamobile.hnu.xiangyu.ask.dao.AskLabelMapper;
import com.chinamobile.hnu.xiangyu.ask.dao.AskPhotoMapper;
import com.chinamobile.hnu.xiangyu.ask.dao.AskSpeakLikeMapper;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskLabel;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskPhoto;
import com.chinamobile.hnu.xiangyu.ask.pojo.AskSpeakLike;
import com.chinamobile.hnu.xiangyu.ask.vo.AskInfoVo;
import com.chinamobile.hnu.xiangyu.club.dao.ClubInfoMapper;
import com.chinamobile.hnu.xiangyu.club.vo.ClubVo;
import com.chinamobile.hnu.xiangyu.common.dao.PubFeedbackMapper;
import com.chinamobile.hnu.xiangyu.common.pojo.PubFeedback;
import com.chinamobile.hnu.xiangyu.user.dao.UserAccountMapper;
import com.chinamobile.hnu.xiangyu.user.dao.UserCertificationMapper;
import com.chinamobile.hnu.xiangyu.user.dao.UserFavoriteMapper;
import com.chinamobile.hnu.xiangyu.user.dao.UserFriendMapper;
import com.chinamobile.hnu.xiangyu.user.dao.UserHistoricalFootprintMapper;
import com.chinamobile.hnu.xiangyu.user.dao.UserLabelMapper;
import com.chinamobile.hnu.xiangyu.user.dao.UserReservedQuestionMapper;
import com.chinamobile.hnu.xiangyu.user.pojo.UserAccount;
import com.chinamobile.hnu.xiangyu.user.pojo.UserCertification;
import com.chinamobile.hnu.xiangyu.user.pojo.UserFavorite;
import com.chinamobile.hnu.xiangyu.user.pojo.UserFriend;
import com.chinamobile.hnu.xiangyu.user.pojo.UserLabel;
import com.chinamobile.hnu.xiangyu.user.pojo.UserReservedQuestion;
import com.chinamobile.hnu.xiangyu.user.service.UserService;
import com.chinamobile.hnu.xiangyu.user.vo.UserFavoriteVo;
import com.chinamobile.hnu.xiangyu.user.vo.UserHistoricalFootprintVo;
import com.chinamobile.hnu.xiangyu.util.UploadDocumentUtil;
import com.chinamobile.hnu.xiangyu.util.Utils;
import com.chinamobile.hnu.xiangyu.util.dto.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author The Old Man and the Sea
 *
 *         2018年6月7日
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserReservedQuestionMapper questionMapper;

	@Autowired
	private UserAccountMapper accountMapper;

	@Autowired
	private UserLabelMapper labelMapper;

	@Autowired
	private UserCertificationMapper certificationMapper;

	@Autowired
	private PubFeedbackMapper feedbackMapper;

	@Autowired
	private UserFavoriteMapper favoriteMapper;

	@Autowired
	private ClubInfoMapper clubMapper;

	@Autowired
	private UserHistoricalFootprintMapper footprintMapper;

	@Autowired
	protected AskInfoMapper infoMapper;

	@Autowired
	protected AskPhotoMapper photoMapper;

	@Autowired
	protected AskLabelMapper askLabelMapper;

	@Autowired
	protected AskSpeakLikeMapper likeMapper;

	@Autowired
	private UserFriendMapper friendMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.user.service.UserService#
	 * insertUserReservedQuestion(java.util.List)
	 */
	@Override
	public void insertUserReservedQuestion(List<UserReservedQuestion> list) {
		// TODO Auto-generated method stub
		if (list.size() > 0) {
			questionMapper.deleteByUid(list.get(0).getUserId());
			questionMapper.insertList(list);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.user.service.UserService#
	 * insertUserLabelAndSex (java.lang.Integer, java.lang.String)
	 */
	@Transactional
	@Override
	public void insertUserLabelAndSex(Integer uid, Byte sex, String labels) {
		// TODO Auto-generated method stub
		UserAccount user = new UserAccount();
		user.setId(uid);
		user.setSex(sex);
		accountMapper.updateByPrimaryKeySelective(user);
		if (StringUtils.isNotBlank(labels)) {
			List<UserLabel> userLabels = new ArrayList<>();
			String[] labelArray = labels.split(",");
			for (String str : labelArray) {
				if (StringUtils.isNotBlank(str)) {
					UserLabel userLabel = new UserLabel();
					userLabel.setLabel(str);
					userLabel.setUserId(uid);
					userLabels.add(userLabel);
				}
			}
			if (userLabels.size() > 0) {
				labelMapper.deleteByUid(uid);
				labelMapper.insertList(userLabels);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.user.service.UserService#
	 * insertUserCertification
	 * (com.chinamobile.hnu.xiangyu.user.pojo.UserCertification)
	 */
	@Transactional
	@Override
	public void insertUserCertification(UserCertification certification) {
		// TODO Auto-generated method stub
		UserCertification userCert = certificationMapper.selectByPrimaryKey(certification.getUserId());
		if (userCert != null) {
			certification.setGmtModified(new Date());
			certificationMapper.updateByPrimaryKeySelective(certification);
		} else {
			certification.setGmtCreate(new Date());
			certificationMapper.insertSelective(certification);
		}
		UserAccount userAccount = new UserAccount();
		userAccount.setCertFlag((byte) 1);
		userAccount.setId(certification.getUserId());
		accountMapper.updateByPrimaryKeySelective(userAccount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.user.service.UserService#
	 * selctUserReservedQuestion(java.lang.Integer)
	 */
	@Override
	public List<UserReservedQuestion> selctUserReservedQuestion(Integer uid) {
		// TODO Auto-generated method stub
		return questionMapper.selctUserReservedQuestion(uid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.user.service.UserService#
	 * selctUserReservedQuestionById(java.lang.Integer)
	 */
	@Override
	public UserReservedQuestion selctUserReservedQuestionById(Integer questionId) {
		// TODO Auto-generated method stub
		return questionMapper.selectByPrimaryKey(questionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.UserService#insertPubFeedback
	 * (com.chinamobile.hnu.xiangyu.common.pojo.PubFeedback)
	 */
	@Override
	public void insertPubFeedback(PubFeedback pubFeedback) {
		// TODO Auto-generated method stub
		pubFeedback.setGmtCreate(new Date());
		feedbackMapper.insertSelective(pubFeedback);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * 
	 *  18.09.12修改：把问问和团队收藏分开
	 * 
	 * @see com.chinamobile.hnu.xiangyu.user.service.UserService#
	 * selectUserFavoriteByPage (java.lang.Integer, java.lang.Integer,
	 * java.util.Map)
	 */
	@Override
	public Page<UserFavoriteVo> selectUserFavoriteByPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<UserFavoriteVo> list = favoriteMapper.selectByPage(map);
		if (list.size() > 0) {
			Set<Long> askSet = new HashSet<>();
			Set<Long> clubSet = new HashSet<>();

			Map<Long, UserFavoriteVo> askMap = new HashMap<>();
			Map<Long, UserFavoriteVo> clubMap = new HashMap<>();

			for (UserFavoriteVo userFavoriteVo : list) {
				if (userFavoriteVo.getCategory() == 1) {
					askSet.add(userFavoriteVo.getBizId());
					askMap.put(userFavoriteVo.getBizId(), userFavoriteVo);
				} else {
					clubSet.add(userFavoriteVo.getBizId());
					clubMap.put(userFavoriteVo.getBizId(), userFavoriteVo);
				}
			}
			// 收藏的问问
			if (askSet.size() > 0) {
				List<AskInfoVo> askInfoVo = infoMapper.selectUserVisitHistoryByUserId(askSet);
				Map<Long, AskInfoVo> voMap = new HashMap<>();
				if (askInfoVo.size() > 0) {
					askInfoVo.forEach(vo -> {
						voMap.put(vo.getId(), vo);
						if (StringUtils.isNotBlank(vo.getHeader())) {
							vo.setHeader(UploadDocumentUtil.buildPublicPath(vo.getHeader()));
						}
						vo.setTime(Utils.formatAgo(vo.getGmtCreate()));
						voMap.put(vo.getId(), vo);
					});

					// 图片
					List<AskPhoto> photos = photoMapper.selectByAskIdList(voMap.keySet());
					if (photos.size() > 0) {
						for (AskPhoto ph : photos) {
							List<AskPhoto> askPhoto = voMap.get(ph.getAskId()).getPhotos();
							if (StringUtils.isNotBlank(ph.getPhotoId())) {
								ph.setPhotoId(UploadDocumentUtil.buildPublicPath(ph.getPhotoId()));
							}
							if (askPhoto == null) {
								askPhoto = new ArrayList<AskPhoto>();
								askPhoto.add(ph);
								voMap.get(ph.getAskId()).setPhotos(askPhoto);
							} else {
								askPhoto.add(ph);
							}
						}
					}

					// 标签
					List<AskLabel> label = askLabelMapper.selectByAskIdList(voMap.keySet());
					if (label.size() > 0) {
						for (AskLabel askLabel : label) {
							List<AskLabel> labels = voMap.get(askLabel.getAskId()).getLabels();
							if (labels == null) {
								labels = new ArrayList<AskLabel>();
								labels.add(askLabel);
								voMap.get(askLabel.getAskId()).setLabels(labels);
							} else {
								labels.add(askLabel);
							}
						}
					}

					// 我的点赞;
					Integer userId = (Integer) map.get("uid");
					List<AskSpeakLike> like = likeMapper.selectByUserId(userId);
					if (like.size() > 0) {
						for (AskInfoVo info : askInfoVo) {
							for (AskSpeakLike ali : like) {
								if (ali.getAskId().equals(info.getId())) {
									info.setUserLike(1);
								}
							}
						}
					}

					// 我的收藏
					List<UserFavorite> uf = favoriteMapper.selectByUserId(userId);
					if (uf.size() > 0) {
						for (AskInfoVo info : askInfoVo) {
							for (UserFavorite ali : uf) {
								if (ali.getBizId().equals(info.getId())) {
									info.setIsCollect(1);
								}
							}
						}
					}
				}
				for (AskInfoVo vo : askInfoVo) {
					askMap.get(vo.getId()).setObj(vo);
				}
			}
			// 收藏的团队
			if (clubSet.size() > 0) {
				List<ClubVo> clubList = clubMapper.selectByIdList(clubSet);
				for (ClubVo clubInfo : clubList) {
					if (StringUtils.isNotBlank(clubInfo.getLogoImage())) {
						clubInfo.setLogoImage(UploadDocumentUtil.buildPublicPath(clubInfo.getLogoImage()));
					}
					clubMap.get(Long.valueOf(clubInfo.getId())).setObj(clubInfo);
				}
			}
		}
		return new Page<UserFavoriteVo>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinamobile.hnu.xiangyu.user.service.UserService#
	 * selectUserHistoricalFootprintByPage(java.lang.Integer, java.lang.Integer,
	 * java.util.Map)
	 */
	@Override
	public Page<UserHistoricalFootprintVo> selectUserHistoricalFootprintByPage(Integer pageCurrent, Integer pageSize,
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<UserHistoricalFootprintVo> list = footprintMapper.selectByPage(map);
		if (list.size() > 0) {
			Set<Long> idSet = new HashSet<>();
			for (UserHistoricalFootprintVo userHistoricalFootprintVo : list) {
				idSet.add(userHistoricalFootprintVo.getHistoricalFootprintId());
			}
			// 1.问问。2.团队
			if ((int) map.get("type") == 1) {
				List<AskInfoVo> askInfoVo = infoMapper.selectUserVisitHistoryByUserId(idSet);
				Map<Long, AskInfoVo> voMap = new HashMap<>();
				if (askInfoVo.size() > 0) {
					askInfoVo.forEach(vo -> {
						voMap.put(vo.getId(), vo);
						if (StringUtils.isNotBlank(vo.getHeader())) {
							vo.setHeader(UploadDocumentUtil.buildPublicPath(vo.getHeader()));
						}
						vo.setTime(Utils.formatAgo(vo.getGmtCreate()));
						voMap.put(vo.getId(), vo);
					});

					// 图片
					List<AskPhoto> photos = photoMapper.selectByAskIdList(voMap.keySet());
					if (photos.size() > 0) {
						for (AskPhoto ph : photos) {
							List<AskPhoto> askPhoto = voMap.get(ph.getAskId()).getPhotos();
							if (StringUtils.isNotBlank(ph.getPhotoId())) {
								ph.setPhotoId(UploadDocumentUtil.buildPublicPath(ph.getPhotoId()));
							}
							if (askPhoto == null) {
								askPhoto = new ArrayList<AskPhoto>();
								askPhoto.add(ph);
								voMap.get(ph.getAskId()).setPhotos(askPhoto);
							} else {
								askPhoto.add(ph);
							}
						}
					}

					// 标签
					List<AskLabel> label = askLabelMapper.selectByAskIdList(voMap.keySet());
					if (label.size() > 0) {
						for (AskLabel askLabel : label) {
							List<AskLabel> labels = voMap.get(askLabel.getAskId()).getLabels();
							if (labels == null) {
								labels = new ArrayList<AskLabel>();
								labels.add(askLabel);
								voMap.get(askLabel.getAskId()).setLabels(labels);
							} else {
								labels.add(askLabel);
							}
						}
					}

					// 我的点赞;
					Integer userId = (Integer) map.get("uid");
					List<AskSpeakLike> like = likeMapper.selectByUserId(userId);
					if (like.size() > 0) {
						for (AskInfoVo info : askInfoVo) {
							for (AskSpeakLike ali : like) {
								if (ali.getAskId().equals(info.getId())) {
									info.setUserLike(1);
								}
							}
						}
					}

					// 我的收藏
					List<UserFavorite> uf = favoriteMapper.selectByUserId(userId);
					if (uf.size() > 0) {
						for (AskInfoVo info : askInfoVo) {
							for (UserFavorite ali : uf) {
								if (ali.getBizId().equals(info.getId())) {
									info.setIsCollect(1);
								}
							}
						}
					}
				}
				for (UserHistoricalFootprintVo userHistoricalFootprintVo : list) {
					userHistoricalFootprintVo.setObj(voMap.get(userHistoricalFootprintVo.getHistoricalFootprintId()));
				}
			} else {
				List<ClubVo> clubList = clubMapper.selectByIdList(idSet);
				Map<Long, ClubVo> voMap = new HashMap<>();
				for (ClubVo clubInfo : clubList) {
					if (StringUtils.isNotBlank(clubInfo.getLogoImage())) {
						clubInfo.setLogoImage(UploadDocumentUtil.buildPublicPath(clubInfo.getLogoImage()));
					}
					voMap.put(Long.valueOf(clubInfo.getId()), clubInfo);
				}
				for (UserHistoricalFootprintVo userHistoricalFootprintVo : list) {
					userHistoricalFootprintVo.setObj(voMap.get(userHistoricalFootprintVo.getHistoricalFootprintId()));
				}

			}
		}
		return new Page<UserHistoricalFootprintVo>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.UserService#seleUserList(java
	 * .lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public Page<UserAccount> seleUserList(Integer pageCurrent, Integer pageSize, Map<String, Object> maps) {
		PageHelper.startPage(pageCurrent <= 0 ? 1 : pageCurrent, pageSize);
		List<UserAccount> users = accountMapper.seleUserList(maps);
		return new Page<UserAccount>(users);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.UserService#selectUserLabel(
	 * java.lang.Integer)
	 */
	@Override
	public List<UserLabel> selectUserLabel(Integer uid) {
		// TODO Auto-generated method stub
		return labelMapper.selectUserLabel(uid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.UserService#setFriendNickName(
	 * java.lang.Object)
	 */
	@Override
	public <T> T setFriendNickName(T t, int uid) {
		// TODO Auto-generated method stub
		if (t == null) {
			return t;
		}
		// 其他用户uid
		List<Integer> uidList = new ArrayList<>();
		String fieldName = null;
		try {
			if (t instanceof List) {
				List list = (List) t;
				if (list.size() > 0) {
					for (Object obj : list) {
						Field field1 = null;
						Field field2 = null;
						Field field3 = null;
						Field field4 = null;
						try {
							field1 = obj.getClass().getDeclaredField("uid");
						} catch (NoSuchFieldException e) {

						}
						try {
							field2 = obj.getClass().getDeclaredField("memberId");
						} catch (NoSuchFieldException e) {
						}
						if (field2 == null) {
							try {
								field2 = obj.getClass().getSuperclass().getDeclaredField("memberId");
							} catch (NoSuchFieldException e) {
							}
						}
						try {
							field3 = obj.getClass().getDeclaredField("presentor");
						} catch (NoSuchFieldException e) {

						}
						try {
							field4 = obj.getClass().getDeclaredField("creator");
						} catch (NoSuchFieldException e) {

						}

						if (field1 == null && field2 == null && field3 == null && field4 == null) {
							return t;
						}

						if (field1 != null) {
							field1.setAccessible(true);
							Integer fuid = (Integer) field1.get(obj);
							if (fuid != null) {
								uidList.add(fuid);
							}
							fieldName = "uid";
						} else if (field2 != null) {
							field2.setAccessible(true);
							Integer fuid = (Integer) field2.get(obj);
							if (fuid != null) {
								uidList.add(fuid);
							}
							fieldName = "memberId";
						} else if (field3 != null) {
							field3.setAccessible(true);
							Integer fuid = (Integer) field3.get(obj);
							if (fuid != null) {
								uidList.add(fuid);
							}
							fieldName = "presentor";
						} else if (field4 != null) {
							field4.setAccessible(true);
							Integer fuid = (Integer) field4.get(obj);
							if (fuid != null) {
								uidList.add(fuid);
							}
							fieldName = "creator";
						} else {
							return t;
						}
					}

					if (uidList.size() > 0) {
						List<UserFriend> friendList = friendMapper.selectFriends(uidList, uid);
						int index = 0;
						if (friendList.size() > 0) {
							Map<Integer, UserFriend> friendMap = new HashMap<>();
							for (UserFriend userFriend : friendList) {
								friendMap.put(userFriend.getFriendId(), userFriend);
							}
							for (Object obj : list) {

								Field nickname1 = null;
								try {
									if (fieldName.equals("uid") || fieldName.equals("memberId")) {
										nickname1 = obj.getClass().getDeclaredField("nickname");
									} else if (fieldName.equals("presentor")) {
										nickname1 = obj.getClass().getDeclaredField("presentorName");
									} else if (fieldName.equals("creator")) {
										nickname1 = obj.getClass().getDeclaredField("creatorName");
									}
								} catch (NoSuchFieldException e) {

								}

								if (nickname1 == null) {
									return t;
								}
								UserFriend friend = friendMap.get(uidList.get(index++));
								if (friend != null && nickname1 != null) {
									nickname1.setAccessible(true);
									nickname1.set(obj, friend.getAliasName());
								}
							}
						}
					}

				}
			} else {
				Field field1 = null;
				Field field2 = null;
				Field field3 = null;
				Field field4 = null;
				try {
					field1 = t.getClass().getDeclaredField("uid");
				} catch (NoSuchFieldException e) {

				}
				try {
					field2 = t.getClass().getDeclaredField("memberId");
				} catch (NoSuchFieldException e) {

				}
				if (field2 == null) {
					try {
						field2 = t.getClass().getSuperclass().getDeclaredField("memberId");
					} catch (NoSuchFieldException e) {

					}
				}
				try {
					field3 = t.getClass().getDeclaredField("presentor");
				} catch (NoSuchFieldException e) {

				}
				try {
					field4 = t.getClass().getDeclaredField("creator");
				} catch (NoSuchFieldException e) {

				}
				if (field1 == null && field2 == null && field3 == null && field4 == null) {
					return t;
				}

				if (field1 != null) {
					field1.setAccessible(true);
					Integer fuid = (Integer) field1.get(t);
					if (fuid != null) {
						uidList.add(fuid);
					}
					fieldName = "uid";
				} else if (field2 != null) {
					field2.setAccessible(true);
					Integer fuid = (Integer) field2.get(t);
					if (fuid != null) {
						uidList.add(fuid);
					}
					fieldName = "memberId";
				} else if (field3 != null) {
					field3.setAccessible(true);
					Integer fuid = (Integer) field3.get(t);
					if (fuid != null) {
						uidList.add(fuid);
					}
					fieldName = "presentor";
				} else if (field4 != null) {
					field4.setAccessible(true);
					Integer fuid = (Integer) field4.get(t);
					if (fuid != null) {
						uidList.add(fuid);
					}
					fieldName = "creator";
				} else {
					return t;
				}

				if (uidList.size() > 0) {
					Field nickname1 = null;
					try {
						if (fieldName.equals("uid") || fieldName.equals("memberId")) {
							nickname1 = t.getClass().getDeclaredField("nickname");
						} else if (fieldName.equals("presentor")) {
							nickname1 = t.getClass().getDeclaredField("presentorName");
						} else if (fieldName.equals("creator")) {
							nickname1 = t.getClass().getDeclaredField("creatorName");
						}
					} catch (NoSuchFieldException e) {

					}

					if (nickname1 == null) {
						return t;
					}
					List<UserFriend> friendList = friendMapper.selectFriends(uidList, uid);

					if (friendList.size() > 0) {
						if (nickname1 != null) {
							nickname1.setAccessible(true);
							nickname1.set(t, friendList.get(0).getAliasName());
						}

					}

				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.UserService#setFriendNickNameAsk
	 * (java.lang.Object, int)
	 */
	@Override
	public <T> T setFriendNickNameAsk(T t, int uid) {
		// TODO Auto-generated method stub
		if (t == null) {
			return t;
		}
		// 其他用户uid
		List<Integer> uidList = new ArrayList<>();
		String fieldName = null;
		try {
			if (t instanceof List) {
				List list = (List) t;
				if (list.size() > 0) {
					for (Object obj : list) {
						Field field1 = null;
						try {
							field1 = obj.getClass().getDeclaredField("presentor");
						} catch (NoSuchFieldException e) {

						}
						if(field1 == null){
							try {
								field1 = obj.getClass().getDeclaredField("uid");
							} catch (NoSuchFieldException e) {

							}
						}
						
						if(field1 == null){
							try {
								field1 = obj.getClass().getDeclaredField("replyId");
							} catch (NoSuchFieldException e) {

							}
						}
						
						if (field1 == null) {
							return t;
						}

						field1.setAccessible(true);
						Integer fuid = (Integer) field1.get(obj);
						if (fuid != null) {
							uidList.add(fuid);
						}
						fieldName = "presentor";
					}

					if (uidList.size() > 0) {
						List<UserFriend> friendList = friendMapper.selectFriends(uidList, uid);
						int index = 0;
						if (friendList.size() > 0) {
							Map<Integer, UserFriend> friendMap = new HashMap<>();
							for (UserFriend userFriend : friendList) {
								friendMap.put(userFriend.getFriendId(), userFriend);
							}
							for (Object obj : list) {

								Field nickname1 = null;
								try {
									if (fieldName.equals("presentor")) {
										nickname1 = obj.getClass().getDeclaredField("nn");
									}
								} catch (NoSuchFieldException e) {

								}
								if(nickname1 == null){
									try {
										nickname1 = obj.getClass().getDeclaredField("presentorName");
									} catch (NoSuchFieldException e) {

									}
								}
								if(nickname1 == null){
									try {
										nickname1 = obj.getClass().getDeclaredField("nickName");
									} catch (NoSuchFieldException e) {

									}
								}
								if (nickname1 == null) {
									return t;
								}
								UserFriend friend = friendMap.get(uidList.get(index++));
								if (friend != null && nickname1 != null) {
									nickname1.setAccessible(true);
									nickname1.set(obj, friend.getAliasName());
								}
							}
						}
					}

				}
			} else {
				Field field1 = null;
				try {
					field1 = t.getClass().getDeclaredField("presentor");
				} catch (NoSuchFieldException e) {

				}
				if(field1 == null){
					try {
						field1 = t.getClass().getDeclaredField("uid");
					} catch (NoSuchFieldException e) {

					}
				}
				
				if (field1 == null) {
					return t;
				}
				field1.setAccessible(true);
				Integer fuid = (Integer) field1.get(t);
				if (fuid != null) {
					uidList.add(fuid);
				}
				fieldName = "presentor";

				if (uidList.size() > 0) {
					Field nickname1 = null;
					try {
						if (fieldName.equals("presentor")) {
							nickname1 = t.getClass().getDeclaredField("nn");
						}
					} catch (NoSuchFieldException e) {
					}
					if(nickname1 == null){
						try {
							nickname1 = t.getClass().getDeclaredField("presentorName");
						} catch (NoSuchFieldException e) {

						}
					}
					if (nickname1 == null) {
						return t;
					}
					List<UserFriend> friendList = friendMapper.selectFriends(uidList, uid);

					if (friendList.size() > 0) {
						if (nickname1 != null) {
							nickname1.setAccessible(true);
							nickname1.set(t, friendList.get(0).getAliasName());
						}
					}
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinamobile.hnu.xiangyu.user.service.UserService#updateRemark(com.
	 * chinamobile.hnu.xiangyu.user.pojo.UserFriend)
	 */
	@Override
	public void updateRemark(UserFriend userFriend) {
		// TODO Auto-generated method stub
		UserFriend friendKey = friendMapper.selectByPrimaryKey(userFriend);
		if (friendKey != null) {
			friendMapper.updateByPrimaryKeySelective(userFriend);
		} else {
			friendMapper.insertSelective(userFriend);
		}
	}

}
