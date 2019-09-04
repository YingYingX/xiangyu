/**  

 * <p>Title: AclubInfoVo.java</p>  

 * <p>Description: </p>  

 * <p>Copyright: Copyright douziit(c) 2017</p>  

 * @author lh  

 * @date 2018年6月20日  

 * @version 1.0  

 */
package com.chinamobile.hnu.xiangyu.web.api.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * <p>
 * Title: AclubInfoVo.java
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author lh
 * 
 * @date 2018年6月20日
 * 
 * @version 1.0
 */
public class AclubInfoVo {
	private Integer id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date gmtCreate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date gmtModified;

	private String name;

	private String logoImage;

	private Integer creator;
	private String creatorName;

	private Integer memberNumber;

	private String coverImage;

	private String introduction;

	private String qrcodeImage;

	private Integer yesterdayTraffic;

	private Byte settingVisible;

	private Byte settingSpeak;

	private Byte settingActivity;

	private Byte settingNotice;

	private Byte settingVote;

	private Byte status;

	private Integer collectionNumber;

	private Integer speakCount;
	private Integer activityCount;
	private Integer noticeCount;
	private Integer voteCount;
	private Integer bulidingCount;

	private String nickname;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Integer getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(Integer memberNumber) {
		this.memberNumber = memberNumber;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getQrcodeImage() {
		return qrcodeImage;
	}

	public void setQrcodeImage(String qrcodeImage) {
		this.qrcodeImage = qrcodeImage;
	}

	public Integer getYesterdayTraffic() {
		return yesterdayTraffic;
	}

	public void setYesterdayTraffic(Integer yesterdayTraffic) {
		this.yesterdayTraffic = yesterdayTraffic;
	}

	public Byte getSettingVisible() {
		return settingVisible;
	}

	public void setSettingVisible(Byte settingVisible) {
		this.settingVisible = settingVisible;
	}

	public Byte getSettingSpeak() {
		return settingSpeak;
	}

	public void setSettingSpeak(Byte settingSpeak) {
		this.settingSpeak = settingSpeak;
	}

	public Byte getSettingActivity() {
		return settingActivity;
	}

	public void setSettingActivity(Byte settingActivity) {
		this.settingActivity = settingActivity;
	}

	public Byte getSettingNotice() {
		return settingNotice;
	}

	public void setSettingNotice(Byte settingNotice) {
		this.settingNotice = settingNotice;
	}

	public Byte getSettingVote() {
		return settingVote;
	}

	public void setSettingVote(Byte settingVote) {
		this.settingVote = settingVote;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Integer getCollectionNumber() {
		return collectionNumber;
	}

	public void setCollectionNumber(Integer collectionNumber) {
		this.collectionNumber = collectionNumber;
	}

	public Integer getSpeakCount() {
		return speakCount;
	}

	public void setSpeakCount(Integer speakCount) {
		this.speakCount = speakCount;
	}

	public Integer getActivityCount() {
		return activityCount;
	}

	public void setActivityCount(Integer activityCount) {
		this.activityCount = activityCount;
	}

	public Integer getNoticeCount() {
		return noticeCount;
	}

	public void setNoticeCount(Integer noticeCount) {
		this.noticeCount = noticeCount;
	}

	public Integer getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}

	public Integer getBulidingCount() {
		return bulidingCount;
	}

	public void setBulidingCount(Integer bulidingCount) {
		this.bulidingCount = bulidingCount;
	}

}
