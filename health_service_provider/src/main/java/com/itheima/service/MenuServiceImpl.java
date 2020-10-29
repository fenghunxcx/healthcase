package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MenuDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.BusinessRuntimeException;
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
    public List<Menu> findAll() {
        return menuDao.findAll();
    }

    @Override
    public List<Map<String, String>> findName() {
        return menuDao.findName();
    }

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

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Menu> page = menuDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page);
    }

    @Override
    public void add(Menu menu) {
        Menu addMenu = handle(menu);
        menuDao.addALL(addMenu);
    }

    @Override
    public Menu findById(Integer id) {

        return menuDao.findById(id);
    }

    @Override
    public void edit(Menu menu) {
        Menu handleMenu=null;
        Integer id = menu.getId();
        String level = menuDao.selectById(id);//1
        Integer priority  = menuDao.selectPriorityById(id);//1
        if (level.equals("1")){
            if (menu.getPriority()!=priority ){
                throw new BusinessRuntimeException("一级目录不允许变为二级目录或者其他一级目录");
            }
            else if (menu.getPriority()==priority){
                menu.setPath(String.valueOf(menu.getPriority()+1));
                menu.setLevel("1");
                handleMenu=menu;
            }

        }
        else if (level.equals("2")){
            handleMenu = handle(menu);
        }

        menuDao.edit(handleMenu);
    }

    @Override
    public void delById(Integer id) {
        menuDao.delById(id);
    }

    public Menu handle(Menu menu){


        Integer priority = menu.getPriority();
        if (priority==-1){
            Integer counts = menuDao.selectParentsCounts(1);
            counts+=1;
            menu.setPriority(counts);
            menu.setLevel("1");
            menu.setPath(String.valueOf(counts+1));

        }
        else {
            menu.setLevel("2");
            Integer parentsId = menuDao.selectParentsId(1,priority);
            Integer counts = menuDao.selectChirdCounts(parentsId);
            Integer path1=priority+1;
            Integer path2 =counts+1;
            menu.setPath("/"+path1+"-"+path2);
            menu.setParentMenuId(parentsId);
        }
        return menu;
    }
}
