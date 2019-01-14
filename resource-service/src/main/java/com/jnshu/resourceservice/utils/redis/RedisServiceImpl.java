package com.jnshu.resourceservice.utils.redis;

import com.alibaba.fastjson.*;
import org.springframework.dao.*;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * @program: morepineapple
 * @description:
 * @author: Mr.huang
 * @create: 2019-01-02 12:17
 **/
@Service
public class RedisServiceImpl implements RedisService {

	@Resource
	private RedisTemplate<String, ?> redisTemplate;

	@Override
	public boolean set(final String key, final String value) {

		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				connection.set(serializer.serialize(key), serializer.serialize(value));
				return true;
			}
		});
		return result;
	}

	@Override
	public String get(final String key){
		String result = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] value =  connection.get(serializer.serialize(key));
				return serializer.deserialize(value);
			}
		});
		return result;
	}

	@Override
	public long del(final String key){
		long result = redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				long value =  connection.del(serializer.serialize(key));
				System.out.println("被删除的返回值" + value);
				return value;
			}
		});
		return result;
	}

	@Override
	public boolean expire(final String key, long expire) {
		return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	}

	@Override
	public <T> boolean setList(String key, List<T> list) {
		String value = JSON.toJSONString(list);
		return set(key,value);
	}

	@Override
	public <T> List<T> getList(String key,Class<T> clz) {
		String json = get(key);
		if(json!=null){
			List<T> list = JSON.parseArray(json, clz);
			return list;
		}
		return null;
	}

	@Override
	public long lpush(final String key, Object obj) {
		final String value = JSON.toJSONString(obj);
		long result = redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
				return count;
			}
		});
		return result;
	}

	@Override
	public long rpush(final String key, Object obj) {
		final String value = JSON.toJSONString(obj);
		long result = redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
				return count;
			}
		});
		return result;
	}

	@Override
	public String lpop(final String key) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] res =  connection.lPop(serializer.serialize(key));
				return serializer.deserialize(res);
			}
		});
		return result;
	}



}
