package com.jnshu.resourceservice.config;

import org.springframework.context.annotation.*;
import org.springframework.web.filter.*;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @program: morepineapple
 * @description: 配置Put请求接受不到参数问题
 * @author: Mr.huang
 * @create: 2018-12-28 16:51
 **/
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public HttpPutFormContentFilter httpPutFormContentFilter() {
		return new HttpPutFormContentFilter();
	}

}




