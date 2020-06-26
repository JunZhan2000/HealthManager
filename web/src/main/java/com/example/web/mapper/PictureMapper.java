package com.example.web.mapper;

import com.example.web.entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PictureMapper {
    //插入图片记录
    Integer insertPicture(Picture picture);

    //根据报告ID查询图片的url
    List<String> queryByReportID(Integer reportID);

    //根据报告ID删除图片
    Integer deleteByReportID(Integer reportID);
}
