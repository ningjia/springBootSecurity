package com.example.springBootSecurity.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * 实现springSecurity提供的权限类GrantedAuthority
 */
public class Authority implements GrantedAuthority {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Authority(String id, String name) {

        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
