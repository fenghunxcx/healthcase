package com.itheima.dao;

import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public interface MemberDao {
    Member findByTelephone(String telephone);

    void add(Member member);

    long findCountBeforeByDate(String lastDay);
    long findBymonths(@Param("startmonth") String startmonth, @Param("endmonth") String endmonth);

    /**
     * 查询今日新增会员数
     * @param todayDate
     * @return
     */
    long findTodayNewMember(String todayDate);

    /**
     * 查询总会员数
     * @return
     */
    long findTotalCount();

    /**
     * 指定日期之后的新增会员数
     * @param date
     * @return
     */
    long findCountByAfterDate(String date);

    Integer getSex(String s);


    List<Map<String, Object>> getAge();
}
