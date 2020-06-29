package com.example.web.controller;

import com.example.web.config.JWTAuthenticationFilter;
import com.example.web.entity.CTJudgeRecord;
import com.example.web.entity.CancerJudgeRecord;
import com.example.web.service.CJRecordService;
import com.example.web.utils.FileUtil;
import com.example.web.utils.Response;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.font.MultipleMaster;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@RestController
public class CancerJudgeController {
    @Autowired
    private CJRecordService cjRecordService;

    /**
     * description 新建记录
     * @param picture
     * @return
     */
    @PostMapping("/POST/CJRecord")
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
        /**
         * Ai接口
         */
        Integer answer = 0;
        /**
         * AI接口
         */
        //构造CTRecord类
        CancerJudgeRecord cancerJudgeRecord = new CancerJudgeRecord();
        Integer uid = JWTAuthenticationFilter.getUID();
        cancerJudgeRecord.setUid(uid);
        cancerJudgeRecord.setType_id(2);
        cancerJudgeRecord.setPicture_url(filePath);
        cancerJudgeRecord.setAnswer(answer);
        cancerJudgeRecord.setTime(new Timestamp(System.currentTimeMillis()));
        //插入数据库
        Integer count = cjRecordService.insertCJRecord(cancerJudgeRecord);
        if(count == 0) {
            return Response.fail(cancerJudgeRecord);
        }
        return Response.success(cancerJudgeRecord);
    }

    /**
     * description 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/DELETE/CJRecord")
    public Response deleteRecord(Integer id)
    {
        Integer uid = Integer.parseInt((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Integer count = cjRecordService.deleteCJRecord(id, uid);
        if(count == 0)
        {
            return Response.fail("fail");
        }
        else
            return Response.success("success");
    }

    /**
     * description 修改
     * @param cancerJudgeRecord
     * @return
     */
    @PostMapping("/POST/CJRecord/update")
    public Response updateRecord(@RequestBody() CancerJudgeRecord cancerJudgeRecord)
    {
        Integer uid = Integer.parseInt((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Integer count = 0;
        cancerJudgeRecord.setType_id(2);
        cancerJudgeRecord.setUid(uid);
        count = cjRecordService.updateCJRecord(cancerJudgeRecord);
        if(count == 0)
            return Response.fail(cancerJudgeRecord);
        else
            return Response.success(cancerJudgeRecord);
    }


    @GetMapping("/GET/CJRecord")
    public Response getCJRecord(@RequestParam(name = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize)
    {
        Integer uid = JWTAuthenticationFilter.getUID();
        PageHelper.startPage(pageNum, pageSize);
        List<CancerJudgeRecord> list = cjRecordService.getCJRecordFromUid(uid); //查询记录

        return Response.success(list);
    }

    /**
     * description 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/GET/CJRecord/id")
    public Response getFromID(Integer id)
    {
        Integer uid = Integer.parseInt((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        CancerJudgeRecord cancerJudgeRecord = cjRecordService.getCJRecordFromID(id, uid);
        if(cancerJudgeRecord == null)
            return Response.fail(null);
        else
            return Response.success(cancerJudgeRecord);
    }

    @GetMapping("/GET/CJRecord/uid")
    public Response getFromUid()
    {
        Integer uid = Integer.parseInt((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<CancerJudgeRecord> list = cjRecordService.getCJRecordFromUid(uid);
        if(list == null)
            return Response.fail(null);
        else
            return Response.success(list);
    }
}
