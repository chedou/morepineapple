package com.jnshu.resourceservice.client.hystrix;

import com.jnshu.resourceservice.client.*;
import com.jnshu.resourceservice.entity.*;
import org.springframework.stereotype.*;

/**
 * @Description Hystrix熔断器
 * @author Mr.HUANG
 * @date 2018/12/13
 */
@Component
public class AuthServiceHystrix implements AuthServiceClient {

    /**
     * @Description 获取token
     * @param authorization 	所拥有的权限信息
     * 			type 			权限认证类型
     * 			username 		用户名
     * 			password 		用户密码
     * @return  JWT 			封装的权限信息
     * @author Mr.HUANG
     * @date 2018/12/13
     * @throws
     */
    @Override
    public JWT getToken(String authorization, String type, String username, String password) {
        return null;
    }
}
