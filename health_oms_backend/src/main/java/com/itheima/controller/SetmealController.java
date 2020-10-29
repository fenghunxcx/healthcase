package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.constant.RedisConst;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping("/getToken")
    public Result getToken(){
        log.debug("获取token");
        String accessKey = "xweTHqDGlgfFUHj9LvQX_CFlcPPn_W7LVvBG-FkL";
        String secretKey = "1HJDNbeiJL7HViyfQGYOUCgw8oOgkQ-jEKmVvfaf";
        String bucket = "uploadserver";
        Auth auth = Auth.create(accessKey, secretKey);
        String uploadToken = auth.uploadToken(bucket);
        log.debug("获取token成功：" + uploadToken);
        return new Result(true, MessageConst.GET_QINIU_TOKEN_SUCCESS, uploadToken);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        log.debug("SetmealControlller:add:" + setmeal);
        log.debug("SetmealControlller:add:" + Arrays.toString(checkgroupIds));
        setmealService.add(setmeal, checkgroupIds);
        log.debug("套餐添加成功！！");
        return  new Result(true ,MessageConst.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页获取套餐数据
     * @param queryPageBean 查询参数
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        log.debug("SetmealControlller:findPage:" + queryPageBean);
        PageResult pageResult = setmealService.findPage(queryPageBean);
        log.debug("PageResult:" + pageResult);
        return new Result(true,MessageConst.QUERY_SETMEAL_SUCCESS, pageResult);
    }

    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/saveImgName")
    public Result saveImgName(String imgName){
        log.debug("SetmealControllr:saveImgName:" + imgName);
        jedisPool.getResource().sadd(RedisConst.SETMEAL_PIC_RESOURCES, imgName);
        log.debug("图片名称添加到redis成功！！");
        return new Result(true,MessageConst.ADD_IMGNAME_REDIS);
    }

    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('SETMEAL_EDIT')")
    public Result edit(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        setmealService.edit(setmeal, checkgroupIds);
        return new Result(true, MessageConst.EDIT_SETMEAL_SUCCESS);
    }

    @RequestMapping("/delById")
    @PreAuthorize("hasAuthority('SETMEAL_DELETE')")
    public Result delById(Integer id){
        setmealService.delById(id);
        return new Result(true, MessageConst.DELETE_SETMEAL_SUCCESS);
    }

    @RequestMapping("/findById")
    @PreAuthorize("hasAuthority('SETMEAL_QUERY')")
    public Result findById(Integer id){
        Setmeal setmeal = setmealService.findById(id);
        return new Result(true, MessageConst.QUERY_SETMEAL_SUCCESS, setmeal);
    }

    @RequestMapping("/findCheckGroupIdsById")
    @PreAuthorize("hasAuthority('SETMEAL_QUERY')")
    public Result findCheckGroupIdsById(Integer id){
        List<Integer> checkGroupIdsByIds = setmealService.findCheckGroupIdsById(id);
        return new Result(true, MessageConst.QUERY_CHECKGROUP_SUCCESS, checkGroupIdsByIds);
    }
}
