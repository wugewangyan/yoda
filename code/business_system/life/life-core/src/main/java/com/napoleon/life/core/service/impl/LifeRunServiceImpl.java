package com.napoleon.life.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.FontStyle;
import com.github.abel533.echarts.code.LineType;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.Position;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.code.Y;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.style.ItemStyle;
import com.napoleon.life.core.bean.RunTimeLineBean;
import com.napoleon.life.core.bean.RunWeekSumBean;
import com.napoleon.life.core.bean.SumMonthRunBean;
import com.napoleon.life.core.bean.SumWeekRunBean;
import com.napoleon.life.core.dao.LifeRunDao;
import com.napoleon.life.core.entity.LifeRun;
import com.napoleon.life.core.service.LifeRunService;
import com.napoleon.life.core.util.EChartOptionUtil;
import com.napoleon.life.user.service.CommonUserService;

@Service
public class LifeRunServiceImpl implements LifeRunService {

	@Autowired
	private LifeRunDao lifeRunDao;
	
	@Autowired
	private CommonUserService userService;

	/**
	 * 查询某位用户在某年某月的跑步情况
	 * 列表展示某位用户某月的跑步情况
	 */
	@Override
	public List<LifeRun> findByYearAndMonth(String userId, Integer year, Integer month) {
		return this.lifeRunDao.findByYearAndMonth(userId, year, month);
	}
	
	
	/**
	 * 统计某位用户在某年每个月的跑步情况（长度）
	 * @param userId
	 * @param year
	 * @return
	 */
	public List<SumMonthRunBean> groupByYearAndMonth(String userId, Integer year){
		return this.lifeRunDao.groupByYearAndMonth(userId, year);
	}
	
	/**
	 * 统计某位用户在某年每个星期的跑步情况（长度）
	 * @param userId
	 * @param year
	 * @return
	 */
	public Option groupByYearAndWeek(String userId, Integer year){
		List<SumWeekRunBean> weekSums = this.lifeRunDao.groupByYearAndWeek(userId, year);
		if(!weekSums.isEmpty()){
			RunWeekSumBean result = new RunWeekSumBean();
			List<String>  weeks = new ArrayList<String>();
			List<Integer> distances = new ArrayList<Integer>();
			List<Integer> times = new ArrayList<Integer>();
			
			for(SumWeekRunBean weekSum : weekSums){
				weeks.add("第" + weekSum.getWeek() + "周");
				distances.add(weekSum.getTotalDistance());
				times.add(weekSum.getTotalTime());
			}
			
			result.setDistances(distances);
			result.setTimes(times);
			result.setWeeks(weeks);
			
			Option option = new Option();
			option.tooltip().show(true).trigger(Trigger.axis);
    		option.legend().x(X.right).data("跑步量", "跑步时长");
    		option.toolbox().show(true).x(X.right).y(Y.center).orient(Orient.vertical)
         		.feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar), Tool.restore, Tool.saveAsImage);
    		
    		CategoryAxis xAxis = new CategoryAxis();
    		xAxis.type(AxisType.category).name("周").position(Y.bottom).boundaryGap(true).axisLine().show(true).lineStyle()
    		.color("green").type(LineType.solid).width(2);
    		xAxis.axisTick().show(true).length(10).lineStyle().color("red").type(LineType.solid).width(2);
    		xAxis.axisLabel().show(true).interval("auto").rotate(45).margin(8).formatter("{value}").textStyle()
    		.color("blue").fontFamily("sans-serif").fontStyle(FontStyle.normal).fontWeight("bold");
    		xAxis.splitLine().show(true).lineStyle().color("#483d8b").type(LineType.dashed).width(1);
    		xAxis.splitArea().show(true).areaStyle().color(new String[]{"rgba(144,238,144,0.3)", "rgba(135,200,250,0.3)"});
    		xAxis.data().addAll(result.getWeeks());
    		option.xAxis(xAxis);
    		
    		ValueAxis leftAxis = new ValueAxis();
    		leftAxis.type(AxisType.value).name("距离").position(X.left).boundaryGap(new Double[]{0.0, 0.1});
    		leftAxis.axisLine().show(true).lineStyle().color("red").type(LineType.dashed).width(2);
    		leftAxis.axisTick().show(true).length(10).lineStyle().color("green").type(LineType.solid).width(2);
    		leftAxis.axisLabel().show(true).interval("auto").rotate(-45).margin(18).formatter("{value}")
    			.textStyle().color("#1e90ff").fontFamily("verdana").fontSize(10).fontStyle(FontStyle.normal).fontWeight("bold");
    		leftAxis.splitLine().show(true).lineStyle().color("#483d8b").type(LineType.dotted).width(2);
    		leftAxis.splitArea().show(true).areaStyle().color(new String[]{"rgba(205,92,92,0.3)", "rgba(255,215,0,0.3)"});
    		
