package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.pojo.Permission;
import com.itheima.service.PermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: Zhangzhengyuan
 * @date: 2020/10/28-18:52
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/permission")
public class PermissionConntroller {
    @Reference
    PermissionService permissionService;

    @RequestMapping("/findAll")
    public Result findAll() {
    List<Permission>  permissions = permissionService.findAll();
    return new Result(true,"查询操作权限成功",permissions);
    }
}
