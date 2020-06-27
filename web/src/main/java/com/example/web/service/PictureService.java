package com.example.web.service;

import com.example.web.entity.Picture;
import com.example.web.entity.Report;
import com.example.web.mapper.PictureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureService {
    @Autowired
    private PictureMapper pictureMapper;

    //插入图片记录
    public Integer insertPicture(Picture picture){
        return pictureMapper.insertPicture(picture);
    }

    //根据报告ID查询图片的url
    public List<String> queryByReportID(Integer reportID){
        return pictureMapper.queryByReportID(reportID);
    }

    //根据报告ID删除图片
    public Integer deleteByReportID(Integer reportID){
        return pictureMapper.deleteByReportID(reportID);
    }

    //根据用户id删除图片
    public Integer deleteByUID(Integer uid){
        return pictureMapper.deleteByUID(uid);
    }
}
