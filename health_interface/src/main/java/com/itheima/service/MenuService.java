package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;

import java.util.List;
import java.util.Map;

public interface MenuService {
    List<Map<String, Object>> findMenu(String username);
    PageResult findPage(QueryPageBean queryPageBean);
}
