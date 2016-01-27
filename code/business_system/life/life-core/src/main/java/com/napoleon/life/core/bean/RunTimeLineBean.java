package com.napoleon.life.core.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.napoleon.life.core.service.impl.LifeRunServiceImpl.DayAndDistance;

public class RunTimeLineBean implements Serializable {
	private static final long serialVersionUID = -6580030950961867306L;

	private List<Long> ids;
	private List<String> months;
	private Map<String, List<Integer>> monthAndDays;
	private Map<String, List<DayAndDistance>> monthAndDistances;
	
	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	public Map<String, List<DayAndDistance>> getMonthAndDistances() {
		return monthAndDistances;
	}
	public void setMonthAndDistances(Map<String, List<DayAndDistance>> monthAndDistances) {
		this.monthAndDistances = monthAndDistances;
	}
	public Map<String, List<Integer>> getMonthAndDays() {
		return monthAndDays;
	}
	public void setMonthAndDays(Map<String, List<Integer>> monthAndDays) {
		this.monthAndDays = monthAndDays;
	}
	public List<String> getMonths() {
		return months;
	}
	public void setMonths(List<String> months) {
		this.months = months;
	}
	
}
