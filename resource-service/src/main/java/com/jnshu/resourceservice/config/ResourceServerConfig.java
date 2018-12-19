package com.jnshu.resourceservice.config;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.http.*;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.config.annotation.web.configurers.*;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.*;


/**
 * @description 权限认证资源配置
 * @author Mr.HUANG
 * @date 2018/12/6
 */
@Configuration
@EnableResourceServer
@ComponentScan(basePackages = "com.jnshu.resourceservice.config")
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    Logger log = LoggerFactory.getLogger(ResourceServerConfig.class);

    /**
     * @Description 设置URL拦截策略，验证策略
     * @param HttpSecurity
     * @return null
     * @author Mr.HUANG
     * @date 2018/12/13
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // 是否开启防止 csrf攻击策略，默认关闭
                .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 请求认证策略
                .and().authorizeRequests()
                // 允许无需安全验证的URL
                .antMatchers("/user/login","/user/register","/swagger-ui.html").permitAll()
                // swagger设置
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/configuration/security").permitAll()
                // 结束swagger设置
                // .antMatchers("/**").authenticated();
                .anyRequest().authenticated();
        http.headers().cacheControl();
        http.csrf().disable();
    }


    /**
     * @Description 资源服务器相关配置，这里只设置了资源服务器的ID为"cheDou",并写入tokenStore,此处还有很多设置
     * @param
     * @return
     * @author Mr.HUANG
     * @date 2018/12/13
     * @throws
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                // 资源服务器标示
                .resourceId("resource-service-user")
                // 配置 tokenStore
                .tokenStore(tokenStore);
    }


    @Autowired
	TokenStore tokenStore;

    @Autowired
	JwtAccessTokenConverter tokenConverter;


}
