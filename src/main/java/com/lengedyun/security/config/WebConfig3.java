package com.lengedyun.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.session.HttpSessionEventPublisher;


/**
 * @title: WebConfig1
 * @description: 自定义认证逻辑
 * @auther: zhangjianyun
 * @date: 2021/9/26 16:28
 */
@Configuration
public class WebConfig3 extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .csrf().disable()
                .sessionManagement()
                //最大会话数量
                .maximumSessions(1)
                //禁止新的登录
//                .maxSessionsPreventsLogin(true)
        ;
    }

    /**
     * 配合禁止新的登录使用否则 注销登录后无法再登录
     * @return
     */
//    @Bean
//    HttpSessionEventPublisher httpSessionEventPublisher(){
//        return new HttpSessionEventPublisher();
//    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("zjy").password("123").roles("admin").build());
//        return manager;
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.inMemoryAuthentication()
                .withUser("zjy")
                //密文 zjy
                .password("123")
//                .password("zjy")
                //角色一定要配
                .roles("admin");

    }
}
