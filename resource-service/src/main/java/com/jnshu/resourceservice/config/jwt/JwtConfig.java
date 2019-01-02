package com.jnshu.resourceservice.config.jwt;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.security.*;
import org.springframework.context.annotation.*;
import org.springframework.core.io.*;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.*;
import org.springframework.util.*;

import java.io.*;

/**
 * @description: JWT配置类
 * @author: Mr.huang
 * @create: 2018-12-13 20:38
 **/
@Configuration
public class JwtConfig {

	/**
	 * JWT转换器
	 */
	@Autowired
	JwtAccessTokenConverter jwtAccessTokenConverter;

	@Autowired
	private SecurityProperties securityProperties;

	/**
	 * 写入令牌存储
	 *  此处使用 @Autowired 将会报错
	 */
	@Bean
	@Qualifier("tokenStore")
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter);
	}

	/**
	 * @Description JWT转换器，此处需要设置一个 VerifierKey ，也就是公钥（public.cert）
	 * @param
	 * @return
	 * @author Mr.HUANG
	 * @date 2018/12/13
	 * @throws
	 */
	@Bean
	protected JwtAccessTokenConverter jwtTokenEnhancer() {
		JwtAccessTokenConverter converter =  new JwtAccessTokenConverter();
		Resource resource = new ClassPathResource("public.cert");
		String publicKey ;
		// 通过IO流读取公钥文件
		try {
			publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		// 将公钥写入JWT转换器
		converter.setVerifierKey(publicKey);
		return converter;
	}


}
