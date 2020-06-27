package com.example.web.mapper;

import com.example.web.entity.Report;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportMapper {
    //插入一个体检记录
    Integer insertReport(Report report);

    //用户id查询体检记录
    List<Report> queryByUID(Integer uid);

    //根据id删除体检记录
    Integer deleteByID(Integer id);

    //根据uid删除体检记录
    Integer deleteByUID(Integer uid);
}
