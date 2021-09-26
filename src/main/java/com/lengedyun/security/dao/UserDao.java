package com.lengedyun.security.dao;

import com.lengedyun.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @title: UserDao
 * @description: TODO
 * @auther: zhangjianyun
 * @date: 2021/9/26 11:57
 */

public interface UserDao extends JpaRepository<User,Long> {

    User findUserByUsername(String username);
}
