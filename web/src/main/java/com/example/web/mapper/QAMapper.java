package com.example.web.mapper;


import com.example.web.entity.QARecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QAMapper {
    Integer insertQA(QARecord qaRecord);

    Integer updateQA(QARecord qaRecord);

    Integer deleteQA(Integer id);

    Integer deleteQAAll(Integer uid);

    QARecord getQARecordFromID(Integer id);

    List<QARecord> getQARecordFromUid(Integer uid);

}
