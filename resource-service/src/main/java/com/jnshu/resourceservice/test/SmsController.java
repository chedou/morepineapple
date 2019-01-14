package com.jnshu.resourceservice.test;

import com.alibaba.fastjson.*;
import com.aliyuncs.dysmsapi.model.v20170525.*;
import com.aliyuncs.exceptions.*;
import com.jnshu.resourceservice.config.ali.*;
import com.jnshu.resourceservice.utils.sms.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * @program: morepineapple
 * @description:
 * @author: Mr.huang
 * @create: 2019-01-02 21:29
 **/
@RestController
@RequestMapping("sms")
public class SmsController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SimpleSMSSender simpleSMSSender;

	@GetMapping("/auto")
	@ResponseBody
	public String smsAuto()  {
		SimpleSMSSender.SMS sms = SimpleSMSSender.newSMS();
		sms.setPhoneNumbers("13537288757");
		sms.setTemplateParam("{\"code\":\"123456\"}");
		sms.setTemplateCode("SMS_142387991");
		SimpleSMSSender.Result result = simpleSMSSender.sendSms(sms);
		return JSON.toJSONString(result);
	}

	@GetMapping("/Util")
	@ResponseBody
	public String smsUtil(String phoneNumber)  {

		SmsUtil.setNewcode();
		SendSmsResponse sendSmsResponse =null;
		try{
			sendSmsResponse =SmsUtil.sendSms(phoneNumber,SmsUtil.getNewcode());
		} catch (ClientException e) {
			e.printStackTrace();
		}
		logger.info("短信接口返回的 Code:" + sendSmsResponse.getCode() );
		logger.info("短信接口返回的 getMessage：" + sendSmsResponse.getMessage() );
		logger.info("短信接口返回的 RequestId" + sendSmsResponse.getRequestId() );
		logger.info("短信接口返回的 BizId：" + sendSmsResponse.getBizId() );


		return JSON.toJSONString(sendSmsResponse);
	}
}
