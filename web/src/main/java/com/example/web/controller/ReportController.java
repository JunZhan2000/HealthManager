package com.example.web.controller;

import java.util.List;
import com.example.web.config.JWTAuthenticationFilter;
import com.example.web.entity.Picture;
import com.example.web.entity.Report;
import com.example.web.service.PictureService;
import com.example.web.service.ReportService;
import com.example.web.utils.Response;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private PictureService pictureService;

    //上传体检记录（先上传图片）
    @PostMapping("/POST/report")
    public Response postReport(@RequestBody Report report){

        report.setUid(JWTAuthenticationFilter.getUID());
        if(reportService.insertReport(report) == 0){
            return Response.fail("error");
        }
        return Response.success(report);
    }

    //获取一个用户的体检记录
    @GetMapping("/GET/reports")
    public Response getReports(@RequestParam(name = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                               @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize){
        Integer uid = JWTAuthenticationFilter.getUID();

        PageHelper.startPage(pageNum, pageSize);
        List<Report> reports = reportService.queryByUID(uid); //查询记录

        for(Report report: reports){
            report.setPictures(pictureService.queryByReportID(report.getId()));  //查询记录对应的图片
        }

        return Response.success(reports);
    }

    //删除体检记录
    @DeleteMapping("/DELETE/report")
    public Response deleteReport(@RequestParam("reportID") Integer reportID){
        if(reportService.deleteByID(reportID) != 0){
            return Response.success("删除成功");
        } else {
            return Response.fail("删除失败");
        }
    }

    //删除一个用户所有的体检记录
    @DeleteMapping("/DELETE/reports")
    public Response deleteReports(){
        Integer uid = JWTAuthenticationFilter.getUID();
        if(reportService.deleteByUID(uid) != 0){
            return Response.success("删除成功");
        } else {
            return Response.fail("删除失败");
        }
    }
}
