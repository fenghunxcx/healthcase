package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConst;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.BusinessRuntimeException;
import com.itheima.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Jedis;
import java.util.List;
import java.util.Map;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    SetmealDao setmealDao;
    private final String REDIS_SETMEAL = "Setmeal";
    @Autowired
    JedisPool jedisPool;

    @Transactional
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //1. 添加套餐（回显主键）
        setmealDao.add(setmeal);
        //2. 维护套餐和检查组的关系
        setRelation(setmeal, checkgroupIds);
        //删除redis
        deletJedis(REDIS_SETMEAL);
        if (setmeal.getImg() != null) {
            jedisPool.getResource().srem(RedisConst.SETMEAL_PIC_RESOURCES, setmeal.getImg());
        }

    }

    /**
     * 设置检查组和套餐的关系
     * @param setmeal
     * @param checkgroupIds
     */
    private void setRelation(Setmeal setmeal,Integer[] checkgroupIds){
        if(setmeal.getId()!= null && checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.setRelation(setmeal.getId(), checkgroupId);
            }
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Setmeal> pageSetmeal = setmealDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult(pageSetmeal.getTotal(),pageSetmeal.getResult());
    }

    //移动端查询所有套餐
    @Override
    public List<Setmeal> findAll() {
        Jedis jedis = jedisPool.getResource();
        String redisSetmeal = jedis.get(REDIS_SETMEAL);
        //JSON字符串转java对象
        List<Setmeal> setmealList = JSONArray.parseArray(redisSetmeal, Setmeal.class);
        //redis没有从数据库查
        if (redisSetmeal == null) {
            setmealList = setmealDao.findAll();
            addJedis(setmealList);

        }
        return setmealList;

    }

    @Override
    public Setmeal findDetailById(Integer id) {
        Jedis jedis = jedisPool.getResource();
        String s1 = jedis.get(id + "");
        Setmeal setmeal = JSONArray.parseObject(s1, Setmeal.class);
        if (s1 == null) {
            setmeal = setmealDao.findDetailsById(id);
            String s = JSON.toJSONString(setmeal);
            jedis.set(id+"",s);

        }

        return setmeal;
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }


    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        long edit = setmealDao.edit(setmeal);
        if (edit > 0) {
            deletJedis(String.valueOf(setmeal.getId()));
            deletJedis(REDIS_SETMEAL);
        }
        Integer setmealId = setmeal.getId();
        if(setmealId != null){
            setmealDao.delRelation(setmealId);
            setRelation(setmeal, checkgroupIds);
            jedisPool.getResource().srem(RedisConst.SETMEAL_PIC_RESOURCES, setmeal.getImg());
        }
    }

    @Override
    public void delById(Integer id) {
        int row = setmealDao.findOrderById(id);
        if(row > 0){
            throw new BusinessRuntimeException("删除失败,此套餐与订单关联");
        }
        setmealDao.delRelation(id);
        long del = setmealDao.delById(id);
        if (del > 0) {
            deletJedis(String.valueOf(id));
            deletJedis(REDIS_SETMEAL);
        }
    }

    @Override
    public List<Integer> findCheckGroupIdsById(Integer id) {
        return setmealDao.findCheckGroupIdsById(id);
    }


    private void addJedis(List<Setmeal> setmeal) {
        Jedis jedis = jedisPool.getResource();
        String s = JSON.toJSONString(setmeal);
        jedis.set(REDIS_SETMEAL,s);
    }
    private void deletJedis(String key) {
        Jedis jedis = jedisPool.getResource();
        if (key != null) {
            jedis.del(key);
        }

    }


}
