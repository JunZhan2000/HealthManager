package com.example.web.mapper;

import com.example.web.entity.Picture;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PictureMapper {
    //插入图片记录
    Integer insertPicture(Picture picture);

}
