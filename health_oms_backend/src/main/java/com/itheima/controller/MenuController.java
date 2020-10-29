package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
@Slf4j
public class MenuController {

    @Reference
    private MenuService menuService;

    @Autowired
    private UserController userController;
    @RequestMapping("/findMenu")
    public Result findMenu(HttpSession session){
        Result result = userController.getUsername(session);
        List<Map<String, Object>> menu = menuService.findMenu(String.valueOf(result.getData()));
        return new Result(true, MessageConst.GET_MENU_SUCCESS, menu);
    }

    @RequestMapping("/findAll")
    public Result findAll() {
        List<Menu> menus = menuService.findAll();
        return new Result(true,"菜单权限查询成功",menus);
    }


    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = menuService.findPage(queryPageBean);
        return  new Result(true, "查询菜单成功",pageResult);
    }

    @RequestMapping("/findName")
    public Result findName(){
        HashMap<String,String> map = new HashMap<>();
        map.put("label","添加一级菜单");
        map.put("value","-1");
        List<Map<String,String>> list = menuService.findName();
        list.add(map);
        return new Result(true,"查询成功",list);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Menu menu){
        menuService.add(menu);

        return new Result(true,"菜单信息添加成功");
    }

    @RequestMapping("/findById")
    public Result findById(Integer id ){
        Menu menus = menuService.findById(id);
        return new Result(true,"回显菜单成功",menus);
    }
    @RequestMapping("/edit")
    public Result edit(@RequestBody Menu menu){
        System.out.println("menu = " + menu);
        menuService.edit(menu);
        return new Result(true,"更新成功");
    }

    @RequestMapping("/delById")
    public Result delById(Integer id){
        menuService.delById(id);
        return new Result(true,"删除成功");
    }
}
