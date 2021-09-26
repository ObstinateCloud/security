package com.lengedyun.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lengedyun.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.io.PrintWriter;

/**
 * @title: WebConfig
 * @description: TODO
 * @auther: 张健云
 * @date: 2021/5/28 8:12
 */
//@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("zjy")
//                //密文 zjy
//                .password("$2a$10$dIHsUce.Gmjx.xYjffI3y.JcO/18q72Oe2KeeiUsOrczyFtGqTn8W")
////                .password("zjy")
//                //角色一定要配
//                .roles("admin")

        //使用自定义的userDetailsService
        auth.userDetailsService(userService)
                    ;
    }

    @Autowired
    private DataSource dataSource;

    /**
     * 权限配置
     *
     * @return
     */
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        //内存中创建
////        InMemoryUserDetailsManager memoryUserDetailsManager = new InMemoryUserDetailsManager();
////        memoryUserDetailsManager.createUser(User.withUsername("admin").password("$2a$10$dIHsUce.Gmjx.xYjffI3y.JcO/18q72Oe2KeeiUsOrczyFtGqTn8W").roles("admin").build());
////        memoryUserDetailsManager.createUser(User.withUsername("zjy").password("$2a$10$dIHsUce.Gmjx.xYjffI3y.JcO/18q72Oe2KeeiUsOrczyFtGqTn8W").roles("user").build());
//        //在数据库中创建
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//        //防止每次启动都创建
//        if (!jdbcUserDetailsManager.userExists("admin")) {
//            jdbcUserDetailsManager.createUser(User.withUsername("admin").password("$2a$10$dIHsUce.Gmjx.xYjffI3y.JcO/18q72Oe2KeeiUsOrczyFtGqTn8W").roles("admin").build());
//        }
//        if (!jdbcUserDetailsManager.userExists("zjy")) {
//            jdbcUserDetailsManager.createUser(User.withUsername("zjy").password("$2a$10$dIHsUce.Gmjx.xYjffI3y.JcO/18q72Oe2KeeiUsOrczyFtGqTn8W").roles("user").build());
//        }
//        return jdbcUserDetailsManager;
//    }

    /**
     * 角色继承
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        //admin 角色自动拥有 user角色的权限
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                //如果只配置了loginPage，则前端form的action地址与loginPage一致
//                .loginPage("/mylogin.html")
//                //如果同时配置了loginPage和loginProcessingUrl，则前端form的action地址与loginProcessingUrl一致,否则无法跳转
//                .loginProcessingUrl("/doLogin")
//                .usernameParameter("user")
//                .passwordParameter("pwd")
//                .permitAll()
//                //1.服务端跳转，无论从哪来都去loginSuccess
////                .successForwardUrl("/loginSuccess")
//                //2.客户端跳转，重定向到登录前的路径 一般1.和2.只需配置一个
////                .defaultSuccessUrl("/defaultSuccessUrl",true)//无论如何都跳转效果和1一样
//                .defaultSuccessUrl("/defaultSuccessUrl")
////                .failureForwardUrl("/loginFailureForward")
//                .failureUrl("/loginFailure")
//                .and()
//                .logout()
////                .logoutUrl("/myLogout")
////                .logoutRequestMatcher(new AntPathRequestMatcher("/myLogoutPost","POST"))
//                .and()
//                .csrf().disable();
//    }

    /**
     * 前后端分离
     * 采用handler方式处理请求
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                .anyRequest().authenticated()
                //任何请求都需要认证才能访问.注意配置顺序和优先级 anyRequest必须配置在antMatchers之后
                .and()
                .formLogin()
                //如果只配置了loginPage，则前端form的action地址与loginPage一致
//                .loginPage("/mylogin.html")
                //如果同时配置了loginPage和loginProcessingUrl，则前端form的action地址与loginProcessingUrl一致,否则无法跳转
                .loginProcessingUrl("/doLogin")
                .usernameParameter("user")
                .passwordParameter("pwd")
                .permitAll()
                .successHandler((req, resp, auth) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = resp.getWriter();
                    writer.write(new ObjectMapper().writeValueAsString(auth.getPrincipal()));
                    writer.flush();
                    writer.close();

                })
                .failureHandler((req, resp, excecption) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = resp.getWriter();
                    String msg = "login error";

                    if (excecption instanceof LockedException) {
                        msg = "account lock";
                    } else if (excecption instanceof BadCredentialsException) {
                        msg = "account or password error";
                    }
                    writer.write(new ObjectMapper().writeValueAsString(msg));
                    writer.flush();
                    writer.close();
                })
                .and()
                .logout()
                .logoutSuccessHandler((req, resp, auth) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = resp.getWriter();
                    writer.write(new ObjectMapper().writeValueAsString("注销登录成功"));
                    writer.flush();
                    writer.close();
                })
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, exception) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = resp.getWriter();
                    writer.write(new ObjectMapper().writeValueAsString("未登录"));
                    writer.flush();
                    writer.close();
                })
        ;
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/verifycode", "/js/**", "/css/**", "/images/**");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }

    public static void main(String[] args) {
        String password = "zjy";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(password);
        System.out.println(encode);
    }
}
