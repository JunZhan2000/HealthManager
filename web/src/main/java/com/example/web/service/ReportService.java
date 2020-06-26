package com.example.web.service;

import com.example.web.entity.Report;
import com.example.web.mapper.ReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private PictureService pictureService;

    //插入体检记录
    public Integer insertReport(Report report){
        return reportMapper.insertReport(report);
    }

    //用户Id查询体检记录
    public List<Report> queryByUID(Integer uid){
        return reportMapper.queryByUID(uid);
    }

    //根据id删除体检记录
    public Integer deleteByID(Integer reportID){
        //删除记录，为未找到或者未删除，返回0
        if(reportMapper.deleteByID(reportID) == 0){
            return 0;
        }
        //删除记录对应的图片
        pictureService.deleteByReportID(reportID);
        return 1;
    }
}
