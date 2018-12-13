package com.jnshu.resourceservice.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.*;

/**
 * @description 用于开启方法级别的安全权限认证，prePostEnabled 指在方法执行之前进行认证
 * @author Mr.HUANG
 * @date 2018/12/6
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GlobalMethodSecurityConfig {

}
