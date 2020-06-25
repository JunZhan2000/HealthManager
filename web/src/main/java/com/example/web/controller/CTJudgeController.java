package com.example.web.controller;

import com.example.web.entity.CTJudgeRecord;
import com.example.web.service.CTRecordService;
import com.example.web.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CTJudgeController {
    @Autowired
    private CTRecordService ctRecordService;

    /**
     * description 新建记录
     * @param ctJudgeRecord
     * @return
     */
    @PostMapping("/POST/CTRecord")
    public Response postRecord(@RequestBody() CTJudgeRecord ctJudgeRecord)
    {
        Integer uid = Integer.parseInt((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        ctJudgeRecord.setUid(uid);
        ctJudgeRecord.setType_id(1);
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
