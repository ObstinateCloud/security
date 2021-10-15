package com.lengedyun.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

/**
 * @title: IpService
 * @description: get ip address
 * @auther: zhangjianyun
 * @date: 2021/10/14 11:13
 */
@Service
public class IpService {

    public void getIpAddr(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        System.out.println(details);
    }
}
