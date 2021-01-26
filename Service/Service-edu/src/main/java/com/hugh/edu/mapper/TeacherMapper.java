package com.hugh.edu.mapper;

import com.hugh.edu.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author hugh
 * @since 2021-01-05
 */
@Repository
public interface TeacherMapper extends BaseMapper<Teacher> {

}
