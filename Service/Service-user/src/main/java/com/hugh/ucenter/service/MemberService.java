package com.hugh.ucenter.service;

import com.hugh.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hugh.ucenter.entity.vo.RegisterVo;
import com.hugh.util.ReturnMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author hugh
 * @since 2021-01-17
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    ReturnMessage register(RegisterVo registerVo);

    Member getMemberByToken(HttpServletRequest httpServletRequest);

    Member getOpenIdMember(String openid);

    int getTotalUserOneDay(String date);
}
