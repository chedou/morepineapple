package com.jnshu.resourceservice.exception;

/**
 * @description 自定义用户登录异常处理类
 * @author Mr.HUANG
 * @date 2018/12/13
 */
public class UserLoginException extends RuntimeException{

    /**
     * @Description 用户登录异常处理类
     * @param message 异常信息
     * @return
     * @author Mr.HUANG
     * @date 2018/12/13
     * @throws RuntimeException
     */
    public UserLoginException(String message) {
        super(message);
    }

}
