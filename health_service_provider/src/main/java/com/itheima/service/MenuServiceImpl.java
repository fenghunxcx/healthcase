package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MenuDao;
import com.itheima.pojo.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Map<String, Object>> findMenu(String username) {
        List<Menu> menus = menuDao.findLevelMenu(username, 1);
        List<Map<String, Object>> list = new ArrayList<>();

        for (Menu menu : menus) {
            Map<String, Object> primaryMap = new HashMap<>();
            primaryMap.put("path", menu.getPath());
            primaryMap.put("title", menu.getName());
            primaryMap.put("icon", menu.getIcon());
            List<Menu> menus1 = menuDao.findsecondaryMenu(username, 2, menu.getId());
            List<Map<String, Object>> children = new ArrayList<>();
            for (Menu child : menus1) {
                Map<String, Object> SecondaryMap = new HashMap<>();
                SecondaryMap.put("path", child.getPath());
                SecondaryMap.put("title", child.getName());
                SecondaryMap.put("linkUrl", child.getLinkUrl());
                SecondaryMap.put("children", child.getChildren());
                children.add(SecondaryMap);
            }
            primaryMap.put("children", children);
            list.add(primaryMap);
        }
        return list;
    }
}
