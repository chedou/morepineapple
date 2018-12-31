package com.jnshu.resourceservice.utils;

import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;

/**
 * @program: morepineapple
 * @description: 获取权限相关工具类
 * @author: Mr.huang
 * @create: 2018-12-31 11:20
 **/
public class AuthorizationUtils {
	public static Authentication getAuthentication(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			return authentication;
		}
		throw  new AuthenticationServiceException("authentication not found");
	}

	public static String getUserId(){
		return getAuthentication().getName().split(",")[0];
	}
	public static String getUserName(){
		return getAuthentication().getName().split(",")[1];
	}

	public static Object getDetails(){
		System.out.println(getAuthentication().getPrincipal());
		return getAuthentication().getDetails();
	}

}
