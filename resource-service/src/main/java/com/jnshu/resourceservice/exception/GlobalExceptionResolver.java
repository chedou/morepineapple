package com.jnshu.resourceservice.exception;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.serializer.*;
import com.jnshu.resourceservice.core.ret.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;

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
	 * @Description 参数校验异常的处理
	 * @param [response, ex]
	 * @return void
	 * @author Mr.HUANG
	 * @date 2018/12/19
	 * @throws MethodArgumentNotValidException
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public void handleBindException(HttpServletResponse response, MethodArgumentNotValidException ex) {
		RetResult<Object> result = new RetResult<>();
		FieldError fieldError = ex.getBindingResult().getFieldError();
		logger.info("参数校验异常:{}({})", fieldError.getDefaultMessage(),fieldError.getField());
		result.setCode(RetCode.FAIL).setMsg(fieldError.getDefaultMessage()).setData(null);
		responseResult(response, result);
	}


	/**
	 * @Description 校验 除了 requestbody 注解方式的参数校验 对应的 bindingresult 为 BeanPropertyBindingResult
	 * @param [response, ex]
	 * @return void
	 * @author Mr.HUANG
	 * @date 2018/12/19
	 * @throws BindException
	 */
	@ExceptionHandler(value = BindException.class)
	public void handleBindException(HttpServletResponse response, BindException ex) {
		RetResult<Object> result = new RetResult<>();
		FieldError fieldError = ex.getBindingResult().getFieldError();
		logger.info("必填校验异常:{}({})", fieldError.getDefaultMessage(),fieldError.getField());
		result.setCode(RetCode.FAIL).setMsg(fieldError.getDefaultMessage()).setData(null);
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

