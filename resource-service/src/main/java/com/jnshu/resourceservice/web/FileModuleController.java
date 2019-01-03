package com.jnshu.resourceservice.web;

import com.jnshu.resourceservice.core.ret.*;
import com.jnshu.resourceservice.service.*;
import io.swagger.annotations.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.io.*;

/**
 * @program: morepineapple
 * @description: 文件上传控制类
 * @author: Mr.huang
 * @create: 2019-01-03 15:27
 **/

@RestController
@RequestMapping("/a")
@Api(tags = {"文件上传模块"}, description = "测试文件上传模块")
public class FileModuleController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	FileModuleService fileModuleService;


	/**
	 * @Description 文件上传
	 * @param [file] 
	 * @return com.jnshu.resourceservice.core.ret.RetResult<?> 
	 * @author Mr.HUANG
	 * @date 2019/1/3 
	 * @throws IOException
	 */ 
	@PostMapping(value = "/upload/file", produces =  "application/json;charset=UTF-8")
	public RetResult<?> uploadFile(@RequestParam("file")MultipartFile file)
			throws IOException {
		logger.info("----UserModuleController----smsVerification-----");
		String Key =fileModuleService.uploadFile(file);
		return RetResponse.result(RetCode.SUCCESS_FILE_UPLOAD).setData(Key);
	}

}
