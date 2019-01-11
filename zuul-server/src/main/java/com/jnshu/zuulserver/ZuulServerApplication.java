package com.jnshu.zuulserver;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.client.discovery.*;
import org.springframework.cloud.netflix.eureka.*;
import org.springframework.cloud.netflix.zuul.*;

@SpringBootApplication
/**
 * // 使用@EnableZuulProxy来开启Zuul的支持，如果你不想使用Zuul提供的Filter和反向代理的功能的话，
 * 此处可以使用@EnableZuulServer注解
 */
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
public class ZuulServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulServerApplication.class, args);
	}

	// @Bean
	// public PatternServiceRouteMapper serviceRouteMapper(){
	// 	return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)","${version}/${name}");
	// }

}

