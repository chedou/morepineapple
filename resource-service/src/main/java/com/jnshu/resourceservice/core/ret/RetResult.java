package com.jnshu.resourceservice.core.ret;


import java.util.*;

/**
 * @program: morepineapple
 * @description: 返回对象实体（统一封装的载体）
 * @author: Mr.huang
 * @create: 2018-10-14 21:36
 **/
public class RetResult<T> {

	private int code;

	private String msg;

	private T data;

	/**
	 * @Description 根据 code msg data 来构造 Retcode
	 * @param code msg data
	 *
	 */
	public RetResult(Integer code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data= data;
	}

	/**
	 * @Description 生成一块空的内存
	 *
	 */
	public RetResult() {}


	public RetResult<T> setCode(RetCode retCode) {
		this.code = retCode.code;
		return this;
	}

	RetResult<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public RetResult<T> setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public T getData() {
		return data;
	}

	public RetResult<T> setData(T data) {
		this.data = data;
		return this;
	}

	public RetResult<T> setData(T... datas) {
		List<T> dataAll = new ArrayList<>();
		Collections.addAll(dataAll, datas);
		this.data = (T) dataAll;
		return this;
	}


	@Override
	public String toString() {
		return "RetResult{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", data=" + data +
				'}';
	}
}
