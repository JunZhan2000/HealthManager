package com.example.web.mapper;

import com.example.web.entity.CTJudgeRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CTRecordMapper {
    Integer insertCTRecord(CTJudgeRecord record);

    Integer updateCTRecord(CTJudgeRecord record);

    Integer deleteCTRecord(Integer id);

    CTJudgeRecord getCTRecordFromID(Integer id);

    List<CTJudgeRecord> getCTRecordFromUid(Integer uid);
}
