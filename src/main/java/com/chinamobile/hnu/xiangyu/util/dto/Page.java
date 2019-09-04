package com.chinamobile.hnu.xiangyu.util.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 默认修改com.github.pagehelper.Page Created by liushun on 2016/3/22.
 */
@SuppressWarnings("all")
public class Page<T> implements Serializable {

	// 当前第几页
	private int pageCurrent;
	// 每页大小
	private int pageSize;
	// 数据对象
	private List<T> data;
	// 总记录数
	private long totalCount;

	// 总页数
	private int totalPage;

	// 多少行
	private List<T> totalRows;

	public Page(int pageCurrent, int pageSize) {
		this.pageCurrent = pageCurrent;
		this.pageSize = pageSize;
	}

	public Page(List<T> list) {
		com.github.pagehelper.Page page = (com.github.pagehelper.Page) list;
		this.pageCurrent = page.getPageNum();
		this.pageSize = page.getPageSize();
		this.data = page.getResult();
		this.totalCount = page.getTotal();
		this.totalPage = page.getPages();

	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageCurrent() {
		return pageCurrent;
	}

	public void setPageCurrent(int pageCurrent) {
		this.pageCurrent = pageCurrent;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(List<T> totalRows) {
		this.totalRows = totalRows;
	}
}
