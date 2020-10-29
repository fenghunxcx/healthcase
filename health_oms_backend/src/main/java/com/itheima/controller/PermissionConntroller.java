package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Permission;
import com.itheima.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
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
@Slf4j
public class PermissionConntroller {
    @Reference
    PermissionService permissionService;

    @RequestMapping("/findAll")
    public Result findAll() {
    List<Permission>  permissions = permissionService.findAll();
    return new Result(true,"查询操作权限成功",permissions);
    }

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        log.debug("controller{"+queryPageBean+"}");
        PageResult pageResult = permissionService.findPage(queryPageBean);
        log.debug("权限分页查询成功!!");
        return new Result(true, "权限分页查询成功", pageResult);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission){
        log.debug("Permission~~~~~~~~~==================");
        permissionService.add(permission);
        log.debug("权限添加成功！！！");
        return new Result(true,"权限添加成功");
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        log.debug("findById~~~~~~~~~~~~~~~~"+id);
        Permission permission=permissionService.findById(id);
        log.debug("根据id查询成功~~~~~~~~~");
        return new Result(true,"权限查询成功根据id",permission);
    }


    @RequestMapping("/edit")
    public Result edit(@RequestBody Permission permission){
        log.debug("permissionedit================");
        permissionService.edit(permission);
        log.debug("权限修改成功");
        return new Result(true,"权限编辑成功");
    }

    @RequestMapping("/delById")
    public Result delById(Integer id){
        log.debug("删除----------controller");

        permissionService.delById(id);

        log.debug("删除权限成功-------=-=-=-=-");
        return  new Result( true,"删除权限成功！！");
    }







}
