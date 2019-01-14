package com.jnshu.resourceservice.test;

import com.jnshu.resourceservice.core.ret.*;
import com.jnshu.resourceservice.utils.redis.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.*;

/**
 * @program: morepineapple
 * @description:
 * @author: Mr.huang
 * @create: 2019-01-02 19:10
 **/
@RestController
@RequestMapping("redis")
public class RedisController {
	@Resource
	private RedisService redisService;

	@PostMapping("/setRedis")
	public RetResult<String> setRedis(String name) {
		redisService.set("name",name);
		return RetResponse.makeOKRsp(name);
	}

	@PostMapping("/getRedis")
	public RetResult<String> getRedis() {
		String name = redisService.get("name");
		return RetResponse.makeOKRsp(name);
	}

}
