package com.jnshu.resourceservice.web;

import com.jnshu.resourceservice.core.ret.*;
import com.jnshu.resourceservice.service.*;
import com.jnshu.resourceservice.service.impl.*;
import com.jnshu.resourceservice.utils.authorization.*;
import io.swagger.annotations.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;


@RestController
@RequestMapping()
public class LoginController {

    @Autowired
    LoginServiceImpl loginServiceImpl;

    @Autowired
    LoginService loginService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * @Description 用户登录接口
     * @param [username, password]
     * @return com.jnshu.resourceservice.dto.UserLoginDTO
     * @author Mr.HUANG
     * @date 2019/1/10
     * @throws
     */
    @ApiOperation(value = "login",  notes = "用户登录模块")
    @PostMapping(value = "/login",produces =  "application/json;charset=UTF-8")
    public RetResult<?> login(@RequestParam("username") @NotNull String username ,
                              @RequestParam("password") @NotNull String password){
        return RetResponse.result(RetCode.SUCCESS_USER_LOGIN).setData(loginServiceImpl.login(username,password));
    }

    /**
     * @Description 用户登出接口
     * @param [authorization ]
     * @return com.jnshu.resourceservice.dto.UserLoginDTO
     * @author Mr.HUANG
     * @date 2019/1/14
     * @throws
     */
    @ApiOperation(value = "out",  notes = "用户登出模块")
    @PostMapping(value = "/out", produces =  "application/json;charset=UTF-8")
    public  RetResult<?> out(@RequestHeader(value = "Authorization") String authorization ){
        System.out.println("controller 层：" + authorization);

        Long operatorId =Long.parseLong(AuthorizationUtils.getUserId());
        logger.info("----UserModuleController----out-----");
        logger.info("操作者为:{}",operatorId );

        loginService.out(authorization);
        return RetResponse.result(RetCode.SUCCESS_USER_LOGIN_OUT);
    }




}
