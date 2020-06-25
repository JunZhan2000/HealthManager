package com.example.web.controller;

import com.example.web.config.JWTAuthenticationFilter;
import com.example.web.entity.Picture;
import com.example.web.entity.Report;
import com.example.web.service.ReportService;
import com.example.web.utils.FileUtil;
import com.example.web.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping("/POST/report")
    public Response postReport(@RequestParam("file") MultipartFile pictures,
                               @RequestBody Report report){
        report.setUid(JWTAuthenticationFilter.getUID());
        if(reportService.insertReport(report) == null){
            return Response.fail("error");
        }
//        String filePath = FileUtil.uploadImg(pictures);  //下载文件保存到本地
//        Picture picture = new Picture(report.getId(), filePath);

        return Response.success(report);
    }
}
