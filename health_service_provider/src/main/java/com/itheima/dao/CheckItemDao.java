package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

/**
 * 持久层Dao接口
 */
public interface CheckItemDao {
    void add(CheckItem checkItem);

    Page<CheckItem> findByCondition(String queryString);

    long findCountById(Integer id);

    void delById(Integer id);

    /**
     * 基于ID，获取数据
     * @param id ID
     * @return 对象
     */
    public CheckItem findById(Integer id);
}