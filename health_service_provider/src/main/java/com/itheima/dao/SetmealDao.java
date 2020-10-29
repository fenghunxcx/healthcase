package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public interface SetmealDao {
    void add(Setmeal setmeal);

    void setRelation(@Param("setmeal_id") Integer setmealId,@Param("checkgroup_id") Integer checkgroupId);

    Page<Setmeal> findByCondition(String queryString);

    List<Setmeal> findAll();

    Setmeal findDetailsById(int id);

    Setmeal findById(Integer id);

    List<Map<String,Object>> findSetmealCount();

    List<Map<String,Object>> getHotSetmeal();

    @Delete("delete from t_setmeal where id = #{id}")
    void delById(Integer id);

    @Select("select count(0) from t_order where setmeal_id = #{id}")
    int findOrderById(Integer id);

    @Update("update t_setmeal set code = #{code},\n" +
            "  name = #{name},\n" +
            "  helpCode = #{helpCode},\n" +
            "  sex = #{sex},\n" +
            "  age = #{age},\n" +
            "  price = #{price},\n" +
            "  img = #{img},\n" +
            "  remark = #{remark},\n" +
            "  attention = #{attention}\n" +
            "  WHERE (`id`= #{id})")
    void edit(Setmeal Setmeal);

    @Delete("delete from t_setmeal_checkgroup where setmeal_id = #{id}")
    void delRelation(Integer id);

    @Select("select checkgroup_id from t_setmeal_checkgroup where setmeal_id = #{id}")
    List<Integer> findCheckGroupIdsById(Integer id);
}
