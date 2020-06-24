package com.example.web.entity;

import java.util.List;
import lombok.Data;

@Data
public class Note {
    private Integer id;  //id
    private Integer uid;  //用户id
    private String text;  //标签内容
    private List<Integer> days;  //需要提醒的日子
}
