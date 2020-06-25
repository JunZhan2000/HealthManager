package com.example.web.mapper;

import com.example.web.entity.Report;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportMapper {
    //插入一个体检记录
    Integer insertReport(Report report);
}
