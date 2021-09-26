package com.lengedyun.security.service;

import com.lengedyun.security.dao.UserDao;
import com.lengedyun.security.entity.Role;
import com.lengedyun.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: UserService
 * @description: TODO
 * @auther: zhangjianyun
 * @date: 2021/9/26 11:59
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(s);
        if (user ==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }

}
