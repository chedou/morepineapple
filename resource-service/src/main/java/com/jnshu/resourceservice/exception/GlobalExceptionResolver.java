package com.jnshu.resourceservice.exception;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.serializer.*;
import com.jnshu.resourceservice.core.ret.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.io.*;

/**
 * @program: morepineapple
 * @description: 全局异常处理配置
 * @author: Mr.huang
 * @create: 2018-12-15 16:41
 **/
@ControllerAdvice
@ResponseBody
public class GlobalExceptionResolver {

	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);


	/**
	 * @Description 用户登录验证异常统一处理
	 * @param [e]
	 * @return org.springframework.http.ResponseEntity<java.lang.String>
	 * @author Mr.HUANG
	 * @date 2018/12/16
	 * @throws
	 */
	@ExceptionHandler(value = UserLoginException.class)
	public void handleException(HttpServletResponse response,Exception e) {
		RetResult<Object> result = new RetResult<>();
		result.setCode(RetCode.FAIL).setMsg(e.getMessage()).setData(null);
		responseResult(response, result);
	}

	/**
	 * 业务异常的处理
	 */
	@ExceptionHandler(value = ServiceException.class)
	public void serviceExceptionHandler(HttpServletResponse response, ServiceException e) {
		RetResult<Object> result = new RetResult<>();
		result.setCode(RetCode.FAIL).setMsg(e.getMessage()).setData(null);
		responseResult(response, result);
	}

	/**
	 * 其他异常统一处理
	 */
	@ExceptionHandler(value = Exception.class)
	public void exceptionHandler(HttpServletResponse response, Exception e) {
		RetResult<Object> result = new RetResult<>();
		result.setCode(RetCode.INTERNAL_SERVER_ERROR).setMsg("服务器打酱油了，请稍后再试~");
		logger.error(e.getMessage(), e);
		responseResult(response, result);
	}
	/**
	 * @param response
	 * @param result
	 * @Title: responseResult
	 * @Description: 响应结果
	 * @Reutrn void
	 */
	private void responseResult(HttpServletResponse response, RetResult<Object> result) {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "application/json;charset=UTF-8");
		response.setStatus(200);
		try {
			response.getWriter().write(JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
	}


}

