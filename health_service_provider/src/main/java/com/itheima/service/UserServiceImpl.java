package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.UserDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.BusinessRuntimeException;
import com.itheima.pojo.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	@Override
	public boolean login(String username, String password) {

		if("admin".equals(username) && "123".equals(password)){
			return true;
		}else{
			throw new BusinessRuntimeException("登录失败!!!");
		}
	}

	@Override
	public SysUser findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public PageResult findPage(QueryPageBean queryPageBean) {
		PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
		Page<SysUser> sysUsers = userDao.findPage(queryPageBean.getQueryString());
		return new PageResult(sysUsers.getTotal(),sysUsers);
	}

	@Override
	public void add(SysUser sysUser, Integer[] roleIds) {
		SysUser user = userDao.findByUsername(sysUser.getUsername());
		if (user != null) {
			throw new BusinessRuntimeException("用户名已存在,添加失败");
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encode = encoder.encode(sysUser.getPassword());
		sysUser.setPassword(encode);
		userDao.add(sysUser);
		if (sysUser.getId() != null && roleIds.length > 0 && roleIds != null) {
			setRelations(sysUser.getId(),roleIds);
		}
	}

	@Override
	public List<Integer> findRoleIdsById(Integer id) {
		return userDao.findRoleIdsById(id);
	}

	@Override
	public SysUser findById(Integer id) {
		return userDao.findById(id);
	}

	@Override
	public void edit(SysUser sysUser, Integer[] roleIds) {
		SysUser byUserName = userDao.findByUsername(sysUser.getUsername());
		if (byUserName != null&& byUserName.getId()!=sysUser.getId()) {
			throw new BusinessRuntimeException("用户名已存在,修改失败");
		}
		SysUser byId = userDao.findById(sysUser.getId());
		if (byId.getPassword().equals(sysUser.getPassword())) {
			userDao.edit(sysUser);
		} else {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encode = encoder.encode(sysUser.getPassword());
			sysUser.setPassword(encode);
			userDao.edit(sysUser);
		}
		userDao.delRelation(sysUser.getId());
		setRelations(sysUser.getId(),roleIds);
	}

	@Override
	public void delById(Integer id) {
		userDao.delRelation(id);
		userDao.delById(id);
	}

	private void setRelations(Integer sysUserId, Integer[] roleIds) {
		for (Integer roleId : roleIds) {
			userDao.setRelations(sysUserId,roleId);
		}
	}
}