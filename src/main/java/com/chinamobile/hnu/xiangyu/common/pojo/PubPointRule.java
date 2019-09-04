package com.chinamobile.hnu.xiangyu.common.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * 
 * @author lh <自动生成>
 * @联系QQ：2465613689
 * @version  2018-07-30 16:22:57
 */
@Table(name = "pub_point_rule")
public class PubPointRule  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//columns START
	/**
	 * 规则ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 类别名称
	 */
	private String name;
	/**
	 * 获取或消费积分的数量
	 */
	private Integer amount;
	/**
	 * 规则类型，1：获取的规则；2：消费的规则
	 */
	private Integer type;
	/**
	 * 积分频率，1：每天；2：每次; 3:单次
	 */
	private Integer frequency;
	/**
	 * 每天的上限，0：不限制
	 */
	private Integer upperAmount;
	//columns END 数据库字段结束
	
	

	//get and set
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public Integer getId() {
		return this.id;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getName() {
		return this.name;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	
	public Integer getAmount() {
		return this.amount;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
	public Integer getType() {
		return this.type;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
	
	public Integer getFrequency() {
		return this.frequency;
	}
	public void setUpperAmount(Integer upperAmount) {
		this.upperAmount = upperAmount;
	}
	
	
	public Integer getUpperAmount() {
		return this.upperAmount;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("id=").append(getId()).append(",")
			.append("name=").append(getName()).append(",")
			.append("amount=").append(getAmount()).append(",")
			.append("type=").append(getType()).append(",")
			.append("frequency=").append(getFrequency()).append(",")
			.append("upperAmount=").append(getUpperAmount()).append(",")
			.toString();
	}
	
	
}

	
