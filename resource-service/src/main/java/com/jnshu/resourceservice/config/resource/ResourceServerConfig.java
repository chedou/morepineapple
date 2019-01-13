package com.jnshu.resourceservice.config.resource;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.http.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.config.annotation.web.configurers.*;
import org.springframework.security.oauth2.provider.authentication.*;
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
        // http
        //         // 是否开启防止 csrf攻击策略，默认关闭
        //         // .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //         // 请求认证策略
        //         .authorizeRequests()
        //         // 允许无需安全验证的URL
        //         .antMatchers("/user/login","/user/register","/swagger-ui.html","/a/login","/a/register").permitAll()
        //         // swagger设置
        //         .antMatchers("/swagger-resources/**").permitAll()
        //         .antMatchers("/images/**").permitAll()
        //         .antMatchers("/webjars/**").permitAll()
        //         .antMatchers("/v2/api-docs").permitAll()
        //         .antMatchers("/configuration/ui").permitAll()
        //         .antMatchers("/configuration/security").permitAll()
			// 	.antMatchers("/oauth/token").permitAll()
        //         // 结束swagger设置
        //         .antMatchers("/**").authenticated()
			// 	.and().csrf().disable();
			// 	// .and().formLogin().loginProcessingUrl("/a/login").passwordParameter("password")
			// 	// .usernameParameter("username").permitAll();
        //         // .anyRequest().authenticated();
        // // http.headers().cacheControl();
        // http.requestCache().requestCache(new NullRequestCache());

		http
			// 由于使用的是JWT，我们这里不需要csrf
			.csrf().disable()
			// 基于token，所以不需要session
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
				//.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

				// 允许对于网站静态资源的无授权访问
				.antMatchers(
						HttpMethod.GET,
						"/",
						"/*.html",
						"/favicon.ico",
						"/**/*.html",
						"/**/*.css",
						"/**/*.js"
				).permitAll()
				// 对于获取token的rest api要允许匿名访问
				.antMatchers("/auth/**").permitAll()
				.antMatchers("/swagger-ui.html","/a/login","/a/register","/login").permitAll()
				// 除上面外的所有请求全部需要鉴权认证
				.anyRequest().authenticated();

		// 禁用缓存
		http.headers().cacheControl();

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
                .resourceId("resource-service")
				.tokenExtractor(new BearerTokenExtractor())
                // 配置 tokenStore
                .tokenStore(tokenStore);
    }


    @Autowired
	TokenStore tokenStore;

    @Autowired
	JwtAccessTokenConverter tokenConverter;

	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}


}
