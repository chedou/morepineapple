package com.jnshu.resourceservice.web.test;

import com.alibaba.fastjson.*;
import com.jnshu.resourceservice.config.ali.*;
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

}
