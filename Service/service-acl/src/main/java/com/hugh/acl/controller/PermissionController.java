package com.hugh.acl.controller;


import com.hugh.acl.entity.Permission;
import com.hugh.acl.service.PermissionService;
import com.hugh.util.ReturnMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/permission")
//@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public ReturnMessage indexAllPermission() {
        List<Permission> list =  permissionService.queryAllMenu();
        return ReturnMessage.ok().data("children",list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public ReturnMessage remove(@PathVariable String id) {
        permissionService.removeChildById(id);
        return ReturnMessage.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public ReturnMessage doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
        return ReturnMessage.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public ReturnMessage toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return ReturnMessage.ok().data("children", list);
    }



    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public ReturnMessage save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return ReturnMessage.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public ReturnMessage updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return ReturnMessage.ok();
    }

}

