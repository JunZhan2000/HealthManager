package com.example.web.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

public class FileUtil {
    @Value("${upload-path}")
    private static String uploadPath;

    /**
     * 静态方法，下载文件到本地
     * @param file 要下载的文件
     * @return 下载成功则返回静态资源地址，失败则返回失败提示
     */
    public static String uploadImg(MultipartFile file){
        if(file.isEmpty()){
            return "上传失败";
        }
        String filePath = new String(uploadPath);//图片上传位置
        System.out.println(filePath );
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();  //生成图片名
        File dest = new File(filePath+ fileName);//上传图片路径

        if(!new File(filePath).exists()){
            //如果目录不存在则新建一个
            new File(filePath).mkdirs();
        }
        try {
            file.transferTo(dest);  //保存文件
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return  "/upload/" + fileName;
    }
}
