package com.hugh.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hugh.edu.entity.Subject;
import com.hugh.edu.entity.SubjectType;
import com.hugh.edu.entity.subjectVo.FirstSubject;
import com.hugh.edu.entity.subjectVo.SecondSubject;
import com.hugh.edu.listener.SubjectListener;
import com.hugh.edu.mapper.SubjectMapper;
import com.hugh.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author hugh
 * @since 2021-01-10
 */
@Service()
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    /**
     * 读取Excel文件中的内容
     * @param file 打开的文件
     * @param subjectService subject服务类
     */
    @Override
    public void saveFile(MultipartFile file,SubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectType.class, new SubjectListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FirstSubject> getAllSubject() {
        QueryWrapper firstWrapper = new QueryWrapper();
        firstWrapper.eq("parent_id","0");
        List<Subject> firstSubjects = baseMapper.selectList(firstWrapper);

        QueryWrapper secondWrapper = new QueryWrapper();
        secondWrapper.ne("parent_id","0");
        List<Subject> secondSubjects = baseMapper.selectList(secondWrapper);

        List<FirstSubject> firstSubjectList = new ArrayList<>();

        for (int i=0;i<firstSubjects.size();i++){
            FirstSubject firstSubject = new FirstSubject();

            Subject subject =firstSubjects.get(i);
            firstSubject.setId(subject.getId());
            firstSubject.setLabel(subject.getTitle());
            // 多行值转换时使用工具类 BeanUtils.copyProperties(subject,firstSubject);

            for(int m=0; m<secondSubjects.size(); m++){
                SecondSubject secondSubject = new SecondSubject();
                Subject subject1 = secondSubjects.get(m);
                secondSubject.setId(subject1.getId());
                secondSubject.setLabel(subject1.getTitle());
                if (subject1.getParentId().equals(subject.getId())){
                    firstSubject.getChildren().add(secondSubject);
                }
            }
            firstSubjectList.add(firstSubject);
        }

        return firstSubjectList;
    }

    @Override
    public List<Subject> getParentSubject() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("parent_id","0");
        List<Subject> subjects = baseMapper.selectList(wrapper);
        return subjects;
    }

}
