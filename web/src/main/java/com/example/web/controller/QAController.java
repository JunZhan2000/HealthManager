package com.example.web.controller;

import com.example.web.config.JWTAuthenticationFilter;
import com.example.web.entity.CTJudgeRecord;
import com.example.web.entity.QARecord;
import com.example.web.service.QAService;
import com.example.web.utils.Response;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class QAController {

    @Autowired
    private QAService qaService;

    @PostMapping("/POST/QARecord")
    public Response insertQA(@RequestBody() QARecord qaRecord)
    {
        String department = qaRecord.getQ_department();
        String title = qaRecord.getQ_title();
        String text = qaRecord.getQ_text();
        //调用py程序获取结果

        String answer = "暂时的默认结果";
        //插入记录
        qaRecord.setAnswer(answer);
        Integer uid = Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        qaRecord.setUid(uid);
        qaRecord.setTime(new Timestamp(System.currentTimeMillis()));
        Integer count = qaService.insertQARecord(qaRecord);
        if(count == 0)
            return Response.fail(qaRecord);
        else
            return Response.success(qaRecord);
    }

    @PostMapping("/POST/QARecord/update")
    public Response updateQA(@RequestBody() QARecord qaRecord)
    {
        Integer uid = JWTAuthenticationFilter.getUID();
        qaRecord.setUid(uid);
        Integer count = qaService.updateQARecord(qaRecord);
        if(count == 0)
            return Response.fail(qaRecord);
        else
            return Response.success(qaRecord);
    }
    @DeleteMapping("/DELETE/QARecord")
    public Response deleteQARecord(Integer id)
    {
        Integer uid = Integer.parseInt((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Integer count = qaService.deleteQA(id, uid);
        if(count == 0)
            return Response.fail(count);
        else
            return Response.success(count);
    }

    @DeleteMapping("/DELETE/QARecord/uid")
    public Response deleteQAAll()
    {
        Integer uid = Integer.parseInt((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Integer count = qaService.deleteQAAll(uid);
        if(count == 0)
            return Response.fail(count);
        else
            return Response.success(count);
    }

    @GetMapping("/GET/QARecord")
    public Response getQARecord(@RequestParam(name = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize)
    {
        Integer uid = JWTAuthenticationFilter.getUID();
        PageHelper.startPage(pageNum, pageSize);
        List<QARecord> list = qaService.getQARecordFromUid(uid); //查询记录
        return Response.success(list);
    }

    @GetMapping("/GET/QARecord/id")
    public Response getQAFromID(Integer id)
    {
        Integer uid = Integer.parseInt((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        QARecord qaRecord = qaService.getQARecordFromID(id, uid);
        if(qaRecord == null)
            return Response.fail(qaRecord);
        else
            return Response.success(qaRecord);
    }

    @GetMapping("/GET/QARecord/uid")
    public Response getQAFromUid()
    {
        Integer uid = Integer.parseInt((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<QARecord> list = qaService.getQARecordFromUid(uid);
        if(list.isEmpty())
            return Response.fail(list);
        else
            return Response.success(list);
    }
}