    		ValueAxis rightAxis = new ValueAxis();
    		rightAxis.type(AxisType.value).name("时间").axisLabel().formatter("{value}");
    		
    	    option.yAxis(leftAxis, rightAxis);
    	    
    	    Line runLine = new Line();
    	    runLine.name("跑步量").type(SeriesType.line).data().addAll(result.getDistances());
    	    
    	    Line timeLine = new Line();
    	    timeLine.name("跑步时长").type(SeriesType.line).yAxisIndex(1).data().addAll(result.getTimes());
    	    
    	    option.series(runLine, timeLine);
    	    
    	    return option;
		}
		
		return null;
	}
	
	
	
	public Option findByYear(String userId, Integer year){
		List<LifeRun> lifeRuns = this.lifeRunDao.findByYear(userId, year);
		RunTimeLineBean result = new RunTimeLineBean();
		
		if(!lifeRuns.isEmpty()){
			// ［每月］和该月中［日期和跑步距离］的映射
			Map<String, List<DayAndDistance>> monthAndDistances = new HashMap<String, List<DayAndDistance>>();
			// [每月和对应的跑步日期]的映射
			Map<String, List<Integer>> monthAndDays = new TreeMap<String, List<Integer>>();
			// 主键的列表
			List<Long> ids = new ArrayList<Long>();
			
			for(LifeRun lifeRun : lifeRuns){
				ids.add(lifeRun.getId());
				
				String month = String.valueOf(lifeRun.getMonth());
				if(monthAndDistances.get(month) == null){
					List<DayAndDistance> distances = new ArrayList<DayAndDistance>();
					List<Integer> days = new ArrayList<Integer>();
					
					distances.add(new DayAndDistance(lifeRun.getDay(), lifeRun.getDistance()));
					days.add(lifeRun.getDay());
					
					monthAndDistances.put(month, distances);
					monthAndDays.put(month, days);
				}else{
					monthAndDistances.get(month).add(new DayAndDistance(lifeRun.getDay(), lifeRun.getDistance()));
					monthAndDays.get(month).add(lifeRun.getDay());
				}
			}
			
			result.setMonthAndDistances(monthAndDistances);
			result.setMonthAndDays(monthAndDays);
			
			List<String> months = new ArrayList<String>();
			months.addAll(monthAndDays.keySet());
			Collections.sort(months);
			result.setMonths(months);
			result.setIds(ids);
		}
		
		Option option = new Option();
		EChartOptionUtil.setTimeLine(result.getMonths(), option);
		
		List<Option> options = new ArrayList<Option>();
		
    	for(String month : result.getMonths()){
    		Option optionTemp = new Option();
    		optionTemp.title().text(month + "月份跑步详情").subtext("数据来自国家统计局");
    		optionTemp.tooltip().show(true).trigger(Trigger.axis).formatter("跑步详情 : <br/>" + month + "月{b}号 : {c}m");
    		optionTemp.legend().x(X.right).data("跑步距离").selected("跑步距离");
    		optionTemp.toolbox().show(true).x(X.right).y(Y.center).orient(Orient.vertical)
	         	.feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar), Tool.restore, Tool.saveAsImage);
    		optionTemp.calculable(true);
    		optionTemp.grid().y(80).y2(100);
    		
    		// 添加Y轴
    	    EChartOptionUtil.setYAxis(AxisType.value, "距离", optionTemp);
    	    // 添加X轴
    	    EChartOptionUtil.setXAxis(AxisType.category, 0, "天", result.getMonthAndDays().get(month), optionTemp);
    		
    		Bar bar = new Bar();
    	    bar.name("跑步距离");
    	    bar.markLine().symbol(new String[]{"arrow", "none"}).symbolSize(new Integer[]{4, 2});
    	    ItemStyle itemStyle = bar.markLine().itemStyle();
    	    itemStyle.normal().lineStyle().color("orange");
    	    itemStyle.normal().barBorderColor("orange").label().position(Position.left).textStyle().color("orange");
    		bar.markLine().data(new Data("average", "平均值"));
        	bar.data().addAll(result.getMonthAndDistances().get(month));
    		
    		optionTemp.series(bar);
    		options.add(optionTemp);
    	}
	    
		option.setOptions(options);
		return option;
	}
	
	public class Data{
		private String type;
		private String name;
		
		public Data(String type, String name){
			this.type = type;
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public class DayAndDistance{
		private Integer name;
		private Integer value;
		public Integer getName() {
			return name;
		}
		public void setName(Integer name) {
			this.name = name;
		}
		public Integer getValue() {
			return value;
		}
		public void setValue(Integer value) {
			this.value = value;
		}
		public DayAndDistance(Integer name, Integer value) {
			this.name = name;
			this.value = value;
		}
	}
			
	

}
