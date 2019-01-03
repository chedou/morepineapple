package com.jnshu.resourceservice.service;


import org.springframework.web.multipart.*;

import java.io.*;

/**
 * @Description 文件上传服务类
 * @author Mr.HUANG
 * @date 2019/1/3
 *
 */
public interface FileModuleService {

	/**
	 * @Description 文件上传
	 * @param []
	 * @return java.lang.String
	 * @author Mr.HUANG
	 * @date 2019/1/3
	 * @throws
	 */
	String uploadFile(MultipartFile file) throws IOException;



}
