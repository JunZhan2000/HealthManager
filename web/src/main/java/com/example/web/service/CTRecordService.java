package com.example.web.service;

import com.example.web.entity.CTJudgeRecord;
import com.example.web.mapper.CTRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CTRecordService {
    @Autowired
    private CTRecordMapper ctRecordMapper;

    /**
     * description 新增记录
     * @param ctJudgeRecord
     * @return
     */
    public Integer insertCTRecord(CTJudgeRecord ctJudgeRecord)
    {
        Integer count = ctRecordMapper.insertCTRecord(ctJudgeRecord);
        return count;
    }

    /**
     * description 修改记录
     * @param ctJudgeRecord
     * @return
     */
    public Integer updateCTRecord(CTJudgeRecord ctJudgeRecord)
    {
        Integer count = ctRecordMapper.updateCTRecord(ctJudgeRecord);
        return count;
    }

    /**
     * description 删除记录
     * @param id
     * @return
     */
    public Integer deleteCTRecord(Integer id, Integer uid)
    {
        CTJudgeRecord ctJudgeRecord = ctRecordMapper.getCTRecordFromID(id);
        Integer count = 0;
        /**
         * 判断uid是否相等
         */
        if(ctJudgeRecord != null && ctJudgeRecord.getUid() == uid)
        {
            count = ctRecordMapper.deleteCTRecord(id);
        }
        return count;
    }

    /**
     * description 用id查找record
     * @param id
     * @return
     */
    public CTJudgeRecord getCTRecordFromID(Integer id, Integer uid)
    {
        CTJudgeRecord ctJudgeRecord = ctRecordMapper.getCTRecordFromID(id);
        if(ctJudgeRecord.getUid() == uid)
            return ctJudgeRecord;
        else
            return null;
    }

    /**
     * description 通过uid获取records
     * @param uid
     * @return
     */
    public List<CTJudgeRecord> getCTRecordFromUid(Integer uid)
    {
        List<CTJudgeRecord> list = ctRecordMapper.getCTRecordFromUid(uid);
        return list;
    }
}
