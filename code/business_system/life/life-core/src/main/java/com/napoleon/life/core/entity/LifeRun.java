package com.napoleon.life.core.entity;  
   
 import java.sql.Timestamp;

import com.napoleon.life.common.persistence.Entity;
 
 public class LifeRun implements Entity<Long>{  
   
	private static final long serialVersionUID = -7868347964713164298L;

	/**
	 *  自增主键
	 */
    private Long id;  
    
 	/**
	 *  用户名
	 */
    private String userId;  
    
 	/**
	 *  跑步类型
	 */
    private String runType;  
    
 	/**
	 *  跑步行走总持续时间（分）
	 */
    private Integer durationTime;  
    
 	/**
	 *  跑步距离（米）
	 */
    private Integer distance;  
    
 	/**
	 *  跑步地点
	 */
    private String address;  
    
 	/**
	 *  跑步的日期
	 */
    private Timestamp runTime;  
    
 	/**
	 *  年份
	 */
    private Integer year;  
    
 	/**
	 *  月份
	 */
    private Integer month;  
    
 	/**
	 *  天
	 */
    private Integer day;  
    
 	/**
	 *  星期
	 */
    private Integer week;  
    
 
    public Long getId() {  
        return id;  
    }  
      
    public void setId(Long id) {  
        this.id = id;  
    }  
    public String getUserId() {  
        return userId;  
    }  
      
    public void setUserId(String userId) {  
        this.userId = userId;  
    }  
    public String getRunType() {  
        return runType;  
    }  
      
    public void setRunType(String runType) {  
        this.runType = runType;  
    }  
    public Integer getDurationTime() {  
        return durationTime;  
    }  
      
    public void setDurationTime(Integer durationTime) {  
        this.durationTime = durationTime;  
    }  
    public Integer getDistance() {  
        return distance;  
    }  
      
    public void setDistance(Integer distance) {  
        this.distance = distance;  
    }  
    public String getAddress() {  
        return address;  
    }  
      
    public void setAddress(String address) {  
        this.address = address;  
    }  
    public Timestamp getRunTime() {  
        return runTime;  
    }  
      
    public void setRunTime(Timestamp runTime) {  
        this.runTime = runTime;  
    }  
    public Integer getYear() {  
        return year;  
    }  
      
    public void setYear(Integer year) {  
        this.year = year;  
    }  
    public Integer getMonth() {  
        return month;  
    }  
      
    public void setMonth(Integer month) {  
        this.month = month;  
    }  
    public Integer getDay() {  
        return day;  
    }  
      
    public void setDay(Integer day) {  
        this.day = day;  
    }  
    public Integer getWeek() {  
        return week;  
    }  
      
    public void setWeek(Integer week) {  
        this.week = week;  
    }  
 }  