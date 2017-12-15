package com.example.springBootSecurity.mapper;

import com.example.springBootSecurity.model.Role;
import com.example.springBootSecurity.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    /**
     * 根据用户名称，返回用户信息
     *
     * 由于不连接数据库，此方法仅用于模拟测试
     * @param username
     * @return
     */
    public User findByUsername(String username){
        /* 模拟数据库查询出的角色数组 */
        //管理员角色
        List<Role> roleList_admin = new ArrayList<Role>();
        roleList_admin.add(new Role(1l,"ROLE_ADMIN"));
        roleList_admin.add(new Role(2l,"ROLE_TEST"));
        //普通用户角色
        List<Role> roleList_normal = new ArrayList<Role>();
        roleList_normal.add(new Role(3l,"ROLE_NORMAL"));
        /* 模拟数据库查询出的用户数据 */
        //管理员用户
        User user_admin = new User();
        user_admin.setUsername("admin");
        user_admin.setPassword("1234");
        user_admin.setId(1l);
        user_admin.setRoles(roleList_admin);
        //普通用户
        User user_normal = new User();
        user_normal.setUsername("user");
        user_normal.setPassword("1234");
        user_normal.setId(1l);
        user_normal.setRoles(roleList_normal);
        //仅当用户名称为admin或user时进行返回，用于测试"用户名不存在"的情况
        if(username.equals("admin")){
            return user_admin;
        } else if(username.equals("user")){
            return user_normal;
        } else {
            return null;
        }
    }
}
