package com.example.web.entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Report {
    Integer id;
    Integer uid;
    LocalDate time;
    List<String> pictures;
}
