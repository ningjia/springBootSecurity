package com.example.springBootSecurity.conf;

import com.example.springBootSecurity.service.LoginService;
import com.example.springBootSecurity.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

        /*
 * 注册Spring Security
 *
 * @EnableWebSecurity注解开启Spring Security的功能
 *
 * authorizeRequests()定义哪些URL需要被保护、哪些不需要被保护
 * formLogin()定义当需要用户登录时，转到的登录页面
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //设置匹配器来匹配路径
                .antMatchers("/", "/function2").permitAll()
                //设置请求都必须要有权限认证
                .anyRequest().authenticated()
                //设置登录页面
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                //设置注销请求
                .and()
                .logout()
                .permitAll();
    }

}
