package com.chinamobile.hnu.xiangyu.web.api.vo;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskCommentReply;

/**
 * 
 * <p>
 * Title: AskComentReplyVo.java
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author lh
 * 
 * @date 2018年8月2日
 * 
 * @version 1.0
 */
public class AskComentReplyVo extends AskCommentReply {

	private String time;

	private String presentorName;

	private String presentorHeader;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPresentorName() {
		return presentorName;
	}

	public void setPresentorName(String presentorName) {
		this.presentorName = presentorName;
	}

	public String getPresentorHeader() {
		return presentorHeader;
	}

	public void setPresentorHeader(String presentorHeader) {
		this.presentorHeader = presentorHeader;
	}

}
