package com.itheima.dao;

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
}
