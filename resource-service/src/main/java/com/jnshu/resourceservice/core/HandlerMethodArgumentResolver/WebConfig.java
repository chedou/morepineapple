package com.jnshu.resourceservice.core.HandlerMethodArgumentResolver;

import org.springframework.context.annotation.*;
import org.springframework.http.converter.*;
import org.springframework.web.method.support.*;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.*;
import java.util.*;

/**
 * @program: morepineapple
 * @description: 添加多RequestBody解析器
 * @author: Mr.huang
 * @create: 2018-12-26 15:31
 **/
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new MultiRequestBodyArgumentResolver());
	}

	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(responseBodyConverter());
	}

}
