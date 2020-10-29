package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.SysUser;

import java.util.List;

public interface UserService {
	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密 码
	 * @return
	 */
	boolean login(String username,String password);

	/**
	 * 根据用户名获取用户信息
	 * @param username
	 * @return
	 */
	SysUser findByUsername(String username);
    PageResult findPage(QueryPageBean queryPageBean);

    void add(SysUser sysUser, Integer[] roleIds);

    List<Integer> findRoleIdsById(Integer id);

    SysUser findById(Integer id);

    void edit(SysUser sysUser, Integer[] roleIds);

    void delById(Integer id);

}