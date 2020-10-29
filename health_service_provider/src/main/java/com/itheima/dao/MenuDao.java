package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface MenuDao {
    @Select("select * from t_menu")
    List<Menu> findAll();

    List<Menu> findLevelMenu(@Param("username") String username, @Param("level") Integer level);

    List<Menu> findsecondaryMenu(@Param("username") String username, @Param("level") Integer level, @Param("id") Integer id);

    Page<Menu> findPage(@Param("s") String queryString);

    List<Map<String, String>> findName();

    Integer selectParentsCounts(@Param("l") Integer level );

    Integer selectParentsId(@Param("l") Integer level, @Param("p") Integer priority);

    Integer selectChirdCounts(@Param("id") Integer parentsId);

    void addALL(Menu menu);

    Menu findById(@Param("id") Integer id);

    void edit(Menu handleMenu);

    void delById(@Param("id") Integer id);

    String selectById(@Param("id") Integer id);

    Integer selectPriorityById(@Param("id") Integer id);

    String selectParentsName(@Param("id") Integer pId);
}
