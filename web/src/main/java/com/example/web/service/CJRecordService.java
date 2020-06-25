package com.example.web.service;

import com.example.web.entity.CancerJudgeRecord;
import com.example.web.mapper.CJRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CJRecordService {
    @Autowired
    private CJRecordMapper cjRecordMapper;

    /**
     * description 新增记录
     * @param cancerJudgeRecord
     * @return
     */
    public Integer insertCJRecord(CancerJudgeRecord cancerJudgeRecord)
    {
        Integer count = cjRecordMapper.insertCJRecord(cancerJudgeRecord);
        return count;
    }

    /**
     * description 修改记录
     * @param cancerJudgeRecord
     * @return
     */
    public Integer updateCJRecord(CancerJudgeRecord cancerJudgeRecord)
    {
        Integer count = cjRecordMapper.updateCJRecord(cancerJudgeRecord);
        return count;
    }

    /**
     * description 删除记录
     * @param id
     * @return
     */
    public Integer deleteCJRecord(Integer id, Integer uid)
    {
        CancerJudgeRecord cancerJudgeRecord = cjRecordMapper.getCJRecordFromID(id);
        Integer count = 0;
        /**
         * 判断uid是否相等
         */
        if(cancerJudgeRecord != null && cancerJudgeRecord.getUid() == uid)
        {
            count = cjRecordMapper.deleteCJRecord(id);
        }
        return count;
    }

    /**
     * description 用id查找record
     * @param id
     * @return
     */
    public CancerJudgeRecord getCJRecordFromID(Integer id, Integer uid)
    {
        CancerJudgeRecord cancerJudgeRecord = cjRecordMapper.getCJRecordFromID(id);
        if(cancerJudgeRecord.getUid() == uid)
            return cancerJudgeRecord;
        else
            return null;
    }

    /**
     * description 通过uid获取records
     * @param uid
     * @return
     */
    public List<CancerJudgeRecord> getCJRecordFromUid(Integer uid)
    {
        List<CancerJudgeRecord> list = cjRecordMapper.getCJRecordFromUid(uid);
        return list;
    }
}
