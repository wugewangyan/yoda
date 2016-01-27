package com.napoleon.life.core.entity;  
   
 import java.sql.Timestamp;

import com.napoleon.life.common.persistence.Entity;
 
 public class LifeBasketball implements Entity<Long>{  
   
	private static final long serialVersionUID = -8377053933991801313L;

	/**
	 *  自增主键
	 */
    private Long id;  
    
 	/**
	 *  用户名
	 */
    private String userId;  
    
 	/**
	 *  开始时间
	 */
    private Timestamp startTime;  
    
 	/**
	 *  结束时间
	 */
    private Timestamp finishTime;  
    
 	/**
	 *  地点
	 */
    private String address;  
    
 
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
    public Timestamp getStartTime() {  
        return startTime;  
    }  
      
    public void setStartTime(Timestamp startTime) {  
        this.startTime = startTime;  
    }  
    public Timestamp getFinishTime() {  
        return finishTime;  
    }  
      
    public void setFinishTime(Timestamp finishTime) {  
        this.finishTime = finishTime;  
    }  
    public String getAddress() {  
        return address;  
    }  
      
    public void setAddress(String address) {  
        this.address = address;  
    }  
 }  