package com.jnshu.resourceservice.utils.pageutil;

import org.codehaus.jackson.map.annotate.*;
import org.hibernate.validator.constraints.*;

import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
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

	@Range(min = 1,message = "请输入正确的页码", groups = PageUtilGroup.class)
	private Integer page = 1;

	@NotNull(message = "请求参数不能为空", groups = PageUtilGroup.class)
	private Integer size = 10;

	private Long total;

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

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
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
