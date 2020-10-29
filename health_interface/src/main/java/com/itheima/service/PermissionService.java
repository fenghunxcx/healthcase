package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAll();

    PageResult findPage(QueryPageBean queryPageBean);

    void add(Permission permission);

    Permission findById(Integer id);

    void edit(Permission permission);

    void delById(Integer id);



}
