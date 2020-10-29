package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public interface SetmealService {
    public void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findPage(QueryPageBean queryPageBean);

    List<Setmeal> findAll();

    /**
     * 基于ID，获取套餐详情
     * @param id
     * @return
     */
    public Setmeal findDetailById(Integer id);

    Setmeal findById(Integer id);

    List<Map<String,Object>> findSetmealCount();

    void edit(Setmeal setmeal, Integer[] checkgroupIds);

    void delById(Integer id);

    List<Integer> findCheckGroupIdsById(Integer id);
}
