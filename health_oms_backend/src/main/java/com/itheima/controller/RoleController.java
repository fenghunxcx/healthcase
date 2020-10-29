package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: Zhangzhengyuan
 * @date: 2020/10/26-21:28
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Reference
    RoleService roleService;
@RequestMapping("/add")
public Result add(@RequestBody Role role, Integer[] permissionIds, Integer[] menuIds) {
    roleService.add(role,permissionIds,menuIds);
    return new Result(true,"新增角色成功");
}
    @RequestMapping("/findAll")
    public Result findRolesAll() {
    List<Role> roles = roleService.findRolesAll();
    return new Result(true,"查询角色成功",roles);
    }
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
     PageResult pageResult =  roleService.findPage(queryPageBean);
     return new Result(true,"查询角色成功",pageResult);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
    Role role = roleService.findById(id);
    return new Result(true,"查询角色成功",role);
    }

    @RequestMapping("/findPermissionIdsById")
    public Result findPermissionIdsById(Integer id) {
       List<Integer> permissionIds = roleService.findPermissionIdsById(id);
        return new Result(true,"查询角色操作权限成功",permissionIds);
    }

    @RequestMapping("/findMenuIdsById")
    public Result findMenuIdsById(Integer id) {
        List<Integer> permissionIds = roleService.findMenuIdsById(id);
        return new Result(true,"查询角色菜单权限成功",permissionIds);
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody Role role, Integer[] permissionIds, Integer[] menuIds) {
      roleService.edit(role,permissionIds,menuIds);
      return new Result(true,"角色修改成功");
    }

    @RequestMapping("/delById")
    public Result delById(Integer id) {
    roleService.delById(id);
    return  new Result(true,"删除角色成功");
    }
}
