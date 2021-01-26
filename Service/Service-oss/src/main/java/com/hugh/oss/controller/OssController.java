package com.hugh.oss.controller;

import com.hugh.oss.service.OssService;
import com.hugh.util.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by hugh on 2021/1/10
 */
@Api(description = "阿里OSS文件上传")
@RestController
@RequestMapping("/edu/fileOss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @ApiOperation(value = "文件上传，返回文件url")
    @PostMapping("upload")
    //这里的这个参数只能设置成file?奇怪了
    public ReturnMessage uploadAvatar(MultipartFile file){
        String url = ossService.uploadAvatar(file);
        return ReturnMessage.ok().data("url",url);
    }
}
