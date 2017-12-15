package com.example.springBootSecurity.service.impl;

import com.example.springBootSecurity.mapper.UserMapper;
import com.example.springBootSecurity.model.User;
import com.example.springBootSecurity.service.LoginService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("userDetailsService")
public class LoginServiceImpl implements LoginService, UserDetailsService {

    UserMapper userMapper = new UserMapper();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }
}
