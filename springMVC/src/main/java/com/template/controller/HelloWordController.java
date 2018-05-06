package com.template.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/shiro")
public class HelloWordController {


    @RequestMapping("/u/hello")
    public String success(){
        return "index";
    }



    @RequiresPermissions("list:view")
    @RequestMapping("/u/list")
    public String list(){
        return "list";
    }

    @RequestMapping("/u/user")
    public String user(){
        return "user";
    }

    @RequestMapping("/u/admin")
    public String admin(){
        return "admin";
    }

    @RequestMapping("/login")
    public String hello(){
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam("username") String username,
                                    @RequestParam("password") String password){


        System.out.println("username={}".replace("{}",username));
        System.out.println("password={}".replace("{}",password));
        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            // 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            // rememberme
            token.setRememberMe(true);
            try {
                // 执行登录.
                currentUser.login(token);
            }
            // 所有认证时异常的父类.
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
                System.out.println("登录失败: " + ae.getMessage());
                return "faild"+ae.getMessage();
            }
        }

        return "success";
    }
}
