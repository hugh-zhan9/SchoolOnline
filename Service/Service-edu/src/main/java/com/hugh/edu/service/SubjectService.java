package com.hugh.edu.service;

import com.hugh.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hugh.edu.entity.subjectVo.FirstSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author hugh
 * @since 2021-01-10
 */
public interface SubjectService extends IService<Subject> {

    void saveFile(MultipartFile file,SubjectService subjectService);

    List<FirstSubject> getAllSubject();

    List<Subject> getParentSubject();
}
