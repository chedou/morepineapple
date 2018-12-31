package com.jnshu.resourceservice.core.ret;

import java.io.*;

/**
 * @program: morepineapple
 * @description: 将结果转换为封装后的对象，最后返回的对象
 * @author: Mr.huang
 * @create: 2018-10-14 21:38
 **/
public class RetResponse implements Serializable   {

	private static final long serialVersionUID = -1428285660796168326L;


	private final static String SUCCESS = "success";

	public static <T> RetResult<T> makeOKRsp() {
		return new RetResult<T>().setCode(RetCode.SUCCESS).setMsg(SUCCESS);
	}

	public static <T> RetResult<T> makeOKRsp(T data) {
		return new RetResult<T>().setCode(RetCode.INTERFACE_EXCEED_LOAD).setMsg(RetCode.INTERFACE_EXCEED_LOAD.message()).setData(data);
	}

	public static <T> RetResult<T> result(RetCode result ,T data){
		RetResult<T> retResult =new RetResult<>();
		retResult.setCode(result.code).setMsg(result.message()).setData(data);
		return retResult;
	}

	public static <T> RetResult<T> result(RetCode result ) {
		RetResult<T> retResult = new RetResult<>();
		retResult.setCode(result.code).setMsg(result.message());
		return retResult;
	}
}
