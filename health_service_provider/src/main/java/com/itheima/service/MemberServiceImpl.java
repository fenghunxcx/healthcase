package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.exception.BusinessRuntimeException;
import com.itheima.pojo.Member;
import com.itheima.utils.DateUtils;
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

    @Override
    public Map findmember(String startmonth, String endmonth) {
        List<Object> months = new ArrayList<>();
        List<Long> memberCount = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        Date startdate = DateUtils.parseString3Date(startmonth);
        Date endDate = DateUtils.parseString3Date(endmonth);
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startdate);
        end.setTime(endDate);
        start.add(Calendar.MONTH,+1);
        end.add(Calendar.MONTH,+1);
        int yearStart = start.get(Calendar.YEAR);
        int endYear = end.get(Calendar.YEAR);
        int startMonth = start.get(Calendar.MONTH);
        int endMonth = end.get(Calendar.MONTH);
        if (yearStart > endYear) {
            throw new BusinessRuntimeException("起始日期不能大于结束日期");
        } else if (yearStart == endYear) {
            if (startMonth >= endMonth) {
                throw new BusinessRuntimeException("起始日期不能大于结束日期");
            }
        }
        int month  = (endYear - yearStart) *12;
        month = month+endMonth-startMonth;
        for (int i = 0; i < month; i++) {
            Date time = start.getTime();
            String monthstr = new SimpleDateFormat("yyyy-MM").format(time);
            String monthbegin = monthstr+"-01";
            String monthend = monthstr+"-31";
            months.add(monthstr);
            long bymonths = memberDao.findBymonths(monthbegin, monthend);
            memberCount.add(bymonths);
            start.add(Calendar.MONTH,+1);
        }
        System.out.println(list);
        Map map = new HashMap();
        map.put("months",months);
        map.put("memebercount",memberCount);

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
