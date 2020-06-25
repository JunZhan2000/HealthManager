package com.example.web.service;

import com.example.web.entity.Report;
import com.example.web.mapper.ReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired
    private ReportMapper reportMapper;

    //插入体检记录
    public Integer insertReport(Report report){
        return reportMapper.insertReport(report);
    }
}
