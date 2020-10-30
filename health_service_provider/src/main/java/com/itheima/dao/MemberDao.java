package com.itheima.dao;

import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Param;

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
}
