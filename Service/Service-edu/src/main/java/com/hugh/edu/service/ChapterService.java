package com.hugh.edu.service;

import com.hugh.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hugh.edu.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hugh
 * @since 2021-01-12
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChaptersByCourseId(String id);

    Integer saveChapter(Chapter chapter);

    boolean deleteChapterById(String id);

    Integer updateChapter(Chapter chapter);

    Chapter getChapterById(String id);

    void deleteByCourseId(String id);
}
