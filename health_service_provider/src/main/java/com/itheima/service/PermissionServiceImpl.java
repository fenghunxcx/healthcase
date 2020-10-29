package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.PermissionDao;
import com.itheima.pojo.Permission;
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
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionDao permissionDao;
    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }
}
