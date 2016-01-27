package com.napoleon.life.core.bean;

public class SumMonthRunBean {

	private Integer month;
	
	/**
	 * 总跑步距离［单位：m］
	 */
	private Integer totalDistance;
	
	/**
	 * 总跑步时间［单位：分钟］
	 */
	private Integer totalTime;

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Integer totalDistance) {
		this.totalDistance = totalDistance;
	}

	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}
}
