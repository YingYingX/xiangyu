package com.chinamobile.hnu.xiangyu.web.api.vo;

import com.chinamobile.hnu.xiangyu.common.pojo.PubFeedback;

/**
 * 
 * <p>
 * Title: FeebackVo.java
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author lh
 * 
 * @date 2018年8月31日
 * 
 * @version 1.0
 */
public class FeebackVo extends PubFeedback {

	private String nickName;
	private String feedName;

	public String getFeedName() {
		return feedName;
	}

	public void setFeedName(String feedName) {
		this.feedName = feedName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
