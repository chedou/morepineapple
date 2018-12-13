package com.jnshu.resourceservice.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.*;

/**
 * @description: 使用SpringBoot 2.0版本之后出现密文解析错误报错，这里用于解决密文解析错误问题
 * @author: Mr.huang
 * @create: 2018-10-10 00:43
 **/
@Configurable
public class CustomPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence charSequence) {
		return charSequence.toString();
	}

	@Override
	public boolean matches(CharSequence charSequence, String s) {
		return s.equals(charSequence.toString());
	}

}
