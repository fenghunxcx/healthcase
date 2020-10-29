package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.SysUser;
import com.itheima.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	@Reference
	UserService userService;
	@RequestMapping("/delById")
	public Result delById(Integer id) {
		userService.delById(id);
		return  new Result(true,"删除用户成功");
	}
	@RequestMapping("/edit")
	public Result edit(@RequestBody SysUser sysUser , Integer[] roleIds) {
		userService.edit(sysUser,roleIds);
		return new Result(true,"修改用户成功");
	}
	@RequestMapping("/findById")
	public Result findById(Integer id) {
		SysUser user = userService.findById(id);
		return new Result(true,"查询用户成功",user);
	}

	@RequestMapping("/findRoleIdsById")
	public Result findRoleIdsById(Integer id) {
		List<Integer> list = userService.findRoleIdsById(id);
		return new Result(true,"查询成功",list);
	}

	@RequestMapping("/add")
	public Result add(@RequestBody SysUser sysUser , Integer[] roleIds) {
		userService.add(sysUser,roleIds);
		return new Result(true,"用户添加成功");
	}
	@RequestMapping("/findPage")
	public Result findPage(@RequestBody QueryPageBean queryPageBean) {
		PageResult pageResult =  userService.findPage(queryPageBean);
		return new Result(true,"查询用户成功",pageResult);
	}

	/**
	 * 获取用户名
	 * @return
	 */
	@RequestMapping("/getUsername")
	public Result getUsername(HttpSession session){
		//session.getAttribute("SPRING_SECURITY_CONTEXT");
		//session.getAttributeNames();  //可以获取session中所有的参数名
		//SecurityContextHolder 帮助类， 可以直接获取 session中 securityContext对象
		// SecurityContext: 上下文对象, 包含了用户信息和权限信息
		SecurityContext context = SecurityContextHolder.getContext();
		//获取认证对象
		Authentication authentication = context.getAuthentication();
		//获取principal 对象
		Object principal = authentication.getPrincipal();
		//如果 没有登录，获取到的是匿名用户，如果的登录了获取到的User对象
		//instanceof : 实例类型判断运算符
		String username = null;
		if(principal instanceof User){
			User u = (User) principal;
			username = u.getUsername();
		}

		return new Result(true, MessageConst.GET_USERNAME_SUCCESS, username);
	}

	/**
	 * 登录成功后访问的方法
	 * 	登录成功以后： pages/main.html
	 */
	@RequestMapping("/loginSuccess")
	public ModelAndView loginSuccess(){

		//在ModelAndView 中指定视图名称
		//默认为请求转发
		// 设置为重定向
		return new ModelAndView("redirect:http://localhost:83/pages/main.html");
	}

	/**
	 * 登录失败后访问的方法
	 * 登录失败以后：跳转到登录页面, 重新登录, 提示登录失败
	 */
	@RequestMapping("/loginFail")
	public ModelAndView loginFail(){

		return new ModelAndView("redirect:http://localhost:83/login.html");
	}
}