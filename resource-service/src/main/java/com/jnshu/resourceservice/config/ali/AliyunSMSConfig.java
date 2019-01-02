package com.jnshu.resourceservice.config.ali;

import groovy.util.logging.*;
import lombok.*;
import org.springframework.boot.context.properties.*;
import org.springframework.stereotype.*;


/**
 * @program: morepineapple
 * @description:
 * @author: Mr.huang
 * @create: 2019-01-02 20:33
 **/
@Slf4j
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "aliyun.sms")
public class AliyunSMSConfig {
	private String accessKeyId;
	private String accessKeySecret;
	private String product;
	private String domain;
	private String regionId;
	private String signName;
	private String dateFormat;
	private String endpointName;

}
