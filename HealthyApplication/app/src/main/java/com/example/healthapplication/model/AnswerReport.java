package com.example.healthapplication.model;

public class AnswerReport {
    String id;
    String uid;
    String q_department;
    String q_title;
    String q_text;
    String time;
    String answer;

           public AnswerReport(String id, String uid, String q_department, String q_title, String q_text, String time, String answer) {
               this.id = id;
               this.uid = uid;
               this.q_department = q_department;
               this.q_title = q_title;
               this.q_text = q_text;
               this.time = time;
               this.answer = answer;
           }


           public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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
       }
