package com.example.web.entity;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class Report {
    Integer id;
    Integer uid;
    Date time;
    List<String> pictures;
}
