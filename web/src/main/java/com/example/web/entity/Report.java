package com.example.web.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Report {
    Integer id;
    Integer uid;
    LocalDateTime time;
    List<String> pictures;
}
