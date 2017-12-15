package com.example.springBootSecurity.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class User implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private List<Role> roles;

    public User() {
    }

    public User(Long id, String username, String password) {

        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(Long id, String username, String password, List<Role> roles) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 返回用户信息（包含权限）
     *
     * 特别注意：如果前缀是ROLE_，security就会认为这是个角色信息，而不是权限。例如ROLE_MENBER就是MENBER角色，CAN_SEND就是CAN_SEND权限
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        //将角色数据返回至Security
        List<Role> roles = this.getRoles();
        auths.addAll(roles);
        //将权限数据返回至Security
        for (Role role : roles) {
            List<Authority> authorityList = findByRolename(role.getName());
            auths.addAll(authorityList);
        }
        return auths;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 返回角色所对应的权限
     *
     * 由于不连接数据库，此方法用于模拟效果
     * @param rolename
     * @return
     */
    private List<Authority> findByRolename(String rolename){
        List<Authority> authorityList = new ArrayList<Authority>();
        if(rolename.equals("ROLE_ADMIN")){
            authorityList.add(new Authority("1","AUTH_ADMIN"));
        } else if(rolename.equals("ROLE_TEST")){
            authorityList.add(new Authority("2","AUTH_TEST_1"));
            authorityList.add(new Authority("3","AUTH_TEST_2"));
        } else if(rolename.equals("ROLE_NORMAL")){
            authorityList.add(new Authority("4","AUTH_NORMAL_1"));
            authorityList.add(new Authority("5","AUTH_NORMAL_2"));
        }
        return authorityList;
    }

}
