package com.jnshu.resourceservice.service.impl;

import com.aliyun.oss.*;
import com.jnshu.resourceservice.exception.ServiceException;
import com.jnshu.resourceservice.service.*;
import com.jnshu.resourceservice.utils.oss.*;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;
import org.springframework.web.multipart.commons.*;

import java.io.*;
import java.rmi.*;
import java.util.*;

/**
 * @program: morepineapple
 * @description:
 * @author: Mr.huang
 * @create: 2019-01-03 16:48
 **/
@Service
public class FileModuleImpl implements FileModuleService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	/**
	 * @return java.lang.String
	 * @throws
	 * @Description 文件上传
	 * @author Mr.HUANG
	 * @date 2019/1/3
	 */
	@Override
	public String uploadFile(MultipartFile file) throws IOException {

		logger.info("=========>OSS文件上传开始："+file.getName());
		// 获取原文件名
		String originalFileName = file.getOriginalFilename();
		logger.info("上传前的文件名：" + originalFileName);
		// 获取原文件大小
		Long originalFileSize  = file.getSize();
		// 上传之后的文件名
		String newFileName = null;
		// 上传文件返回的文件名
		String key = "";

		if (null == file ){
			throw new ServiceException("文件为空，请查看服务器日志");
		}

		// 将 multipartfilein 转化为 inputstream流
		CommonsMultipartFile cFile = (CommonsMultipartFile) file;
		DiskFileItem fileItem = (DiskFileItem) cFile.getFileItem();
		InputStream inputStream = fileItem.getInputStream();
		if (!file.isEmpty()){
			OSSClient ossClient = OSSUtilsByALiYun.getOSSClient();

			if (originalFileName.length()>0){
				newFileName = UUID.randomUUID() +"";
			}
			// 将文件上传并获得返回值 key(文件名称)
			key = OSSUtilsByALiYun.uploadObject2OSS(ossClient, inputStream,
					originalFileName,newFileName, originalFileSize);
		}
		if (null==key ){
			throw new ServerException("文件上传失败，请查看服务器日志");
		}
		String fileURL = "https://" + OSSUtilsByALiYun.getStaticBucketName() +
				".oss-cn-shenzhen.aliyuncs.com/" + key;
		logger.info("fileURL:"  + fileURL);

		return fileURL;
	}
}
