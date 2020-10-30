package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.Map;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public interface MemberService {
    public void add(Member member);
    public Member findByTelephone(String telephone);

    Map<String,Object> getMemberReport();
    Map findmember(String startmonth, String endmonth);
}
