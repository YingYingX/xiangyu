/**
 * 
 */
package com.chinamobile.hnu.xiangyu.common.enums;

/**
 * @ClassName:     ExtidType.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author          dev31
 * @version         V1.0  
 * @Date           2018年7月31日 下午5:54:53 
 */
public enum ExtidType {

	TYPE1("1","团队"),
	TYPE2("2","团队"),
	TYPE3("3","团队"),
	TYPE_1("_1","问问"),
	TYPE_2("_2","问问"),
	TYPE_3("_3","问问"),
	TYPE1_("1_","专场"),
	TYPE2_("2_","专场"),
	TYPE3_("3_","专场");
	
	private String value;
	private String des;
	
	private ExtidType(String value, String des) {
		this.value = value;
		this.des = des;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

}
