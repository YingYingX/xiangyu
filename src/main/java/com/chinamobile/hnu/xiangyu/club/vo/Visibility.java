/**
 * 
 */
package com.chinamobile.hnu.xiangyu.club.vo;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author The Old Man and the Sea
 *
 * 2018年5月29日
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Visibility {

	@NotNull(message="团队id不能为空")
	private Integer id;
	
//	@NotNull(message="请选择团队是否可见")
	private Byte settingVisible;

//	@NotNull(message="请选择团言是否可见")
    private Byte settingSpeak;

//	@NotNull(message="请选择活动是否可见")
    private Byte settingActivity;

//	@NotNull(message="请选择公告是否可见")
    private Byte settingNotice;

//	@NotNull(message="请选择投票是否可见")
    private Byte settingVote;
    
    

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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Visibility [settingVisible=" + settingVisible + ", settingSpeak=" + settingSpeak + ", settingActivity="
				+ settingActivity + ", settingNotice=" + settingNotice + ", settingVote=" + settingVote + "]";
	}
    
    
    
}
