package com.jnshu.resourceservice.config.swagger;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.*;
import springfox.documentation.schema.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.*;
import springfox.documentation.spring.web.plugins.*;
import springfox.documentation.swagger2.annotations.*;

import java.util.*;

/**
 * @program: morepineapple
 * @description: Swagger2 配置文件
 * @author: Mr.huang
 * @create: 2018-10-14 22:08
 **/

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfigurer extends WebMvcConfigurerAdapter {

	@Bean
	public Docket createRestApi() {
		//添加head参数start
		ParameterBuilder tokenPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
		pars.add(tokenPar.build());
		//添加head参数end

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.jnshu.resourceservice.web"))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(pars);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("萝卜多-多菠萝 APIs")
				.description(" ")
				.termsOfServiceUrl("https://")
				.contact(new Contact("Mr_黄", "https://", null))
				.version("1.0")
				.build();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}






}
