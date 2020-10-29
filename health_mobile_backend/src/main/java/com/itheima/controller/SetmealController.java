package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.constant.RedisConst;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;
import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Reference
    SetmealService setmealService;

    @Autowired
    JedisPool jedisPool;

    //移动端查询所有套餐
    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        log.debug("SetmealController： getSetmeal");
        List<Setmeal> setmealList = setmealService.findAll();
        log.debug("查询所有套餐成功！！");
        log.debug(setmealList.toString());
        return new Result(true, MessageConst.QUERY_SETMEALLIST_SUCCESS, setmealList);
    }
    @RequestMapping("/findDetailsById")
    public Result findDetailsById(Integer id){
        log.debug("SetmealController:findDetailById: " + id);
        Setmeal setmeal = setmealService.findDetailById(id);
        log.debug("查询套餐详情成功");
        log.debug(setmeal.toString());
        return new Result(true,MessageConst.QUERY_SETMEAL_SUCCESS,setmeal);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        log.debug("SetmealController:findById: " + id);
        Setmeal setmeal = setmealService.findById(id);
        log.debug("根据id查询套餐成功");
        return new Result(true,MessageConst.QUERY_SETMEAL_SUCCESS,setmeal);
    }

   /* @RequestMapping("/edit")
    public Result edit(Integer[] checkgroupIds ,@RequestBody Setmeal setmeal){
        log.debug("CheckGroupController:edit: " + Arrays.toString(checkgroupIds));

        setmealService.edit(setmeal, checkgroupIds);
        log.debug("套餐修改成功！！！");
        return new Result(true,"套餐修改成功");
    }
    @RequestMapping("/addRedis")
    public Result addImg2Redis(String imgName) {
        Jedis jedis = jedisPool.getResource();
        jedis.sadd(RedisConst.SETMEAL_PIC_RESOURCES, imgName);
        return new Result(true, MessageConst.ADD_IMGNAME_REDIS);
    }*/
}
