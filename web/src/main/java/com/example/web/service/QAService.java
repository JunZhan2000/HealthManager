package com.example.web.service;

import com.example.web.entity.QARecord;
import com.example.web.mapper.QAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QAService {
    @Autowired
    private QAMapper qaMapper;

    /**
     * description 插入新数据
     * @param qaRecord
     * @return
     */
    public Integer insertQARecord(QARecord qaRecord)
    {
        Integer count = 0;
        count = qaMapper.insertQA(qaRecord);
        return count;
    }

    /**
     * description 更新数据
     * @param qaRecord
     * @param uid
     * @return
     */
    public Integer updateQARecord(QARecord qaRecord)
    {
        Integer count = qaMapper.updateQA(qaRecord);
        return count;
    }

    /**
     * description 删除数据
     * @param id
     * @param uid
     * @return
     */
    public Integer deleteQA(Integer id, Integer uid)
    {
        QARecord temp = qaMapper.getQARecordFromID(id);
        Integer count = 0;
        if(temp != null && temp.getUid() == uid)
        {
            count = qaMapper.deleteQA(id);
        }
        return count;
    }

    /**
     * description 删除uid的全部数据
     * @param uid
     * @return
     */
    public Integer deleteQAAll(Integer uid)
    {
        Integer count = qaMapper.deleteQAAll(uid);
        return count;
    }

    /**
     * description 获取特定id的数据
     * @param id
     * @return
     */
    public QARecord getQARecordFromID(Integer id, Integer uid)
    {
        QARecord qaRecord = qaMapper.getQARecordFromID(id);
        if(qaRecord != null && qaRecord.getUid() == uid)
        {
            return qaRecord;
        }
        return null;
    }

    /**
     * description 获取uid的全部数据
     * @param uid
     * @return
     */
    public List<QARecord> getQARecordFromUid(Integer uid)
    {
        List<QARecord>list = qaMapper.getQARecordFromUid(uid);
        return list;
    }
}
