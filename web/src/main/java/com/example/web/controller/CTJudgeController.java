package com.example.web.controller;

import com.example.web.config.JWTAuthenticationFilter;
import com.example.web.entity.CTJudgeRecord;
import com.example.web.entity.Picture;
import com.example.web.entity.Report;
import com.example.web.service.CTRecordService;
import com.example.web.utils.FileUtil;
import com.example.web.utils.HttpUtil;
import com.example.web.utils.Response;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
public class CTJudgeController {
    @Autowired
    private CTRecordService ctRecordService;


    @PostMapping("/POST/CTRecord")
    public Response postRecord(@RequestParam(value = "picture") MultipartFile picture)
    {
        // 将图片文件转成BufferedImage格式
        BufferedImage image = null;
        try {
            FileInputStream in = (FileInputStream)picture.getInputStream();
            image = ImageIO.read(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将文件保存到本地
        String filePath = FileUtil.uploadImg(picture);  //下载文件保存到本地
        String parameterName = "picture_location";
        String parameterValue = "C:" + filePath;

        String url = HttpUtil.generateGetUrl("http://localhost:8000/AI_API/CT_judge",
                parameterName, parameterValue);
//        Integer answer = Integer.parseInt(HttpUtil.get(url));
        System.out.println(HttpUtil.get(url));
        Integer answer = 1;

        //构造CTRecord类
        CTJudgeRecord ctJudgeRecord = new CTJudgeRecord();
        Integer uid = JWTAuthenticationFilter.getUID();
        ctJudgeRecord.setUid(uid);
        ctJudgeRecord.setType_id(1);
        ctJudgeRecord.setPicture_url(filePath);
        ctJudgeRecord.setAnswer(answer);
        ctJudgeRecord.setTime(new Timestamp(System.currentTimeMillis()));
        //插入数据库
        Integer count = ctRecordService.insertCTRecord(ctJudgeRecord);
        if(count == 0)
        {
            return Response.fail(ctJudgeRecord);
        }
        return Response.success(ctJudgeRecord);
    }

    /**
     * description 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/DELETE/CTRecord")
    public Response deleteRecord(Integer id)
    {
        Integer uid = Integer.parseInt((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Integer count = ctRecordService.deleteCTRecord(id, uid);
        if(count == 0)
        {
            return Response.fail("fail");
        }
        else
            return Response.success("success");
    }

    /**
     * description 修改
     * @param ctJudgeRecord
     * @return
     */
    @PostMapping("/POST/CTRecord/update")
    public Response updateRecord(@RequestBody() CTJudgeRecord ctJudgeRecord)
    {
        Integer uid = Integer.parseInt((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Integer count = 0;
        ctJudgeRecord.setUid(uid);
        ctJudgeRecord.setType_id(1);
        count = ctRecordService.updateCTRecord(ctJudgeRecord);
        if(count == 0)
            return Response.fail(ctJudgeRecord);
        else
            return Response.success(ctJudgeRecord);
    }

    /**
     * description 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/GET/CTRecord/id")
    public Response getFromID(Integer id)
    {
        Integer uid = Integer.parseInt((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        CTJudgeRecord ctJudgeRecord = ctRecordService.getCTRecordFromID(id, uid);
        if(ctJudgeRecord == null)
            return Response.fail(null);
        else
            return Response.success(ctJudgeRecord);
    }

    @GetMapping("/GET/CTRecord")
    public Response getCTRecord(@RequestParam(name = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize)
    {
        Integer uid = JWTAuthenticationFilter.getUID();
        PageHelper.startPage(pageNum, pageSize);
        List<CTJudgeRecord> list = ctRecordService.getCTRecordFromUid(uid); //查询记录

        return Response.success(list);
    }

    @GetMapping("/GET/CTRecord/uid")
    public Response getFromUid()
    {
        Integer uid = Integer.parseInt((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<CTJudgeRecord> list = ctRecordService.getCTRecordFromUid(uid);
        if(list == null)
            return Response.fail(null);
        else
            return Response.success(list);
    }
}
