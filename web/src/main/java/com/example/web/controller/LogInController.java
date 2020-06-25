package com.example.web.controller;

import com.example.web.entity.SMSParameter;
import com.example.web.entity.User;
import com.example.web.service.SystemUserService;
import com.example.web.utils.JwtUtil;
import com.example.web.utils.Response;
import com.example.web.utils.VerifyCode;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
public class LogInController {
    @Autowired
    private SystemUserService userService;

    private SMSParameter smsParameter = new SMSParameter();

    @GetMapping("/hello")
    public String hello(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer uid = Integer.getInteger((String) auth.getPrincipal());

        return "hello";
    }


    @GetMapping("/test")
    public String anonymous(){
        return "这是一个匿名用户可以访问的接口~~~";
    }

    @GetMapping("/sendSms")
    public Response testSendMessage(@RequestParam("phone") String phone){
        if (userService.queryByPhone(phone) != null){
            return Response.fail("该号码已注册");
        }
        try {
            //短信模板中的参数列表
            String[] params = {VerifyCode.createRandom(true,4),"3"};
            SmsSingleSender sender = new SmsSingleSender(smsParameter.getAppId(),
                    smsParameter.getAppKey());

            SmsSingleSenderResult result = sender.sendWithParam("86",
                    phone, smsParameter.getTemplateId(),
                    params, smsParameter.getSmsSign(), "", "");

            System.out.println(result.toString());
            if(result.errMsg.equals("OK")){
                return Response.success(params[0]);
            } else {
                return Response.fail(result.errMsg);
            }
        } catch (HTTPException | JSONException | IOException e) {
            // HTTP 响应码错误
            e.printStackTrace();
            return Response.fail("error");
        } // JSON 解析错误
        // 网络 IO 错误
    }

    @PostMapping("signIn")
    public Response signIn(@RequestBody() User user){
        //先将密码加密再存到数据库中
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if(userService.insertUser(user) != null){
            JSONObject json=new JSONObject();
            User insertedUser = userService.queryByPhone(user.getPhone());
            insertedUser.setPassword(null);
            //创建token
            String token = JwtUtil.generateToken(insertedUser.getId().toString());
            json.put("success", true);
            json.put("code", 1);
            //json.put("result", user1);
            json.put("time", new Date().toString());
            json.put("message", "登陆成功");
            json.put("userInfo", insertedUser);
            json.put(JwtUtil.AUTHORIZATION,token);
            return Response.success(json.toString());
        } else {
            return Response.fail("创建用户失败");
        }
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
    public Response toLogin(@RequestBody User user) {
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
                    return Response.success(json.toString());
                } else {
                    json.put("success", false);
                    json.put("code", -1);
                    json.put("message", "登陆失败,密码错误");
                    return Response.fail(json.toString());
                }
            }else {
                json.put("success", false);
                json.put("code", 0);
                json.put("message", "无此用户信息");
                return Response.fail(json.toString());
            }
        } catch (Exception e) {
            json.put("code", -2);
            json.put("success", false);
            json.put("message", e.getMessage());

            return Response.fail(json.toString());
        }
    }

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
}