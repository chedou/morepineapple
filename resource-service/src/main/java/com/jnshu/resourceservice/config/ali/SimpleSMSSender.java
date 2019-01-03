package com.jnshu.resourceservice.config.ali;

import com.aliyuncs.*;
import com.aliyuncs.dysmsapi.model.v20170525.*;
import com.aliyuncs.exceptions.*;
import com.aliyuncs.profile.*;
import groovy.util.logging.*;
import lombok.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.text.*;
import java.util.*;

/**
 * @program: morepineapple
 * @description:
 * @author: Mr.huang
 * @create: 2019-01-02 21:15
 **/
@Component
@Slf4j
public class SimpleSMSSender {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 *	自动注册短信服务的配置
 	 */
	@Autowired
	private AliyunSMSConfig smsConfig;


	public static SMS newSMS(){
		return new SMS();
	}


	// 一个短信实体
	@Data
	public static class SMS {
		private String phoneNumbers;
		private String templateParam;
		private String outId;
		private String templateCode;

	}

	@Data
	public static class Result {

		private SendSmsResponse sendSmsResponse;
		private SMS sms;
	}


	/**
	 * 发送短信
	 *
	 *
	 * 发送验证码类的短信时，每个号码每分钟最多发送一次，每个小时最多发送5次。
	 * 其它类短信频控请参考阿里云
	 *
	 * @param sms 短信
	 */
	public Result sendSms(SMS sms) {
		// 获取客户端
		IAcsClient acsClient = getClient();
		// 获取短信请求
		SendSmsRequest request = getRequest(sms);
		// 获取短信返回
		SendSmsResponse sendSmsResponse = null;
		try {
			sendSmsResponse = acsClient.getAcsResponse(request);
		} catch (ClientException e) {
			logger.error("发送短信发生错误。错误代码是 [{}]，错误消息是 [{}]，错误请求ID是 [{}]，错误Msg是 [{}]，错误类型是 [{}]", e.getErrCode(), e.getMessage(), e.getRequestId(), e.getErrMsg(), e.getErrorType());
			e.printStackTrace();
		}
		// 返回结果
		Result result = new Result();
		result.setSendSmsResponse(sendSmsResponse);
		result.setSms(sms);
		return result;
	}

	public static Query newQuery(){
		return new Query();
	}


	// 构建一个查询器
	@Data
	public static class Query {

		/** 业务ID */
		private String bizId;
		private String phoneNumber;
		private Date sendDate;
		private Long pageSize;
		private Long currentPage;

	}

	/**
	 * 查询短信发送结果
	 *
	 * @param query 查询条件
	 */
	public QuerySendDetailsResponse querySendDetails(Query query) {
		IAcsClient acsClient = getClient();
		QuerySendDetailsRequest request = new QuerySendDetailsRequest();
		request.setPhoneNumber(query.getPhoneNumber());
		request.setBizId(query.getBizId());
		SimpleDateFormat ft = new SimpleDateFormat(smsConfig.getDateFormat());
		request.setSendDate(ft.format(query.getSendDate()));
		request.setPageSize(query.getPageSize());
		request.setCurrentPage(query.getCurrentPage());
		QuerySendDetailsResponse querySendDetailsResponse = null;
		try {
			querySendDetailsResponse = acsClient.getAcsResponse(request);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return querySendDetailsResponse;
	}


	// 获取短信请求
	private SendSmsRequest getRequest(SMS sms) {
		SendSmsRequest request = new SendSmsRequest();
		request.setPhoneNumbers(sms.getPhoneNumbers());
		request.setSignName(smsConfig.getSignName());
		request.setTemplateCode(sms.getTemplateCode());
		request.setTemplateParam(sms.getTemplateParam());
		request.setOutId(sms.getOutId());
		return request;
	}


	// 获取短信发送服务机
	private IAcsClient getClient() {
		IClientProfile profile = DefaultProfile.getProfile(smsConfig.getRegionId(), smsConfig.getAccessKeyId(), smsConfig.getAccessKeySecret());
		try {
			DefaultProfile.addEndpoint(smsConfig.getEndpointName(),
					smsConfig.getRegionId(), smsConfig.getProduct(), smsConfig.getDomain()
			);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return new DefaultAcsClient(profile);
	}



}
