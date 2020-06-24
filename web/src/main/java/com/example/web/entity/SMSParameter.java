package com.example.web.entity;

import lombok.Data;

/**
 *  封装发送短信所需的参数
 */
@Data
public class SMSParameter {
    // 短信应用 SDK AppID，SDK AppID 以1400开头
    private int appId = 1400391091;
    // 短信应用SDK AppKey
    private String appKey = "af4e530a7e62752a13e0c32493153120";
    // 需要发送短信的手机号码，可以定义多个手机号码
//    private String[] phoneNumbers = {"18046637757"};
    // 短信模板ID，需要在短信控制台中申请，我们查看自己的短信模板ID即可
    private int templateId = 645358;
    // 签名，签名参数使用的是`签名内容`，而不是`签名ID`，真实的签名需要在短信控制台申请，这里按自己的来修改就好
    private String smsSign = "我即果壳";
}