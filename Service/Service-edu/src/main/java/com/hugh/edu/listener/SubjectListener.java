package com.hugh.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hugh.edu.entity.Subject;
import com.hugh.edu.entity.SubjectType;
import com.hugh.edu.service.SubjectService;
import com.hugh.servicebase.exception.SchoolException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 监听器
 *
 * Created by hugh on 2021/1/10
 */
@NoArgsConstructor
@AllArgsConstructor
public class SubjectListener extends AnalysisEventListener<SubjectType> {

    // 手动传值，由于它是通过new创建出来的所以不能作为bean来交给容器管理
    public SubjectService subjectService;


    /**
     * 逐行读取Excel表格中的数据
     * @param subjectType
     * @param analysisContext
     */
    @Override
    public void invoke(SubjectType subjectType, AnalysisContext analysisContext) {
        if (subjectType==null){
            throw new SchoolException(200001,"文件数据为空");
        }

        Subject oneSubject = this.existOneSubject(subjectService, subjectType.getOneSubjectName());
        if (oneSubject==null){
            // 该一级分类不存在，进行添加
            oneSubject=new Subject();
            oneSubject.setTitle(subjectType.getOneSubjectName());
            oneSubject.setParentId("0");
            subjectService.save(oneSubject);
        }

        // 获取一级分类的id值
        String pid = oneSubject.getId();

        Subject twoSubject = this.existTwoSubject(subjectService, subjectType.getTwoSubjectName(),pid);
        if (twoSubject==null){
            twoSubject = new Subject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectType.getTwoSubjectName());
            subjectService.save(twoSubject);
        }

    }

    /**
     * 判断一级分类是否存在
     * @param subjectService SubjectService服务类
     * @param subjectName   一级分类名称
     * @return  分类对象
     */
    public Subject existOneSubject(SubjectService subjectService, String subjectName){
        QueryWrapper<Subject> wrapper = new QueryWrapper<Subject>();
        wrapper.eq("title",subjectName);
        wrapper.eq("parent_id","0");
        Subject subject = subjectService.getOne(wrapper);
        return subject;
    }

    /**
     * 判断二级分类是否存在
     * @param subjectService    SubjectService服务类
     * @param subjectName   二级分类名称
     * @param parentId  一级分类的ID
     * @return  分类对象
     */
    public Subject existTwoSubject(SubjectService subjectService, String subjectName,String parentId){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",subjectName);
        wrapper.eq("parent_id",parentId);
        Subject subject = subjectService.getOne(wrapper);
        return subject;
    }



    @Override
    public void invokeHeadMap(Map headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
