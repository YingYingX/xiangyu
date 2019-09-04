/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.vo;

import java.util.Date;
import java.util.List;

import com.chinamobile.hnu.xiangyu.club.pojo.ClubLabel;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubMember;
import com.chinamobile.hnu.xiangyu.club.pojo.ClubNotice;
import com.chinamobile.hnu.xiangyu.util.Utils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author The Old Man and the Sea
 *
 * 2018年5月29日
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubVo {
	
	
    private Integer id;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date gmtCreate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
    
    private Byte isFavorite;

    private Integer collectionNumber;
    
    private List<ClubLabel> labels;
    
    private List<ClubMember> users;
    
    private ClubNotice notice;
    
    private ClubSpeakVo speak;
    
    private ClubActivityVo activity;
    
    private Byte isJoin;
    
    private String time;
    
    private boolean isAdmin;
    
    private String college;
    
    
    
	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the isJoin
	 */
	public Byte getIsJoin() {
		return isJoin;
	}

	/**
	 * @param isJoin the isJoin to set
	 */
	public void setIsJoin(Byte isJoin) {
		this.isJoin = isJoin;
	}

	/**
	 * @return the isFavorite
	 */
	public Byte getIsFavorite() {
		return isFavorite;
	}

	/**
	 * @param isFavorite the isFavorite to set
	 */
	public void setIsFavorite(Byte isFavorite) {
		this.isFavorite = isFavorite;
	}

	/**
	 * @return the creatorName
	 */
	public String getCreatorName() {
		return creatorName;
	}

	/**
	 * @param creatorName the creatorName to set
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the gmtCreate
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/**
	 * @param gmtCreate the gmtCreate to set
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
		this.time = Utils.formatAgo(gmtCreate);
	}

	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}

	/**
	 * @param gmtModified the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the logoImage
	 */
	public String getLogoImage() {
		return logoImage;
	}

	/**
	 * @param logoImage the logoImage to set
	 */
	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}

	/**
	 * @return the creator
	 */
	public Integer getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	/**
	 * @return the memberNumber
	 */
	public Integer getMemberNumber() {
		return memberNumber;
	}

	/**
	 * @param memberNumber the memberNumber to set
	 */
	public void setMemberNumber(Integer memberNumber) {
		this.memberNumber = memberNumber;
	}

	/**
	 * @return the coverImage
	 */
	public String getCoverImage() {
		return coverImage;
	}

	/**
	 * @param coverImage the coverImage to set
	 */
	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	/**
	 * @return the introduction
	 */
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * @param introduction the introduction to set
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * @return the qrcodeImage
	 */
	public String getQrcodeImage() {
		return qrcodeImage;
	}

	/**
	 * @param qrcodeImage the qrcodeImage to set
	 */
	public void setQrcodeImage(String qrcodeImage) {
		this.qrcodeImage = qrcodeImage;
	}

	/**
	 * @return the yesterdayTraffic
	 */
	public Integer getYesterdayTraffic() {
		return yesterdayTraffic;
	}

	/**
	 * @param yesterdayTraffic the yesterdayTraffic to set
	 */
	public void setYesterdayTraffic(Integer yesterdayTraffic) {
		this.yesterdayTraffic = yesterdayTraffic;
	}

	/**
	 * @return the settingVisible
	 */
	public Byte getSettingVisible() {
		return settingVisible;
	}

	/**
	 * @param settingVisible the settingVisible to set
	 */
	public void setSettingVisible(Byte settingVisible) {
		this.settingVisible = settingVisible;
	}

	/**
	 * @return the settingSpeak
	 */
	public Byte getSettingSpeak() {
		return settingSpeak;
	}

	/**
	 * @param settingSpeak the settingSpeak to set
	 */
	public void setSettingSpeak(Byte settingSpeak) {
		this.settingSpeak = settingSpeak;
	}

	/**
	 * @return the settingActivity
	 */
	public Byte getSettingActivity() {
		return settingActivity;
	}

	/**
	 * @param settingActivity the settingActivity to set
	 */
	public void setSettingActivity(Byte settingActivity) {
		this.settingActivity = settingActivity;
	}

	/**
	 * @return the settingNotice
	 */
	public Byte getSettingNotice() {
		return settingNotice;
	}

	/**
	 * @param settingNotice the settingNotice to set
	 */
	public void setSettingNotice(Byte settingNotice) {
		this.settingNotice = settingNotice;
	}

	/**
	 * @return the settingVote
	 */
	public Byte getSettingVote() {
		return settingVote;
	}

	/**
	 * @param settingVote the settingVote to set
	 */
	public void setSettingVote(Byte settingVote) {
		this.settingVote = settingVote;
	}

	/**
	 * @return the status
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * @return the collectionNumber
	 */
	public Integer getCollectionNumber() {
		return collectionNumber;
	}

	/**
	 * @param collectionNumber the collectionNumber to set
	 */
	public void setCollectionNumber(Integer collectionNumber) {
		this.collectionNumber = collectionNumber;
	}

	/**
	 * @return the labels
	 */
	public List<ClubLabel> getLabels() {
		return labels;
	}

	/**
	 * @param labels the labels to set
	 */
	public void setLabels(List<ClubLabel> labels) {
		this.labels = labels;
	}

	/**
	 * @return the users
	 */
	public List<ClubMember> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<ClubMember> users) {
		this.users = users;
	}

	
	/**
	 * @return the notice
	 */
	public ClubNotice getNotice() {
		return notice;
	}

	/**
	 * @param notice the notice to set
	 */
	public void setNotice(ClubNotice notice) {
		this.notice = notice;
	}

	
	/**
	 * @return the speak
	 */
	public ClubSpeakVo getSpeak() {
		return speak;
	}

	/**
	 * @param speak the speak to set
	 */
	public void setSpeak(ClubSpeakVo speak) {
		this.speak = speak;
	}

	/**
	 * @return the activity
	 */
	public ClubActivityVo getActivity() {
		return activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setActivity(ClubActivityVo activity) {
		this.activity = activity;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClubVo [id=" + id + ", name=" + name + "]";
	}
    
    
}
