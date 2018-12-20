package com.jnshu.resourceservice.utils;

import org.codehaus.jackson.map.annotate.*;

import java.io.*;

/**
 * @program: morepineapple
 * @description: 数据分页工具诶
 * @author: Mr.huang
 * @create: 2018-12-20 21:55
 **/
@JsonSerialize
public class PageUtil implements Serializable {

	private static final long serialVersionUID = -1428285660796168326L;

	private Integer page = 1;

	private Integer size = 10;

	private Integer total;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "PageUtil{" +
				"page=" + page +
				", size=" + size +
				", total=" + total +
				'}';
	}
}
