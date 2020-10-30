package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PermissionDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.BusinessRuntimeException;
import com.itheima.pojo.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: Zhangzhengyuan
 * @date: 2020/10/28-18:56
 * @Version: 1.0.0
 */
@Service
@Transactional
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionDao permissionDao;
    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        log.debug("service:" + queryPageBean);
        //1. 开始分页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //2. 条件查询
        Page<Permission> page = permissionDao.findByCondition(queryPageBean.getQueryString());
        log.debug("page{"+page+"}");
        //封装PageResult
        return new PageResult(page.getTotal(), page);
    }

    @Override
    public void add(Permission permission) {
        log.debug("service:{"+permission+"}");
        permissionDao.add(permission);
    }

    @Override
    public Permission findById(Integer id) {
        log.debug("permission+++++++++++++");
        return permissionDao.findById(id);
    }

    @Override
    public void edit(Permission permission) {
        log.debug("编辑++++++++++++++++++_+++++++++++");
        permissionDao.edit(permission);
    }

    @Override
    public void delById(Integer id) {
        long count=permissionDao.findCountById(id);
        log.debug("删除++++++++++++++++++");
        if (count>0){
            //权限被角色所关联不能删除
            throw new BusinessRuntimeException("警告！该权限被角色关联，不能删除！！");
        }
        else {
            permissionDao.delById(id);
        }
    }






}
