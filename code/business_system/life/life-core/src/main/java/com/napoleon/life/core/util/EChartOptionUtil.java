package com.napoleon.life.core.util;

import java.util.List;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Timeline;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;

public class EChartOptionUtil {

	public static Option setTimeLine(List<String> data, Option option){
		Timeline timeline = new Timeline();
		timeline.setAutoPlay(true);
		timeline.data().addAll(data);
		option.setTimeline(timeline);
		return option;
	}
	
	
	public static Option setYAxis(AxisType axisType, String name, Option option){
		ValueAxis valueAxis = new ValueAxis();
	    valueAxis.type(axisType).name(name);
	    option.yAxis(valueAxis);
	    return option;
	}
	
	public static Option setXAxis(AxisType axisType, int interval, String name, List xdata, Option option){
		CategoryAxis categoryAxis = new CategoryAxis();
	    categoryAxis.type(axisType).axisLabel().interval(interval);
	    categoryAxis.name(name);
	    categoryAxis.data().addAll(xdata);
	    option.xAxis(categoryAxis);
	    return option;
	}
	
	
	
	
	
	
	
	
}
