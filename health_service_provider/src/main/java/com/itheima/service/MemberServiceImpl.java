package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    //根据手机号查询会员
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }
    //新增会员
    public void add(Member member) {
         memberDao.add(member);
    }

    @Override
    public Map<String, Object> getMemberReport() {
        List<String> months = getMonthOfLastYear();
        List<Long> memberCount = new ArrayList<>();
        //统计每个月份对应的会员数量
        for (String month : months) {
            String firstDay = month +  "-01"; // 2020-01-01
            String lastDay = getThisMonthLastDay(firstDay);
            long count = memberDao.findCountBeforeByDate(lastDay);
            memberCount.add(count);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("months", months);
        map.put("memberCount", memberCount);
        return map;
    }
    /**
     * 根据这个月第一天获取这个月最后一天
     *
     */
    private String getThisMonthLastDay(String firstDay){
        //2020-02-01 thisMonthFirstDay
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date firstDate = sdf.parse(firstDay);
            //创建日历对象
            Calendar cal = Calendar.getInstance();
            //设置为本月一号
            cal.setTime(firstDate);
            //调整为下月1号
            cal.add(Calendar.MONTH, 1);
            //调整为前一天(本月的最后一天)
            cal.add(Calendar.DAY_OF_MONTH, -1);
            String str = sdf.format(cal.getTime());
            return str;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取最近一年的月份
     * @return
     */
    private List<String> getMonthOfLastYear(){
        List<String> months = new ArrayList<>();
        //获取前12个月份
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -12);
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            String date = simpleDateFormat.format(calendar.getTime());
            months.add(date);
        }
        return months;
    }
}
