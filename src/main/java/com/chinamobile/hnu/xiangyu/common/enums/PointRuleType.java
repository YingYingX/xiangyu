/**
 * 
 */
package com.chinamobile.hnu.xiangyu.common.enums;

/**
 * @ClassName:     PointRuleType.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author          dev31
 * @version         V1.0  
 * @Date           2018年7月30日 下午3:20:56 
 */
public enum PointRuleType {
	
	TYPE1(1,"加入/创建团"),
	TYPE2(2,"发布新团言"),
	TYPE3(3,"参与/发起/分享投票"),
	TYPE4(4,"发布公告"),
	TYPE5(5,"参与/发布/分享活动"),
	TYPE6(6,"评论/点赞/收藏"),
	TYPE7(7,"被评论/被点赞/被收藏"),
	TYPE8(8,"专场下提问"),
	TYPE9(9,"个人信息每一项完善"),
	TYPE10(10,"每日登录签到");
	
	private Integer value;
	private String des;
	
	private PointRuleType(Integer value, String des) {
		this.value = value;
		this.des = des;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}

}
