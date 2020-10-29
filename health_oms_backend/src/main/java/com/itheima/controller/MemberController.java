package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @description:
 * @author: Zhangyonghao
 * @date: 2020/10/28-19:55
 * @Version: 1.0.0
 */

@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {

       @Reference
       MemberService memberService;

    //性别
    @RequestMapping("/getProportion")
    public Result getProportion(){

     Map<String,Object> map  = memberService.getCountByMemberSex();
        log.debug("map ="+map.toString());
        //如果获取到的男女生比例不为空，
        //获取到的sex==1的话为男生，sex==2是女生；

        return new Result(true, MessageConst.GET__PROPORTION_MEMBERSUCCESSS,map);
    }
    //年龄
    @RequestMapping("/getAge")
    public Result getAge(){
        Map<String,Object> map  =memberService.getCountByMemberAge();
        log.debug("map ="+map.toString());
        return new Result(true, MessageConst.GET_AGE_SUCCESSS,map);
    }
}
