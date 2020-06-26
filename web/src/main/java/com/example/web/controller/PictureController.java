package com.example.web.controller;

import com.example.web.entity.Picture;
import com.example.web.service.PictureService;
import com.example.web.utils.FileUtil;
import com.example.web.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedList;

@RestController
public class PictureController {
    @Autowired
    private PictureService pictureService;

    //上传单个图片
    @PostMapping("/POST/picture")
    public Response postPicture(@RequestParam(value = "picture") MultipartFile picture){
        String filePath = FileUtil.uploadImg(picture);  //下载文件保存到本地
        Picture pictureRecord = new Picture(1, filePath);
        if(pictureService.insertPicture(pictureRecord) == 0){
            return Response.fail("上传图片失败");
        }
        return Response.success(pictureRecord);

    }

    //上传多个图片
    @PostMapping("/POST/pictures")
    public Response postPictures(@RequestParam(value = "pictures") MultipartFile[] pictures){
        LinkedList<Picture> pictureRecords = new LinkedList<>();
        for(MultipartFile picture : pictures){
            String filePath = FileUtil.uploadImg(picture);  //下载文件保存到本地
            Picture pictureRecord = new Picture(1, filePath);
            pictureService.insertPicture(pictureRecord);  //插入数据库
            pictureRecords.add(pictureRecord);
        }
        return Response.success(pictureRecords);
    }
}
