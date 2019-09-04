/**
 * 
 */
package com.chinamobile.hnu.xiangyu.util.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author The Old Man and the Sea
 *
 * 2018年3月23日
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageVo {
	private Object list;
	private Long count;
	private Integer totalPage;
	
	/**
	 * @param list
	 * @param count
	 */
	public PageVo(Object list, Long count,Integer totalPage) {
		super();
		this.list = list;
		this.count = count;
		this.totalPage = totalPage;
	}
	
	
	/**
	 * @return the list
	 */
	public Object getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(Object list) {
		this.list = list;
	}
	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(long count) {
		this.count = count;
	}
	
	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}


	/**
	 * @param totalPage the totalPage to set
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PageVo [list=" + list + ", count=" + count + "]";
	}
}
