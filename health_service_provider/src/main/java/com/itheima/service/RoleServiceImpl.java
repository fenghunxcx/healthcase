package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.RoleDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.BusinessRuntimeException;
import com.itheima.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: Zhangzhengyuan
 * @date: 2020/10/26-21:32
 * @Version: 1.0.0
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;
    @Override
    public List<Role> findRolesAll() {
        return roleDao.findRolesAll();
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Role> roles = roleDao.findPage(queryPageBean.getQueryString());
        return new PageResult(roles.getTotal(),roles);
    }

    @Override
    public void add(Role role, Integer[] permissionIds, Integer[] menuIds) {
        roleDao.add(role);
        if (role.getId() != null && permissionIds != null & permissionIds.length > 0) {
            setPermissionRelations(role.getId(),permissionIds);
        }
        if (role.getId() != null && menuIds != null & menuIds.length > 0) {
            setMenuRelations(role.getId(),menuIds);
        }
    }

    @Override
    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    @Override
    public List<Integer> findPermissionIdsById(Integer roleId) {
        return roleDao.findPermissionIdsById(roleId);
    }

    @Override
    public List<Integer> findMenuIdsById(Integer roleId) {
        return roleDao.findMenuIdsById(roleId);
    }

    @Override
    public void edit(Role role, Integer[] permissionIds, Integer[] menuIds) {
        roleDao.edit(role);
        roleDao.delPermissionRelation(role.getId());
        roleDao.delMenuRelation(role.getId());
        if (role.getId() != null && permissionIds != null & permissionIds.length > 0) {
            setPermissionRelations(role.getId(),permissionIds);
        }
        if (role.getId() != null && menuIds != null & menuIds.length > 0) {
            setMenuRelations(role.getId(),menuIds);
        }
    }

    @Override
    public void delById(Integer roleId) {
     int  count = roleDao.findUserById(roleId);
        if (count > 0) {
            throw new BusinessRuntimeException("当前角色被用户关联，无法删除！！！");
        }
        roleDao.delMenuRelation(roleId);
        roleDao.delPermissionRelation(roleId);
        roleDao.delById(roleId);
    }

    private void setMenuRelations(Integer roleId, Integer[] menuIds) {
        for (Integer menuId : menuIds) {
            roleDao.setMenuRelations(roleId,menuId);
        }
    }

    private void setPermissionRelations(Integer roleId, Integer[] permissionIds) {
        for (Integer permissionId : permissionIds) {
            roleDao.setPermissionRelations(roleId,permissionId);
        }
    }
}
