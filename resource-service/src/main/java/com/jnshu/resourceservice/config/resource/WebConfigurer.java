package com.jnshu.resourceservice.config.resource;

import com.alibaba.fastjson.serializer.*;
import com.alibaba.fastjson.support.config.*;
import com.alibaba.fastjson.support.spring.*;
import org.slf4j.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.http.converter.*;
import org.springframework.web.multipart.commons.*;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.*;
import java.util.*;

/**
 * @program: morepineapple
 * @description: 自定义消息转换器、异常统一处理设置
 * @author: Mr.huang
 * @create: 2018-12-15 16:35
 **/
@Configuration
public class WebConfigurer extends WebMvcConfigurationSupport {

	private final static Logger LOGGER = LoggerFactory.getLogger(WebConfigurer.class);

	/**
	 * 修改自定义消息转换器
	 * @param converters
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
		converter.setSupportedMediaTypes(getSupportedMediaTypes());
		FastJsonConfig config = new FastJsonConfig();
		config.setSerializerFeatures(
				// String null -> "" List字段如果为null,输出为[],而非null
				SerializerFeature.WriteNullStringAsEmpty,
				// Number null -> 0 字符类型字段如果为null,输出为"",而非null
				SerializerFeature.WriteNullNumberAsZero,
				// 是否输出值为null的字段,默认为false
				SerializerFeature.WriteMapNullValue,
				//禁止循环引用 消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
				SerializerFeature.DisableCircularReferenceDetect
		);
		converter.setFastJsonConfig(config);
		converter.setDefaultCharset(Charset.forName("UTF-8"));
		converters.add(converter);
	}

	private List<MediaType> getSupportedMediaTypes() {
		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
		supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
		supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
		supportedMediaTypes.add(MediaType.APPLICATION_PDF);
		supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
		supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
		supportedMediaTypes.add(MediaType.APPLICATION_XML);
		supportedMediaTypes.add(MediaType.IMAGE_GIF);
		supportedMediaTypes.add(MediaType.IMAGE_JPEG);
		supportedMediaTypes.add(MediaType.IMAGE_PNG);
		supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
		supportedMediaTypes.add(MediaType.TEXT_HTML);
		supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
		supportedMediaTypes.add(MediaType.TEXT_PLAIN);
		supportedMediaTypes.add(MediaType.TEXT_XML);
		return supportedMediaTypes;
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getCommonsMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(20971520);
		multipartResolver.setMaxInMemorySize(1048576);
		return multipartResolver;
	}



}
