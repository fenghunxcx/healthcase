package com.itheima.dao;

import com.itheima.pojo.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuDao {
    @Select("select * from t_menu")
    List<Menu> findAll();

    List<Menu> findLevelMenu(@Param("username") String username, @Param("level") Integer level);

    List<Menu> findsecondaryMenu(@Param("username") String username, @Param("level") Integer level, @Param("id") Integer id);
}
