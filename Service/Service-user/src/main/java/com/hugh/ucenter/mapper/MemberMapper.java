package com.hugh.ucenter.mapper;

import com.hugh.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author hugh
 * @since 2021-01-17
 */
@Repository
public interface MemberMapper extends BaseMapper<Member> {

    int getTotalCountOfUser(String date);

}
