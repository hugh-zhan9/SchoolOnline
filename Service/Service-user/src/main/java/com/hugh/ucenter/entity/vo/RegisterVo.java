package com.hugh.ucenter.entity.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by hugh on 2021/1/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "MemberVo",description = "会员注册Vo")
public class RegisterVo {
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "短信验证码")
    private String code;
}
