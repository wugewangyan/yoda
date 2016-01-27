package com.napoleon.life.core.entity;  
   
 import java.sql.Timestamp;

import com.napoleon.life.common.persistence.Entity;
 
 public class LifeExercise implements Entity<Long>{  
   
	private static final long serialVersionUID = -766804878776963217L;

	/**
	 *  自增主键
	 */
    private Long id;  
    
 	/**
	 *  用户名
	 */
    private String userId;  
    
 	/**
	 *  囚徒类型
	 */
    private String exerciseType;  
    
 	/**
	 *  单次数量
	 */
    private Integer amount;  
    
 	/**
	 *  单次数量的单位
	 */
    private String unit;  
    
 	/**
	 *  锻炼组数
	 */
    private Integer groupAmount;  
    
 	/**
	 *  健身的时间
	 */
    private Timestamp exerciseTime;  
    
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
	 *  所在的星期
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
    public String getExerciseType() {  
        return exerciseType;  
    }  
      
    public void setExerciseType(String exerciseType) {  
        this.exerciseType = exerciseType;  
    }  
    public Integer getAmount() {  
        return amount;  
    }  
      
    public void setAmount(Integer amount) {  
        this.amount = amount;  
    }  
    public String getUnit() {  
        return unit;  
    }  
      
    public void setUnit(String unit) {  
        this.unit = unit;  
    }  
    public Integer getGroupAmount() {  
        return groupAmount;  
    }  
      
    public void setGroupAmount(Integer groupAmount) {  
        this.groupAmount = groupAmount;  
    }  
    public Timestamp getExerciseTime() {  
        return exerciseTime;  
    }  
      
    public void setExerciseTime(Timestamp exerciseTime) {  
        this.exerciseTime = exerciseTime;  
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