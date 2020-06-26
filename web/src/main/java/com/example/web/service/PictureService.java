package com.example.web.service;

import com.example.web.entity.Picture;
import com.example.web.mapper.PictureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureService {
    @Autowired
    private PictureMapper pictureMapper;

    //插入图片记录
    public Integer insertPicture(Picture picture){
        return pictureMapper.insertPicture(picture);
    }
}
