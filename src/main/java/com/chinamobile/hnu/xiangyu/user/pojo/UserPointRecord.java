package com.chinamobile.hnu.xiangyu.user.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version  2018-07-30 16:23:09
 */
@Table(name = "user_point_record")
public class UserPointRecord  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//columns START
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * User ID
	 */
	private Integer uid;
	/**
	 * 类型，1：获取；2：消费
	 */
	private Integer type;
	/**
	 * 获取或消费规则ID，外键关联表pub_point_rule的ID
	 */
	private Integer ruleid;
	/**
	 * 获取或消费规则name，冗余存储
	 */
	private String rulename;
	/**
	 * 获取或消费的积分数量
	 */
	private Integer amount;
	/**
	 * 获取或消费的积分的时间
	 */
	private Date gentime;
	/**
	 * 备注信息
	 */
	private String comment;
	/**
	 * 积分的余额
	 */
	private Integer balance;
	/**
	 * 对应业务的唯一标识
	 */
	private String bizid;
	/**
	 * 辅助定位资源属性
	 */
	private String extid;
	//columns END 数据库字段结束
	
	

	//get and set
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Long getId() {
		return this.id;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
	
	public Integer getUid() {
		return this.uid;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
	public Integer getType() {
		return this.type;
	}
	public void setRuleid(Integer ruleid) {
		this.ruleid = ruleid;
	}
	
	
	public Integer getRuleid() {
		return this.ruleid;
	}
	public void setRulename(String rulename) {
		this.rulename = rulename;
	}
	
	
	public String getRulename() {
		return this.rulename;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	
	public Integer getAmount() {
		return this.amount;
	}

	
	public void setGentime(Date gentime) {
		this.gentime = gentime;
	}
	
	
	public Date getGentime() {
		return this.gentime;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	public String getComment() {
		return this.comment;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	
	
	public Integer getBalance() {
		return this.balance;
	}
	public void setBizid(String bizid) {
		this.bizid = bizid;
	}
	
	
	public String getBizid() {
		return this.bizid;
	}
	public void setExtid(String extid) {
		this.extid = extid;
	}
	
	
	public String getExtid() {
		return this.extid;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id=").append(getId()).append(",")
			.append("uid=").append(getUid()).append(",")
			.append("type=").append(getType()).append(",")
			.append("ruleid=").append(getRuleid()).append(",")
			.append("rulename=").append(getRulename()).append(",")
			.append("amount=").append(getAmount()).append(",")
			.append("gentime=").append(getGentime()).append(",")
			.append("comment=").append(getComment()).append(",")
			.append("balance=").append(getBalance()).append(",")
			.append("bizid=").append(getBizid()).append(",")
			.append("extid=").append(getExtid()).append(",")
			.toString();
	}
	
	
}

	
