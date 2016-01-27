package com.napoleon.life.core.service;

import java.util.List;

import com.github.abel533.echarts.Option;
import com.napoleon.life.core.bean.SumMonthRunBean;
import com.napoleon.life.core.entity.LifeRun;

public interface LifeRunService {
	
	
	/**
	 * 查询用户在某年某月的跑步详情
	 * @param userId  用户注册的email账号
	 * @param year
	 * @param month
	 * @return
	 */
	public List<LifeRun> findByYearAndMonth(String userId, Integer year, Integer month);
	
	
	
	public Option findByYear(String userId, Integer year);
	
	
	public List<SumMonthRunBean> groupByYearAndMonth(String userId, Integer year);
	
	public Option groupByYearAndWeek(String userId, Integer year);
}
