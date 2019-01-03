package com.jnshu.resourceservice.utils.sms;

import com.aliyuncs.*;
import com.aliyuncs.dysmsapi.model.v20170525.*;
import com.aliyuncs.exceptions.*;
import com.aliyuncs.profile.*;
import groovy.util.logging.*;
import org.springframework.boot.context.properties.*;
import org.springframework.stereotype.*;

/**
 * @program: morepineapple
 * @description:
 * @author: Mr.huang
 * @create: 2019-01-03 09:37
 **/
@Slf4j
@Component
@ConfigurationProperties(prefix = "aliyun.sms")
public class SmsUtil {
	private static String accessKeyId;
	private static String accessKeySecret;
	private static String product;
	private static String domain;
	private static String regionId;
	private static String signName;
	private static String dateFormat;
	private static String endpointName;
	private static String templateCode;
	private static int newcode;

	public static SendSmsResponse sendSms(String telephone, String code) throws ClientException {


		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);

		System.out.println(accessKeyId + "1" + accessKeySecret + "2" + product);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		// 必填:待发送手机号
		request.setPhoneNumbers(telephone);
		// TODO 改这里
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(signName);
		// TODO 改这里
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(templateCode);
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的用户,您的验证码为${code}"时,此处的值为
		request.setTemplateParam("{\"code\":\"" + code + "\"}");

		// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");

		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId("yourOutId");

		// hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if (sendSmsResponse.getCode() != null && "OK".equals(sendSmsResponse.getCode())) {
			System.out.println("短信发送成功！");
		} else {
			System.out.println("短信发送失败！");
		}


		return sendSmsResponse;
	}


	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		SmsUtil.product = product;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		SmsUtil.domain = domain;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		SmsUtil.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		SmsUtil.accessKeySecret = accessKeySecret;
	}


	public static String getNewcode() {
		return Integer.toString(newcode);
	}

	public static void setNewcode() {
		//每次调用生成一次四位数的随机数
		newcode = (int) (Math.random() * 9999) + 100;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		SmsUtil.signName = signName;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		SmsUtil.templateCode = templateCode;
	}
}
