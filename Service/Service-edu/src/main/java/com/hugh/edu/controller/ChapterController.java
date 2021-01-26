package com.hugh.edu.controller;


import com.hugh.edu.entity.Chapter;
import com.hugh.edu.entity.vo.ChapterVo;
import com.hugh.edu.service.ChapterService;
import com.hugh.util.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author hugh
 * @since 2021-01-12
 */
@Api(description = "课程章节管理")
@RestController
@RequestMapping("/edu/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @ApiOperation(value = "通过课程id获取指定课程的所有章节")
    @GetMapping("getChapters/{id}")
    public ReturnMessage getChaptersByCourseId(@ApiParam(name = "id", value = "课程ID", required = true) @PathVariable String id){
        List<ChapterVo> list =chapterService.getChaptersByCourseId(id);
        return ReturnMessage.ok().data("chapter",list);
    }

    @ApiOperation(value = "添加章节")
    @PostMapping("saveChapter/{id}")
    public ReturnMessage saveChapter(@PathVariable String id, @RequestBody Chapter chapter){
        chapter.setCourseId(id);
        Integer count = chapterService.saveChapter(chapter);
        if (count==1){
            return ReturnMessage.ok().message("保存成功");
        }else {
            return ReturnMessage.ng();
        }
    }

    @ApiOperation(value = "删除章节")
    @DeleteMapping("deleteChapter/{id}")
    public ReturnMessage deleteChapterById(@PathVariable String id){
        boolean flag = chapterService.deleteChapterById(id);
        if (flag) {
            return ReturnMessage.ok().message("删除成功");
        }else{
            return ReturnMessage.ng().message("删除失败");
        }
    }


    @ApiOperation(value = "修改章节")
    @PostMapping("updateChapter")
    public ReturnMessage updateChapter(@RequestBody Chapter chapter){
        Integer count = chapterService.updateChapter(chapter);
        if (count==1) {
            return ReturnMessage.ok().message("修改成功");
        }else{
            return ReturnMessage.ng().message("修改失败");
        }
    }

    @ApiOperation(value = "通过Id获取章节")
    @GetMapping("getChapter/{id}")
    public ReturnMessage getChapterById(@PathVariable String id){
        Chapter chapter = chapterService.getChapterById(id);
        if (chapter!=null){
            return ReturnMessage.ok().data("chapter",chapter);
        }else
            return ReturnMessage.ng().message("指定章节不存在");
    }


}

