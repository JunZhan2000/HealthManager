package com.example.web.entity;


import lombok.Data;

import java.sql.Timestamp;

@Data
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

    /**
     * 时间
     */
    private Timestamp time;


}
