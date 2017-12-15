# 在Spring Boot中使用Spring Security实现权限控制
## 一、添加依赖
```txt
	compile('org.springframework.boot:spring-boot-starter-security')
```
## 二、定义用户与角色
### 定义角色
```java
@Entity
public class SysRole implements GrantedAuthority {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
```
- 如果希望SpringSecurity中管理相关角色/权限，需要实线接口org.springframework.security.core.GrantedAuthority。
### 定义用户
```java
public class User implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private List<Role> roles;
    //...
    //...
}
```
- 自定义的用户对象必须实现接口org.springframework.security.core.userdetails.UserDetails
- 通过实现getAuthorities()方法，设置用户的权限数组。
    - 特别注意：SpringSecutiry根据名称判断authorities数组中的对象是角色还是权限
    - 如果前缀是ROLE_，security就会认为这是个角色信息，而不是权限。
    - 例如ROLE_MENBER就是MENBER角色，CAN_SEND就是CAN_SEND权限。
- 注意修改UserDetails接口中如下方法的返回值
```text
    isAccountNonExpired
    isAccountNonLocked
    isCredentialsNonExpired
    isEnabled
```
## 三、Service类需要实现UserDetailsService接口
- 实现该接口中的loadUserByUsername方法。SpringSecurity中的auth.userDetailsService()方法需要使用该对象作为参数。

## 四、添加SpringSecurity的配置类
```java
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
```

## 五、java代码中获取当前登录用户的信息
- 参考LoginController中的getUserInfo1、getUserInfo2方法

## 六、Html中获取当前登陆用户的信息
### 加入依赖
```text
    compile('org.thymeleaf.extras:thymeleaf-extras-springsecurity4')
```
### 在Html页面中引入标签
```html
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity4">
```
### 标签说明
- 获得当前登陆的用户名：<sec:authentication property="name"/>
- 权限检查
    - 需要同时拥有AUTH_1和AUTH_2两个权限时：
        - sec:authorize="hasAuthority('AUTH_1') and hasAuthority('AUTH_2')"
    - 只需要拥有AUTH_1和AUTH_2其中一个权限时：
        - sec:authorize="hasAnyAuthority('AUTH_1','AUTH_2')"
    - 必须没有AUTH_1或AUTH_2权限时：
        - sec:authorize="!hasAnyAuthority('AUTH_1','AUTH_2')"
## 七、Refer
- [在Spring Boot中使用Spring Security实现权限控制](http://blog.csdn.net/u012702547/article/details/54319508)
- [Spring Security 入门：登录与退出](http://www.jianshu.com/p/a8e317e82425)
- [spring security之httpSecurity使用示例](https://www.cnblogs.com/davidwang456/p/4549344.html?utm_source=tuicool)

