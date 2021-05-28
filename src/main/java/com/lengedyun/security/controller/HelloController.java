package com.lengedyun.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 张健云
 * @title: HelloController
 * @description: spring-security test
 * @auther: 张健云
 * @date: 2021/5/28 7:56
 */
@RestController
public class HelloController {

    @GetMapping("sayHello")
    public String hello(String name){
        return "hello--"+name;
    }
}
