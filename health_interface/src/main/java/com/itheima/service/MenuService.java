package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Menu;


import java.util.List;
import java.util.Map;

public interface MenuService {


    List<Map<String, String>> findName();



    List<Map<String, Object>> findMenu(String username);
    List<Menu> findAll();
    PageResult findPage(QueryPageBean queryPageBean);

    void add(Menu menu);

    Menu findById(Integer id);

    void edit(Menu menu);

    void delById(Integer id);
}
