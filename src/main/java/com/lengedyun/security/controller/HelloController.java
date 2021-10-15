package com.lengedyun.security.controller;

import com.lengedyun.security.dao.UserDao;
import com.lengedyun.security.entity.Role;
import com.lengedyun.security.entity.User;
import com.lengedyun.security.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张健云
 * @title: HelloController
 * @description: spring-security test
 * @auther: 张健云
 * @date: 2021/5/28 7:56
 */
@RestController
public class HelloController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private IpService ipService;

    @GetMapping("sayHello")
    public String hello(String name){
        ipService.getIpAddr();
        return "hello--welcome"+name;
    }

    @GetMapping("admin/Hello")
    public String adminHello(String name){
        return "hello-admin-"+name;
    }

    @GetMapping("user/Hello")
    public String userHello(String name){
        return "hello-user-"+name;
    }


}
