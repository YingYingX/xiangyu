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
 * @version  2018-07-30 16:22:23
 */
@Table(name = "pub_grade_rule")
public class PubGradeRule  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//columns START
	/**
	 * 会员等级，数字
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer level;
	/**
	 * 所需经验值下限，含这个数
	 */
	private Integer lower;
	//columns END 数据库字段结束
	
	

	//get and set
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
	public Integer getLevel() {
		return this.level;
	}
	public void setLower(Integer lower) {
		this.lower = lower;
	}
	
	
	public Integer getLower() {
		return this.lower;
	}
	
	public String toString() {
		return new StringBuffer()
			.append("level=").append(getLevel()).append(",")
			.append("lower=").append(getLower()).append(",")
			.toString();
	}
	
	
}

	
