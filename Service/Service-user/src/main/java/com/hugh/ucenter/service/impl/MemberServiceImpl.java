package com.hugh.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hugh.servicebase.exception.SchoolException;
import com.hugh.ucenter.entity.Member;
import com.hugh.ucenter.entity.vo.RegisterVo;
import com.hugh.ucenter.mapper.MemberMapper;
import com.hugh.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hugh.util.JwtUtils;
import com.hugh.util.MD5;
import com.hugh.util.ReturnMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author hugh
 * @since 2021-01-17
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    @ApiOperation("用户登录返回token")
    public String login(Member member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new SchoolException(20001,"密码或手机号为空");
        }
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Member memberQ =baseMapper.selectOne(wrapper);
        if (memberQ == null){
            throw new SchoolException(20001,"手机号错误");
        }
        if (!memberQ.getPassword().equals(MD5.encrypt(password))){
            throw new SchoolException(20001,"密码错误");
        }
        if (memberQ.getIsDisabled()){
            throw new SchoolException(20001,"该用户被禁用");
        }

        // 使用JWT生成Token
        String token = JwtUtils.getJwtToken(memberQ.getId(),memberQ.getNickname());

        return token;
    }

    @Override
    @ApiOperation("用户注册")
    public ReturnMessage register(RegisterVo registerVo) {
        try {
            // 判断所有的字段是否为空
            String voMessageCode = registerVo.getCode();
            String phoneNumber = registerVo.getMobile();
            String password = registerVo.getPassword();
            String nickname = registerVo.getNickname();
            if (voMessageCode.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() || nickname.isEmpty()){
                throw new SchoolException(20001,"注册信息不能为空");
            }

            // 判断验证码
            String messageCode = redisTemplate.opsForValue().get(phoneNumber);
            if (!registerVo.getCode().equals(messageCode)){
                throw new SchoolException(20001,"验证码错误");
            }

            //判断手机号是否重复
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("mobile",phoneNumber);
            int c = baseMapper.selectCount(wrapper);
            if (c>=1){
                throw new SchoolException(20001,"该手机号用户已存在");
            }

            Member member = new Member();
            member.setNickname(nickname);
            member.setMobile(phoneNumber);
            member.setPassword(MD5.encrypt(password));
            member.setIsDisabled(false);
            int count = baseMapper.insert(member);
            if (count!=1){
                return ReturnMessage.ng().message("注册失败");
            }
            return ReturnMessage.ok().message("注册成功");
        }catch (SchoolException e){
            return ReturnMessage.ng().message(e.getExceptionMessage());
        }
    }

    @Override
    @ApiOperation("根据Token获取用户信息")
    public Member getMemberByToken(HttpServletRequest token){

        // 解析Token
        String id = JwtUtils.getMemberIdByJwtToken(token);

        Member member = baseMapper.selectById(id);
        return member;
    }

    @Override
    public Member getOpenIdMember(String openid) {
        QueryWrapper<Member> wrapper = new QueryWrapper();
        wrapper.eq("openid",openid);
        Member me = baseMapper.selectOne(wrapper);
        return me;
    }

    @Override
    public int getTotalUserOneDay(String date) {
        int count = baseMapper.getTotalCountOfUser(date);
        return count;
    }
}
