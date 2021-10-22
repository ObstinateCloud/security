package com.lengedyun.security.controller;

import com.lengedyun.security.dao.UserDao;
import com.lengedyun.security.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author 张健云
 * @title: SessionController
 * @description: spring-security test
 * @auther: 张健云
 * @date: 2021/5/28 7:56
 */
@RestController
public class SessionController {
    @Value("${server.port}")
    Integer port;

    @GetMapping("setSession")
    public String setSession(HttpSession session){
        session.setAttribute("user","zjy");
        return String.valueOf(port);
    }

    @GetMapping("getSession")
    public String getSession(HttpSession session){
        return session.getAttribute("user")+":"+port;
    }





}
