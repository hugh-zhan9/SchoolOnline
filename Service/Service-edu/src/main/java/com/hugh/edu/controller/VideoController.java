package com.hugh.edu.controller;


import com.hugh.edu.client.VodService;
import com.hugh.edu.entity.Video;
import com.hugh.edu.service.VideoService;
import com.hugh.util.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author hugh
 * @since 2021-01-12
 */
@Api(description = "课程小节管理")
@RestController
@RequestMapping("/edu/video")
public class VideoController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private VodService vodService;


    @ApiOperation(value = "添加小节")
    @PostMapping("addVideo")
    public ReturnMessage saveVideo(@RequestBody Video video){
        boolean flag = videoService.save(video);
        if(flag){
            return ReturnMessage.ok().message("新增成功");
        }else {
            return ReturnMessage.ng().message("新增失败");
        }
    }


    @ApiOperation(value = "删除小节")
    @DeleteMapping("deleteVideo/{id}")
    public ReturnMessage deleteVideo(@PathVariable String id){
        Video video = videoService.getById(id);
        String videoID = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoID)){
            // 调用服务
            ReturnMessage s = vodService.deleteAliyunVideoById(videoID);
            System.out.println(s);
        }
        boolean flag = videoService.removeById(id);
        if(flag){
            return ReturnMessage.ok().message("删除成功");
        }else {
            return ReturnMessage.ng().message("删除失败");
        }
    }


    @ApiOperation(value = "修改小节")
    @PostMapping("updateVideo")
    public ReturnMessage updateVideo(@RequestBody Video video){
        boolean flag = videoService.updateById(video);
        if(flag){
            return ReturnMessage.ok().message("修改成功");
        }else {
            return ReturnMessage.ng().message("修改失败");
        }
    }


    @ApiOperation(value = "通过ID查询小节")
    @GetMapping("getVideo/{id}")
    public ReturnMessage getVideoById(@PathVariable String id){
        Video video = videoService.getById(id);
        return ReturnMessage.ok().data("video",video);
    }

}

