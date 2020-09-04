package com.itheima.dao;

import com.itheima.pojo.Order;

import java.util.List;

public interface OrderDao {
    List<Order> findByCondition(Order order);

    void add(Order order);
}