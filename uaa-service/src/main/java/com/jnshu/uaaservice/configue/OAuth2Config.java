package com.jnshu.uaaservice.configue;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.io.*;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.oauth2.config.annotation.configurers.*;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.config.annotation.web.configurers.*;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.*;

/**
 * @program: purview
 * @description: Authorization server 配置文件
 * @author: Mr.huang
 * @create: 2018-11-17 22:52
 **/

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

	/**
	 * @description OAuth2Config 客户端服务相关配置
	 * @param: 客户端详细信息配置类
	 * @return null
	 * @author Mr.HUANG
	 * @date 2018/12/11
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// 配置内存中客户端服务
		clients.inMemory()
				// 客户端id
				.withClient("user-service")
				// 客户端安全码
				.secret("123456")
				// 允许请求范围
				.scopes("service")
				// 自动授权
				.autoApprove(true)
				// 客户端可以使用的额授权类型
				.authorizedGrantTypes("implicit","refresh_token", "password", "authorization_code")
				// 访问令牌有效时长
				.accessTokenValiditySeconds(12*300)
				// 刷新令牌有效时长
				.refreshTokenValiditySeconds(12*300);
	}

	/**
	 * @description 授权服务器安全认证的相关配置，用于配置Spring Security，生成对应的安全过滤器调用链，
	 * 主要控制oauth/**端点相关访问
	 * 配置授权服务器端点
	 * @param: endpoints
	 * @return void
	 * @author Mr.HUANG
	 * @date 2018/12/11
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtTokenEnhancer()).authenticationManager(authenticationManager);
	}

	@Autowired
	// @Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtTokenEnhancer());
	}

	/**
	 * @description JWT转换器
	 * @param
	 * @return org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
	 * @author Mr.HUANG
	 * @date 2018/12/11
	 */
	@Bean
	protected JwtAccessTokenConverter jwtTokenEnhancer() {

		/**
		 * @description
		 * @param [ClassPathResource，hwb123] 私钥文件路径，解密密码
		 * @return org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
		 * @author Mr.HUANG
		 * @date 2018/12/11
		 */
		KeyStoreKeyFactory keyStoreKeyFactory =
				new KeyStoreKeyFactory(new ClassPathResource("hwb-jwt.jks"), "hwb123".toCharArray());

		// JwtAccessTokenConverter用于token转换
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

		converter.setKeyPair(keyStoreKeyFactory.getKeyPair("hwb-jwt"));
		return converter;
	}

	/***
	 * @description 密码转换器
	 * @return org.springframework.security.crypto.password.NoOpPasswordEncoder
	 * @author Mr.HUANG
	 * @date 2018/12/11
	 */
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}


}
