package com.itheima.service;

import java.util.Map;

public interface ReportService {
	/**
	 * 获取运营统计数据
	 * Map数据格式：
	 *      todayNewMember -> number		// 今日新增会员
	 *      thisWeekNewMember -> number		// 本周新增会员
	 *      thisMonthNewMember -> number	// 本月新增会员
	 *      totalMember -> number			//  总会员
	 *      todayOrderNumber -> number		// 今日预约人数
	 *      thisWeekOrderNumber -> number	// 本周预约人数
	 *      thisMonthOrderNumber -> number	// 本月预约人数
	 *      todayVisitsNumber -> number		// 今日到诊人数
	 *      thisWeekVisitsNumber -> number	// 本周到诊人数
	 *      thisMonthVisitsNumber -> number	// 本月到诊人数
	 *      hotSetmeals -> List<Setmeal>	// 热门套餐
	 * @return
	 */
	public Map<String,Object> getBussinessReport();
}