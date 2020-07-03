package com.example.healthapplication.model;

public class JudgeRecord {

    String id;
    String uid;
    String picture_url;
    String answer;
    String type_id;
    String time;


    public JudgeRecord(String id, String uid, String picture_url, String answer, String type_id, String time) {
        this.id = id;
        this.uid = uid;
        this.picture_url = picture_url;
        this.answer = answer;
        this.type_id = type_id;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
