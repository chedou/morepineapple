package com.jnshu.backendsystem;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.jdbc.*;
import org.springframework.cloud.client.circuitbreaker.*;
import org.springframework.cloud.client.discovery.*;
import org.springframework.cloud.netflix.eureka.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.context.annotation.*;


@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableDiscoveryClient
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = "com.jnshu.backendsystem.*" )
@EnableCircuitBreaker
public class BackendSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendSystemApplication.class, args);
	}

	//@Bean
	//PageHelper pageHelper(){
	//	//分页插件
	//	PageHelper pageHelper = new PageHelper();
	//	Properties properties = new Properties();
	//	properties.setProperty("reasonable", "true");
	//	properties.setProperty("supportMethodsArguments", "true");
	//	properties.setProperty("returnPageInfo", "check");
	//	properties.setProperty("params", "count=countSql");
	//	pageHelper.setProperties(properties);
    //
	//	//添加插件
	//	new SqlSessionFactoryBean().setPlugins(new Interceptor[]{pageHelper});
	//	return pageHelper;
	//}
}
