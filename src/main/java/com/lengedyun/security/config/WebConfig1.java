package com.lengedyun.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

/**
 * @title: WebConfig1
 * @description: TODO
 * @auther: zhangjianyun
 * @date: 2021/9/26 16:28
 */
@Configuration
public class WebConfig1 extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    /**
     * 令牌持久化方案
     * @return
     */
    @Bean
    JdbcTokenRepositoryImpl jdbcTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                //自动登录
                .rememberMe()
                //防止服务端重启导致，客户端的失效
                .key("zjy")
                .tokenRepository(jdbcTokenRepository())
                .and()
                .csrf()
                .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("zjy")
                .password("$2a$10$dIHsUce.Gmjx.xYjffI3y.JcO/18q72Oe2KeeiUsOrczyFtGqTn8W")
                .roles("admin");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
