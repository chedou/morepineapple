package com.jnshu.resourceservice.core.HandlerMethodArgumentResolver;

import com.alibaba.fastjson.*;
import org.apache.commons.io.*;
import org.apache.commons.lang.*;
import org.springframework.core.*;
import org.springframework.web.bind.support.*;
import org.springframework.web.context.request.*;
import org.springframework.web.method.support.*;

import javax.servlet.http.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * @program: morepineapple
 * @description: 多RequestBody解析器
 * @author: Mr.huang
 * @create: 2018-12-26 15:15
 **/
public class MultiRequestBodyArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String JSONBODY_ATTRIBUTE = "JSON_REQUEST_BODY";


	/**
	 * @Description  supportsParameter指明RequestModelArgumentResolver
	 * 				只处理带有@MultiRequestBody注解的参数
	 * @author Mr.HUANG
	 * @date 2018/12/27
	 *
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(MultiRequestBody.class);
	}

	/**
	 * @Description 对入参进行解析
	 * 				首先获取参数值（json串），然后获取参数的完整类型（带泛型），
	 * 				最后使用fastjson解析器将json格式的参数值转化为具体类型的对象。
	 * @param [parameter, mavContainer, webRequest, binderFactory] 
	 * @return java.lang.Object 
	 * @author Mr.HUANG
	 * @date 2018/12/27 
	 * @throws Exception
	 */ 
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
								  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String jsonBody = getRequestBody(webRequest);

		JSONObject jsonObject = JSON.parseObject(jsonBody);
		// 根据@MultiRequestBody注解value作为json解析的key
		MultiRequestBody parameterAnnotation = parameter.getParameterAnnotation(MultiRequestBody.class);
		//注解的value是JSON的key
		String key = parameterAnnotation.value();
		Object value;
		// 如果@MultiRequestBody注解没有设置value，则取参数名FrameworkServlet作为json解析的key
		if (StringUtils.isNotEmpty(key)) {
			value = jsonObject.get(key);
			// 如果设置了value但是解析不到，报错
			if (value == null && parameterAnnotation.required()) {
				throw new IllegalArgumentException(String.format("required param %s is not present", key));
			}
		} else {
			// 注解为设置value则用参数名当做json的key
			key = parameter.getParameterName();
			value = jsonObject.get(key);
		}

		// 获取的注解后的类型 Long
		Class<?> parameterType = parameter.getParameterType();
		// 通过注解的value或者参数名解析，能拿到value进行解析
		if (value != null) {
			//基本类型
			if(parameterType.isPrimitive()){
				return parsePrimitive(parameterType.getName(),value);
			}
			// 基本类型包装类
			if(isBasicDataTypes(parameterType)){
				return parseBasicTypeWrapper(parameterType, value);
				// 字符串类型
			}else if(parameterType == String.class){
				return value.toString();
			}
			// 其他复杂对象
			return JSON.parseObject(value.toString(), parameterType);
		}

		// 解析不到则将整个json串解析为当前参数类型
		if (isBasicDataTypes(parameterType)) {
			if (parameterAnnotation.required()) {
				throw new IllegalArgumentException(String.format("required param %s is not present", key));
			} else {
				return null;
			}
		}

		Object result = parameterType.newInstance();
		// 非基本类型，不允许解析所有字段，返回null
		if (!parameterAnnotation.parseAllFields()) {
			// 如果是必传参数抛异常
			if (parameterAnnotation.required()) {
				throw new IllegalArgumentException(String.format("required param %s is not present", key));
			}
			// 否则返回空对象
			return result;
		}
		// 非基本类型，允许解析，将外层属性解析
		result = JSON.parseObject(jsonObject.toString(), parameterType);
		// 如果非必要参数直接返回，否则如果没有一个属性有值则报错
		if (!parameterAnnotation.required()) {
			return result;
		}else{
			boolean haveValue = false;
			Field[] declaredFields = parameterType.getDeclaredFields();
			for(Field field : declaredFields){
				field.setAccessible(true);
				if(field.get(result) != null){
					haveValue = true;
					break;
				}
			}
			if(!haveValue){
				throw new IllegalArgumentException(String.format("required param %s is not present", key));
			}
			return result;
		}
	}

	// 基本类型解析
	private Object parsePrimitive(String parameterTypeName,Object value){
		final String booleanTypeName = "boolean";
		if(booleanTypeName.equals(parameterTypeName)){
			return Boolean.valueOf(value.toString());
		}
		final String intTypeName = "int";
		if(intTypeName.equals(parameterTypeName)){
			return Integer.valueOf(value.toString());
		}
		final String charTypeName = "char";
		if(charTypeName.equals(parameterTypeName)){
			return value.toString().charAt(0);
		}
		final String shortTypeName = "short";
		if(shortTypeName.equals(parameterTypeName)){
			return Short.valueOf(value.toString());
		}
		final String longTypeName ="long";
		if(longTypeName.equals(parameterTypeName)){
			return Long.valueOf(value.toString());
		}
		final String floatTypeName = "float";
		if(floatTypeName.equals(parameterTypeName)){
			return Float.valueOf(value.toString());
		}
		final String doubleTypeName = "double";
		if(doubleTypeName.equals(parameterTypeName)){
			return Double.valueOf(value.toString());
		}
		final String byteTypeName = "byte";
		if(byteTypeName.equals(parameterTypeName)){
			return Byte.valueOf(value.toString());
		}
		return null;
	}

	// 基本类型包装类型解析
	private Object parseBasicTypeWrapper(Class<?> parameterType,Object value){
		if(Number.class.isAssignableFrom(parameterType)){
			Number number = (Number) value;
			if(parameterType == Integer.class){
				return number.intValue();
			}else if(parameterType == Short.class){
				return number.shortValue();
			}else if(parameterType ==  Long.class){
				return number.longValue();
			}else if(parameterType ==  Float.class){
				return number.floatValue();
			}else if(parameterType ==  Double.class){
				return number.doubleValue();
			}else if(parameterType == Byte.class){
				return number.byteValue();
			}
		}else if(parameterType == Boolean.class){
			return value.toString();
		}else if(parameterType == Character.class){
			return value.toString().charAt(0);
		}
		return null;
	}
	/**
	 * 基本数据类型直接返回
	 */
	private boolean isBasicDataTypes(Class clazz) {
		Set<Class> classSet = new HashSet<>();
		classSet.add(String.class);
		classSet.add(Integer.class);
		classSet.add(Long.class);
		classSet.add(Short.class);
		classSet.add(Float.class);
		classSet.add(Double.class);
		classSet.add(Boolean.class);
		classSet.add(Byte.class);
		classSet.add(Character.class);
		return classSet.contains(clazz);
	}



	/**
	 * 获取请求体JSON字符串
	 */
	private String getRequestBody(NativeWebRequest webRequest) {
		HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

		// 有就直接获取
		String jsonBody = (String) webRequest.getAttribute(JSONBODY_ATTRIBUTE, NativeWebRequest.SCOPE_REQUEST);
		// 没有就从请求中读取
		if (jsonBody == null) {
			try {
				jsonBody = IOUtils.toString(servletRequest.getReader());
				webRequest.setAttribute(JSONBODY_ATTRIBUTE, jsonBody, NativeWebRequest.SCOPE_REQUEST);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return jsonBody;
	}

}
