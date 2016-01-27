package com.napoleon.life.core.bean;

import java.util.List;

public class RunWeekSumBean {

	private List<String> weeks;
	private List<Integer> distances;
	private List<Integer> times;

	public List<String> getWeeks() {
		return weeks;
	}

	public void setWeeks(List<String> weeks) {
		this.weeks = weeks;
	}

	public List<Integer> getDistances() {
		return distances;
	}

	public void setDistances(List<Integer> distances) {
		this.distances = distances;
	}

	public List<Integer> getTimes() {
		return times;
	}

	public void setTimes(List<Integer> times) {
		this.times = times;
	}

}
