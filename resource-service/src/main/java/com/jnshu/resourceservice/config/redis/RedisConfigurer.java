package com.jnshu.resourceservice.config.redis;

import org.springframework.boot.context.properties.*;
import org.springframework.cache.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.connection.jedis.*;
import org.springframework.data.redis.core.*;
import redis.clients.jedis.*;

/**
 * @program: morepineapple
 * @description: redis配置
 * @author: Mr.huang
 * @create: 2019-01-02 12:15
 **/
@Configuration
@EnableCaching
public class RedisConfigurer extends CachingConfigurerSupport {

	@Bean
	@ConfigurationProperties(prefix="spring.redis")
	public JedisPoolConfig getRedisConfig(){
		JedisPoolConfig config = new JedisPoolConfig();
		return config;
	}

	@Bean
	@ConfigurationProperties(prefix="spring.redis")
	public JedisConnectionFactory getConnectionFactory(){
		JedisConnectionFactory factory = new JedisConnectionFactory();
		JedisPoolConfig config = getRedisConfig();
		factory.setPoolConfig(config);
		return factory;
	}


	@Bean
	public RedisTemplate<?, ?> getRedisTemplate(){
		RedisTemplate<?,?> template = new StringRedisTemplate(getConnectionFactory());
		return template;
	}




}
