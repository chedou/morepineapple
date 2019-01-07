package com.jnshu.resourceservice;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.client.circuitbreaker.*;
import org.springframework.cloud.client.discovery.*;
import org.springframework.cloud.netflix.eureka.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.context.annotation.*;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@ComponentScan(basePackages = "com.jnshu.resourceservice.*" )
@EnableDiscoveryClient
@EnableCircuitBreaker
public class ResourcesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourcesServiceApplication.class, args);
	}

}

