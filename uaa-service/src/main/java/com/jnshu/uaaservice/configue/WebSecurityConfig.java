package com.jnshu.uaaservice.configue;

import com.jnshu.uaaservice.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.web.authentication.www.*;

import javax.servlet.http.*;

/**
 * @description: 授权服务器 Spring Secuity配置文件
 * @author: Mr.huang
 * @create: 2018-11-17 16:40
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * @description 授权管理器
	 * @return org.springframework.security.authentication.AuthenticationManager
	 * @author Mr.HUANG
	 * @date 2018/12/11
	 * 备注：需注入此Bean，否则将提示 password为不支持的授权类型
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManager() throws Exception{
		return super.authenticationManager();
	}

	/**
	 * @description  信息源
	 * @param
	 * @return void
	 * @author Mr.HUANG
	 * @date 2018/11/17
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
				// 对CSRF攻击防御的开启设置，这里默认关闭
				.csrf().disable()
					.exceptionHandling()
					.authenticationEntryPoint((request, response, authException) ->
							response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
				.and()
					// 对路径拦截器进行配置
					.authorizeRequests()
					.antMatchers("/**").authenticated()
				.and()
					// 开启 HTTP BASIC 认证，即是否开启 BasicAuthenticationFilter 过滤器
					.httpBasic();

	}


	/**
	 * @description 对用户信息进行判断的服务
	 * @author Mr.HUANG
	 * @date 2018/12/11
	 */
	@Autowired
	UserServiceDetail userServiceDetail;


	/**
	 * @description 配置验证的用户信息源以及密码加密策略
	 * @param auth 权限管理运行管理
	 * @return
	 * @author Mr.HUANG
	 * @date 2018/11/17
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userServiceDetail)
				.passwordEncoder(new BCryptPasswordEncoder());
	}


	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/userlogin","/userlogout","/userjwt","/v2/api-docs", "/swagger-resources/configuration/ui",
				"/swagger-resources","/swagger-resources/configuration/security",
				"/swagger-ui.html","/css/**", "/js/**","/images/**", "/webjars/**", "**/favicon.ico", "/index");

	}

}
