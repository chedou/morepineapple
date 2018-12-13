package com.jnshu.resourceservice.client;

import com.jnshu.resourceservice.client.hystrix.*;
import com.jnshu.resourceservice.entity.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.web.bind.annotation.*;


/**
 * @description feign 远程调用服务
 * @author Mr.HUANG
 * @date 2018/10/13
 */
@FeignClient(value = "uaa-service",fallback =AuthServiceHystrix.class )
public interface AuthServiceClient {

	/**
	 * @Description feign远程调用权限认证权限
	 * @param authorization 	所拥有的权限信息
	 * 			type 			权限认证类型
	 * 			username 		用户名
	 * 			password 		用户密码
	 * @return  JWT 			封装的权限信息
	 * @author Mr.HUANG
	 * @date 2018/12/13
	 * @throws
	 */
    @PostMapping(value = "/oauth/token")
	JWT getToken(@RequestHeader(value = "Authorization") String authorization,
				 @RequestParam("grant_type") String type,
				 @RequestParam("username") String username,
				 @RequestParam("password") String password);

}



