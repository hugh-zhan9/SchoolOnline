package com.hugh.edu.controller;

import com.hugh.util.ReturnMessage;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hugh on 2021/1/8
 */
@Api(description = "模拟登录")
@RestController
@RequestMapping("edu/user")
public class MockLoginController {

    @PostMapping("/login")
    public ReturnMessage login(){
        return ReturnMessage.ok().data("token","admin");
    }

    @RequestMapping("/info")
    public ReturnMessage info(){
        return ReturnMessage.ok().data("roles","[admin]").data("name","admin").data("avatar","https://i.loli.net/2021/01/02/UoclDZMA7EzOIg3.jpg");
    }
}
