package com.example.web.controller;

import com.example.web.entity.QARecord;
import com.example.web.service.QAService;
import com.example.web.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QAController {

    @Autowired
    private QAService qaService;

    @PostMapping("/POST/QARecord")
    public Response insertQA(@RequestBody() QARecord qaRecord)
    {
        //
        //调用py程序获取结果
        //插入记录
        //返回

        Integer uid = Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        qaRecord.setUid(uid);
        Integer count = qaService.insertQARecord(qaRecord);
        if(count == 0)
            return Response.fail(qaRecord);
        else
            return Response.success(qaRecord);
    }

    @PostMapping("/POST/QARecord/update")
    public Response updateQA(@RequestBody() QARecord qaRecord)
    {
        Integer uid = Integer.parseInt((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
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
