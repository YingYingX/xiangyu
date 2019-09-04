package com.chinamobile.hnu.xiangyu.web.api.vo;

import java.util.List;

import com.chinamobile.hnu.xiangyu.ask.pojo.AskComment;

/**
 * 
 * <p>
 * Title: AskComentVo.java
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
public class AskComentVo extends AskComment {

	private String time;

	private String presentorName;

	private String presentorHeader;

	// 无用字段
	private String nsw;

	List<AskComentReplyVo> askComentReplyVos;

	public String getNsw() {
		return nsw;
	}

	public void setNsw(String nsw) {
		this.nsw = nsw;
	}

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

	public List<AskComentReplyVo> getAskComentReplyVos() {
		return askComentReplyVos;
	}

	public void setAskComentReplyVos(List<AskComentReplyVo> askComentReplyVos) {
		this.askComentReplyVos = askComentReplyVos;
	}

}
