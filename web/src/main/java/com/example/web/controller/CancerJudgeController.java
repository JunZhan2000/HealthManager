package com.example.web.controller;

import com.example.web.entity.CancerJudgeRecord;
import com.example.web.service.CJRecordService;
import com.example.web.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CancerJudgeController {
    @Autowired
    private CJRecordService cjRecordService;

    /**
     * description 新建记录
     * @param cancerJudgeRecord
     * @return
     */
    @PostMapping("/POST/CJRecord")
    public Response postRecord(@RequestBody() CancerJudgeRecord cancerJudgeRecord)
    {
        Integer uid = Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        cancerJudgeRecord.setUid(uid);
        cancerJudgeRecord.setType_id(2);
        Integer count = cjRecordService.insertCJRecord(cancerJudgeRecord);
        if(count == 0)
        {
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
