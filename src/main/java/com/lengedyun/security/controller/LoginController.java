package com.lengedyun.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title: LoginController
 * @description: TODO
 * @auther: 张健云
 * @date: 2021/6/1 7:40
 */
@RestController
public class LoginController {

    @PostMapping("doLogin")
    public void login(String user,String pwd){
        System.out.println(user+":"+pwd);
    }

    @PostMapping("loginSuccess")
    public String loginSuccess(){
        return "login-success";
    }

    @GetMapping("defaultSuccessUrl")
    public String defaultSuccessUrl(){
        return "default-success-url";
    }

    @PostMapping("loginFailureForward")
    public String loginFailureForward(){
        return "login-failure-forward";
    }

    @GetMapping("loginFailure")
    public String loginFailure(){
        return "login-failure";
    }

    @GetMapping("myLogout")
    public String myLogout(){
        return "logout-success";
    }

    @PostMapping("myLogoutPost")
    public String myLogoutPost(){
        return "logout-success-post";
    }
}
