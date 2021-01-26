package com.hugh.cms.controller;


import com.hugh.cms.entity.CrmBanner;
import com.hugh.cms.service.CrmBannerService;
import com.hugh.util.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author hugh
 * @since 2021-01-15
 */
@Api(description = "前台获取Banner接口")
@CrossOrigin
@RestController
@RequestMapping("/cms/banner")
public class CrmBannerController {

    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation("获取所有Banner")
    @GetMapping("getAllBanner")
    public ReturnMessage getAllBanner(){
        List<CrmBanner> list = crmBannerService.selectAllBanner();
        return ReturnMessage.ok().data("banner",list);
    }

}

