package com.hugh.vod.controller;

import com.aliyuncs.exceptions.ClientException;
import com.hugh.util.ReturnMessage;
import com.hugh.vod.service.VideoManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by hugh on 2021/1/14
 */
@Api(description = "阿里云视频管理")
@RestController
@RequestMapping("edu/vodControl")
@CrossOrigin
public class VideoManagerController {

    @Autowired
    private VideoManagerService videoManagerService;

    @PostMapping("/upload")
    public ReturnMessage uploadVideoToAliyun(MultipartFile file){
        String videoId = videoManagerService.uploadVideo(file);
        return ReturnMessage.ok().data("videoId",videoId);
    }

    @ApiOperation("通过ID删除视频")
    @DeleteMapping("/delete/{id}")
    public ReturnMessage deleteAliyunVideoById(@PathVariable("id") String id){
        try{
            videoManagerService.deleteVideoById(id);
            return ReturnMessage.ok().message("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return ReturnMessage.ng().message("删除出错");
        }
    }

    @ApiOperation("通过ID集合删除多个视频")
    @DeleteMapping("/deleteVideos")
    public ReturnMessage deleteVideoSByID(@RequestParam("videoIDList") List<String> videoIdList){
        try{
            videoManagerService.deleteAllVideo(videoIdList);
            return ReturnMessage.ok().message("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return ReturnMessage.ng().message("删除出错");
        }
    }

    @ApiOperation("通过视频ID获取视频播放凭证")
    @GetMapping("/getPlayAuth/{id}")
    public ReturnMessage getPlayAuthByID(@PathVariable String id){
        try {
            String videoAuth =videoAuth = videoManagerService.getPlayAuth(id);
            return ReturnMessage.ok().data("videoAuth",videoAuth);
        } catch (ClientException e) {
            e.printStackTrace();
            return ReturnMessage.ng().message("删除出错");
        }

    }
}
