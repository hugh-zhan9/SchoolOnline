package com.hugh.cms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hugh.cms.entity.CrmBanner;
import com.hugh.cms.service.CrmBannerService;
import com.hugh.util.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hugh on 2021/1/15
 */
@Api(description = "后台管理Banner接口")
@CrossOrigin
@RestController
@RequestMapping("/cms/bannerAdmin")
public class CrmBannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation("分页查询Banner")
    @GetMapping("getBanner/{page}/{limit}")
    public ReturnMessage getBannerByPage(@PathVariable("page") long page,@PathVariable("limit") long limit){
        Page<CrmBanner> pageBanner = new Page();
        crmBannerService.page(pageBanner,null);
        List<CrmBanner> bannersList = pageBanner.getRecords();
        long total = pageBanner.getTotal();
        return ReturnMessage.ok().data("list",bannersList).data("total",total);
    }

    @ApiOperation("通过ID获取Banner")
    @GetMapping("getBanner/{id}")
    public ReturnMessage getBannerById(@PathVariable("id") String id){
        CrmBanner banner =crmBannerService.getById(id);
        return ReturnMessage.ok().data("banner",banner);
    }

    @ApiOperation("新增Banner")
    @PostMapping("saveBanner")
    public ReturnMessage saveBanner(@RequestBody CrmBanner banner){
        crmBannerService.save(banner);
        return ReturnMessage.ok().message("保存成功");
    }

    @ApiOperation("修改Banner")
    @PostMapping("updateBanner")
    public ReturnMessage updateBannerById(@RequestBody CrmBanner banner){
        crmBannerService.updateById(banner);
        return ReturnMessage.ok().message("修改成功");
    }

    @ApiOperation("根据ID删除Banner")
    @DeleteMapping("deleteBanner/{id}")
    public ReturnMessage deleteBannerById(@PathVariable("id") String id){
        Boolean flag = crmBannerService.removeById(id);
        if (flag){
            return ReturnMessage.ok().message("保存成功");
        }else{
            return ReturnMessage.ng().message("删除失败");
        }
    }
}
