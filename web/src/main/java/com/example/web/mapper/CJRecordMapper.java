package com.example.web.mapper;


import com.example.web.entity.CancerJudgeRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CJRecordMapper {
    Integer insertCJRecord(CancerJudgeRecord record);

    Integer updateCJRecord(CancerJudgeRecord record);

    Integer deleteCJRecord(Integer id);

    CancerJudgeRecord getCJRecordFromID(Integer id);

    List<CancerJudgeRecord> getCJRecordFromUid(Integer uid);
}
