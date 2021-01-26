package com.hugh.acl.controller;

import com.alibaba.fastjson.JSONObject;
import com.hugh.acl.entity.Permission;
import com.hugh.acl.service.IndexService;
import com.hugh.acl.service.PermissionService;
import com.hugh.util.ReturnMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/index")
//@CrossOrigin
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 根据token获取用户信息
     */
    @GetMapping("info")
    public ReturnMessage info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return ReturnMessage.ok().data(userInfo);
    }

    /**
     * 获取菜单
     * @return
     */
    @GetMapping("menu")
    public ReturnMessage getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return ReturnMessage.ok().data("permissionList", permissionList);
    }

    @PostMapping("logout")
    public ReturnMessage logout(){
        return ReturnMessage.ok();
    }

}
