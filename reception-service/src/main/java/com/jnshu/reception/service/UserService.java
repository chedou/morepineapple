package com.jnshu.reception.service;

import com.jnshu.reception.pojo.User;

public interface UserService {
    // TODO: 2018/11/13 查询用户列表
    //List<User> qureyUserList(String roleName, String name );

    // TODO: 2018/11/15 查询用户是否已经注册
    Boolean qureyUserName(String name);
    //// TODO: 2018/11/15 查询手机号码是否已经注册
    Boolean qureyPhoneNumber(String phoneNum);
    // TODO: 2018/11/15 新增用户
    Integer insertUserInfo(User user);

    // TODO: 2018/11/15 登录 查询盐值、密码等
    User qureyPasswordByName(String name);

    // TODO: 2018/11/15 查询用户是否存在
    Boolean jugdeUser(String name,String password);
}
