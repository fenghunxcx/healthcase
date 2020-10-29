package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Role;

import java.util.List;

public interface RoleService {
    List<Role> findRolesAll();

    PageResult findPage(QueryPageBean queryPageBean);

    void add(Role role, Integer[] permissionIds, Integer[] menuIds);

    Role findById(Integer id);

    List<Integer> findPermissionIdsById(Integer id);

    List<Integer> findMenuIdsById(Integer id);

    void edit(Role role, Integer[] permissionIds, Integer[] menuIds);

    void delById(Integer id);
}
