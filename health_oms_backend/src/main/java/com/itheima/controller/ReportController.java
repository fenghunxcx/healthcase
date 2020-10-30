package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    MemberService memberService;

    /**
     * {
     *     months: []
     *      memberCount:[]
     * }
     *
     *
     * @return
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        try {
            Map<String,Object> map = memberService.getMemberReport();
            return new Result(true, MessageConst.GET_MEMBER_NUMBER_REPORT_SUCCESS,map );
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.GET_MEMBER_NUMBER_REPORT_FAIL );
        }
    }
    @RequestMapping("/findBymonths")
    //接收参数起始月与结束月,通过起始与结束查询数据库
    public Result findBymonths(@RequestParam("startmonth") String startmonth, @RequestParam("endmonth") String endmonth){
        Map findmember = memberService.findmember(startmonth, endmonth);
        return new Result(true,"查询成功",findmember);
    }
}
