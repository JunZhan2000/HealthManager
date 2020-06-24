package com.example.web.controller;

import com.example.web.entity.User;
import com.example.web.service.SystemUserService;
import com.example.web.utils.JwtUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class LogInController {
    @Autowired
    private SystemUserService userService;


    @GetMapping("login/{status}")
    public String login(@PathVariable String status) {
        System.out.println(status);
        if("auth".equals(status)){
            return "没有登录";
        }
        if("fail".equals(status)){
            return "登录失败";
        }
        if("success".equals(status)){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return JSONObject.fromObject(auth).toString();
        }
        if("logout".equals(status)){
            return "注销成功";
        }
        return "";
    }


    @GetMapping("anonymous")
    public String anonymous(){
        return "这是一个匿名用户可以访问的接口~~~";
    }

    /**
     * 方法名：作用：登陆校验密码
     * 输入 username password  用户名，密码
     * 输出：code: 状态码   1 为认证成功 0 为用户不存在 -1 为密码不一致 -2 表示程序错误
     *       success:  true or false 执行成功或失败
     *       result：只在认证成功时返回，包含用户的全部信息
     *       messsage:
     */
    @PostMapping("/login")
    public String toLogin(User user) {
        System.out.println(user);
        JSONObject json=new JSONObject();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        try {
            User userInfo = userService.queryByPhone(user.getPhone());
            if (userInfo != null) {
                String dbPassWord = userInfo.getPassword();
                if (bCryptPasswordEncoder.matches(user.getPassword(),dbPassWord)) {
                    //创建token
                    String token = JwtUtil.generateToken(userInfo.getId().toString());
                    json.put("success", true);
                    json.put("code", 1);
                    //json.put("result", user1);
                    json.put("time", new Date().toString());
                    json.put("message", "登陆成功");
                    json.put(JwtUtil.AUTHORIZATION,token);
                } else {
                    json.put("success", false);
                    json.put("code", -1);
                    json.put("message", "登陆失败,密码错误");
                }
            }else {
                json.put("success", false);
                json.put("code", 0);
                json.put("message", "无此用户信息");
            }
        } catch (Exception e) {
            json.put("code", -2);
            json.put("success", false);
            json.put("message", e.getMessage());

        }

        return json.toString();
    }

}