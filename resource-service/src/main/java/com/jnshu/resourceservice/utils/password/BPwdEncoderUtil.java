package com.jnshu.resourceservice.utils.password;


import org.springframework.security.crypto.bcrypt.*;

/**
 * @Description 字符串加密转化工具类
 * @author Mr.HUANG
 * @date 2018/12/13
 *
 */
public class BPwdEncoderUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * @description 用BCryptPasswordEncoder加密
     * @param password
     * @return String 加密后的密文
     */
    public static String  BCryptPassword(String password){
        return encoder.encode(password);
    }

    /**
     *
     * @description  rawPassword
     * @param encodedPassword
     * @return null
     */
    public static boolean matches(CharSequence rawPassword, String encodedPassword){
        return encoder.matches(rawPassword,encodedPassword);
    }



}
