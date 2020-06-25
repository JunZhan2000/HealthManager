package com.example.web.entity;

import lombok.Data;

@Data
public class Picture {
    Integer id;
    String url;
    Integer report_id;

    public Picture(Integer report_id, String url){
        this.report_id = report_id;
        this.url = url;
    }
}
