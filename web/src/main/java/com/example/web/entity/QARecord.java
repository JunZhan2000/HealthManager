package com.example.web.entity;

public class QARecord {
    /**
     * QAid
     */
    private int id;

    /**
     * 用户id
     */
    private int uid;

    /**
     * 描述
     */
    private String q_department;

    /**
     * 标题
     */
    private String q_title;


    /**
     * 问题
     */
    private String q_text;

    /**
     * 回答
     */
    private String answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getQ_department() {
        return q_department;
    }

    public void setQ_department(String q_department) {
        this.q_department = q_department;
    }

    public String getQ_title() {
        return q_title;
    }

    public void setQ_title(String q_title) {
        this.q_title = q_title;
    }

    public String getQ_text() {
        return q_text;
    }

    public void setQ_text(String q_text) {
        this.q_text = q_text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


}
