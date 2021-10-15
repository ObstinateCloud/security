package com.lengedyun.security.auth;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @title: MyWebAuthenticationDetails
 * @description: 自定义WebAuthenticationDetails，在对象中放入更多httprequest的参数
 * @auther: zhangjianyun
 * @date: 2021/10/14 11:44
 */
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    //自定义属性
    private  boolean isPassed;

    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        String code = request.getParameter("code");
        String verify_code = (String) request.getSession().getAttribute("verify_code");
        if (code != null && verify_code != null && code.equals(verify_code)) {
            isPassed = true;
        }

    }

    public boolean isPassed() {
        return isPassed;
    }
}
