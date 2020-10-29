package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao {

    List<Menu> findLevelMenu(@Param("username") String username, @Param("level") Integer level);

    List<Menu> findsecondaryMenu(Integer id);

    Page<Menu> findPage(@Param("s") String queryString);
}
