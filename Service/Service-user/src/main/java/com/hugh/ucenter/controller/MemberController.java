package com.hugh.ucenter.controller;


import com.hugh.ucenter.entity.Member;
import com.hugh.ucenter.entity.vo.RegisterVo;
import com.hugh.ucenter.service.MemberService;
import com.hugh.util.ReturnMessage;
import com.hugh.util.vo.MemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author hugh
 * @since 2021-01-17
 */
@Api(description = "用户管理接口")
@CrossOrigin
@RestController
@RequestMapping("/api/ucenter/")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation("用户登录")
    @PostMapping("login")
    public ReturnMessage login(@RequestBody Member member){
        String token = memberService.login(member);
        return ReturnMessage.ok().data("token",token);
    }

    @ApiOperation("用户注册")
    @PostMapping("register")
    public ReturnMessage register(@RequestBody RegisterVo registerVo){
        ReturnMessage message =  memberService.register(registerVo);
        return message;
    }

    @ApiOperation("用户登录或获取用户信息接口")
    @GetMapping("getMemberInfo")
    public ReturnMessage getMember(HttpServletRequest httpServletRequest){
        Member member = memberService.getMemberByToken(httpServletRequest);
        return ReturnMessage.ok().data("memberInfo",member);
    }

    @ApiOperation("根据用户ID获取用户信息")
    @GetMapping("getUserInfo/{id}")
    public MemberVo getUserInfo(@PathVariable("id") String id){
        Member member = memberService.getById(id);
        MemberVo memberVo = new MemberVo();
        BeanUtils.copyProperties(member,memberVo);
        return memberVo;
    }

    @ApiOperation("获取某天注册用户的总人数")
    @GetMapping("getTotalUser/{date}")
    public int getTotalUser(@PathVariable("date") String date){
        int count = memberService.getTotalUserOneDay(date);
        return count;
    }

}

