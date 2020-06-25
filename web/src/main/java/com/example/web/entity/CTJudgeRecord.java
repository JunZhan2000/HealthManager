package com.example.web.entity;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }


}
