package com.example.springBootSecurity.controller;

import com.example.springBootSecurity.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class LoginController {

    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/function1")
    public String function1(Model model) {
        getUserInfo1();
        getUserInfo2();
        return "function1";
    }

    @RequestMapping("/function2")
    public String function2(Model model) {
        getUserInfo1();
        getUserInfo2();
        return "function2";
    }


    /**
     * 获取request对象
     *
     * 也可以在方法参数中添加Request参数，Spring会自动进行注入。
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return attrs.getRequest();
    }

    /**
     * 获取当前登录用户信息,并输出至控制台
     *
     * 必须在springSecurity过滤器执行中执行,否则getAuthentication()获取结果为NULL
     */
    public void getUserInfo1(){
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(object instanceof UserDetails)) {
            //没有经过springSecurity进行用户认证的话，无法获取相关对象，直接返回
            return;
        }
        UserDetails userDetails = (UserDetails) object;

        org.springframework.security.core.userdetails.User u;

        System.out.println("--当前登录用户信息（getUserInfo1）--");
        // 登录名
        System.out.println("Username:" + userDetails.getUsername());
        // 登录密码
        System.out.println("Password:" + userDetails.getPassword());
        // 获得当前用户所拥有的权限
        Collection<? extends GrantedAuthority> collection = userDetails.getAuthorities();
        List<GrantedAuthority> authorities = new ArrayList(collection);
        for (GrantedAuthority grantedAuthority : authorities) {
            System.out.println("Authority:" + grantedAuthority.getAuthority());
        }
    }

    /**
     * 获取当前登录用户信息,并输出至控制台(适用于getUserInfo1方法无法获取到数据时)
     *
     * 经过springSecurity认证后，springSecurity会把一个SecurityContextImpl对象存储到session中，此对象中有当前用户的各种资料
     */
    public void getUserInfo2(){
        HttpServletRequest request = getRequest();
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if(securityContextImpl == null) {
            //没有经过springSecurity进行用户认证的话，无法获取相关对象，直接返回
            return;
        }
        Authentication authentication = securityContextImpl.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();//可以直接由userDetails获取用户信息；也可以参考下面语句获取更多详细信息；
        System.out.println("--当前登录用户信息（getUserInfo2）--");
        // 登录名
        System.out.println("Username:" + authentication.getName());
        // 登录密码，未加密的
        System.out.println("Credentials:" + authentication.getCredentials());
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        // 获得访问地址
        System.out.println("RemoteAddress:" + details.getRemoteAddress());
        // 获得sessionid
        System.out.println("SessionId:" + details.getSessionId());
        // 获得当前用户所拥有的权限
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            System.out.println("Authority:" + grantedAuthority.getAuthority());
        }
    }
}
