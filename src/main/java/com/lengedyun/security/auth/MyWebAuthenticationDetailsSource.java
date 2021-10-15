package com.lengedyun.security.auth;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @title: MyWebAuthenticationDetailsSource
 * @description: 自定义WebAuthenticationDetailsSource
 * @auther: zhangjianyun
 * @date: 2021/10/14 11:59
 */
@Component
public class MyWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest,MyWebAuthenticationDetails> {
    @Override
    public MyWebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new MyWebAuthenticationDetails(context);
    }
}
