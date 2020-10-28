package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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
}
