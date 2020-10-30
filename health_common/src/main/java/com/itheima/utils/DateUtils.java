package com.itheima.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class DateUtils {

    public static Date getThisMonthLastDay(String thisMonthFirstDay){
        try {
            //把本月第一天的日期字符串转换为日期类型
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(thisMonthFirstDay);
            //获取日历对象
            Calendar calendar = Calendar.getInstance();
            //设置日历为 本月第一天
            calendar.setTime(date);
            //设置下月第一天
            calendar.add(Calendar.MONTH, 1);
            //调整为前一天， 为本月最后一天
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            return calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseString2Date(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String parseDate2String(Date orderDate) {
        return parseDate2String(orderDate,"yyyy-MM-dd");
    }

    public static String parseDate2String(Date orderDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(orderDate);
        return str;
    }

    /**
     * 获取最近一年的月份
     * @return
     */
    public static List<String> getThisYearMonths(){
        //动态获取过去1年的12个月，并封装到集合中
        List<String> months = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);//add方法用于计算
        for(int i = 0;i<12;i++){
            calendar.add(Calendar.MONTH,1);//add方法用于计算
            months.add(DateUtils.parseDate2String(calendar.getTime(), "yyyy-MM"));
        }
        return months;
    }

    public static Date getThisWeekFirstDay() {
        Calendar calendar = Calendar.getInstance();
        //获取 今天是一周的第几天 （周日为1 ，周一为 2， 周六 6）
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if(firstDayOfWeek == 1){
            calendar.add(Calendar.DAY_OF_MONTH , -6);
        }else{
            calendar.add(Calendar.DAY_OF_MONTH, -(firstDayOfWeek - 2));
        }
        return calendar.getTime();
    }

    public static Date getThisWeekLastDay(){
        Calendar calendar = Calendar.getInstance();
        //获取 今天是一周的第几天 （周日为1 ，周一为 2， 周六 6）
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if(firstDayOfWeek != 1){
            calendar.add(Calendar.DAY_OF_MONTH, (8 - firstDayOfWeek));
        }
        return calendar.getTime();
    }


    public static Date getThisMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getThisMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        //设置为本月1号
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //设置为下月1号
        calendar.add(Calendar.MONTH,1);
        //设置为前一天，为本月1号
        calendar.set(Calendar.DAY_OF_MONTH,-1);
        return calendar.getTime();
    }

    public static Date parseString3Date(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}