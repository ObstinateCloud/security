package com.lengedyun.security.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @title: Role
 * @description: TODO
 * @auther: zhangjianyun
 * @date: 2021/9/26 11:37
 */
@Entity(name = "t_role")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nameZh;
}
