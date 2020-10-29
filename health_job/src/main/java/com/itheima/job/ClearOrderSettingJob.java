package com.itheima.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.OrderSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 使用quartz对Service注入为null的问题
 *     仅仅是因为dubbo注解使用错误，
 *     以及没有导入dubbo依赖，
 *     或者spring-dubbo的配置文件
 */
@RestController
@Slf4j
public class ClearOrderSettingJob {

    @Reference
    OrderSettingService orderSettingService;

    public void ClearOrderSettingJob() {
        // ---
        log.debug("---定时任务ClearOrderSettingJob开始执行!");

        // 使用注入service层的方式调用sql并实现删除，在serviceImpl中实现删除间隔一个月的数据
        orderSettingService.ClearOrderSetting();

        // ***
        log.debug("***定时任务ClearOrderSettingJob执行成功!");
    }

}
