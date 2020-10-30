package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public interface PermissionDao {
    Set<Permission> findPermissionsByRoleId(Integer roleId);
    @Select("select * from t_permission")
    List<Permission> findAll();

    Page<Permission> findByCondition(String queryString);

    void add(Permission permission);


    Permission findById(Integer id);

    void edit(Permission permission);


    long findCountById(Integer id);

    void delById(Integer id);






}
