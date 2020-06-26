package com.example.web.entity;

import lombok.Data;

@Data
public class CTJudgeRecord {
    //记录id
    private Integer id;
    //用户id
    private Integer uid;
    //图片的url
    private String picture_url;
    //回答:0:no,1:yes
    private Integer answer;
    //诊断类型：肺炎：1、癌症：2
    private Integer type_id = 1;
}
