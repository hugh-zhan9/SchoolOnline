package com.hugh.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hugh.edu.entity.Chapter;
import com.hugh.edu.entity.Video;
import com.hugh.edu.entity.vo.ChapterVo;
import com.hugh.edu.entity.vo.VideoVo;
import com.hugh.edu.mapper.ChapterMapper;
import com.hugh.edu.mapper.VideoMapper;
import com.hugh.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hugh.edu.service.VideoService;
import com.hugh.servicebase.exception.SchoolException;
import com.hugh.util.ReturnMessage;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.annotation.WebFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author hugh
 * @since 2021-01-12
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    VideoService videoService;

    @Override
    public List<ChapterVo> getChaptersByCourseId(String id) {
        List<ChapterVo> chapterVos = new ArrayList<>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("course_id",id);

        List<Chapter> characters = baseMapper.selectList(wrapper);
        List<Video> videos = videoMapper.selectList(wrapper);

        for (Chapter chapter: characters){
            ChapterVo chapterVo = new ChapterVo();
            chapterVo.setId(chapter.getId());
            chapterVo.setLabel(chapter.getTitle());
            chapterVos.add(chapterVo);

            for (Video video : videos){
                VideoVo videoVo = new VideoVo();
                if (chapter.getId().equals(video.getChapterId())){
                    videoVo.setId(video.getId());
                    videoVo.setLabel(video.getTitle());
                    videoVo.setVideoId(video.getVideoSourceId());
                    chapterVo.getChildren().add(videoVo);
                }
            }
        }

        return chapterVos;
    }

    @Override
    public Integer saveChapter(Chapter chapter) {
        Integer count = baseMapper.insert(chapter);
        return count;
    }

    @Override
    public boolean deleteChapterById(String id) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        Integer count = videoService.count(wrapper);
        if (count!=0){
            throw new SchoolException(20001,"该章节下还有子小节，请删除小节后尝试删除本章节");
        }else {
            int result = baseMapper.deleteById(id);
            return result>0;
        }
    }

    @Override
    public Integer updateChapter(Chapter chapter) {
        Integer count = baseMapper.updateById(chapter);
        return count;
    }

    @Override
    public Chapter getChapterById(String id) {
        Chapter chapter =baseMapper.selectById(id);
        return chapter;
    }

    @Override
    public void deleteByCourseId(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("course_Id",id);
        int count = baseMapper.delete(wrapper);
    }
}
