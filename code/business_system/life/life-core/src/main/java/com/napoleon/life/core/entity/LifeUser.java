package com.napoleon.life.core.entity;  
   
 import java.sql.Timestamp;

import com.napoleon.life.common.persistence.Entity;
 
 public class LifeUser implements Entity<Long>{  
   
	private static final long serialVersionUID = -5056773712363384767L;

	/**
	 *  自增主键
	 */
    private Long id;  
    
 	/**
	 *  用户编号
	 */
    private String userNo;  
    
 	/**
	 *  用户姓名
	 */
    private String userName;  
    
 	/**
	 *  邮箱账号
	 */
    private String email;  
    
 	/**
	 *  密码，密文
	 */
    private String password;  
    
 	/**
	 *  性别
	 */
    private String sex;  
    
 	/**
	 *  电话号码，密文
	 */
    private String phone;  
    
    /**
     * 激活码
     */
    private String activateCode;
    
 	/**
	 *  1:未激活，2已经激活
	 */
    private Integer activateStatus;  
    
 	/**
	 *  1：正常，2.冻结，3注销
	 */
    private Integer status;  
    
 	/**
	 *  创建时间
	 */
    private Timestamp createDate;  
    
 	/**
	 *  地址
	 */
    private String address;  
    
 	/**
	 *  更新时间
	 */
    private Timestamp updateTime;
    
    /**
     * 安全码，用于找回密码
     */
    private String safetyCode;
    
    /**
     * 发送安全码的时间，用于找回密码
     */
    private Timestamp safetyCodeTime;
    
    /**
     * 加密之后的userNo,不入库，用于激活邮箱
     */
    private String encryptUserNo;
    
 
    public Long getId() {  
        return id;  
    }  
      
    public void setId(Long id) {  
        this.id = id;  
    }  
    public String getUserNo() {  
        return userNo;  
    }  
      
    public void setUserNo(String userNo) {  
        this.userNo = userNo;  
    }  
    public String getUserName() {  
        return userName;  
    }  
      
    public void setUserName(String userName) {  
        this.userName = userName;  
    }  
    public String getEmail() {  
        return email;  
    }  
      
    public void setEmail(String email) {  
        this.email = email;  
    }  
    public String getPassword() {  
        return password;  
    }  
      
    public void setPassword(String password) {  
        this.password = password;  
    }  
    public String getSex() {  
        return sex;  
    }  
      
    public void setSex(String sex) {  
        this.sex = sex;  
    }  
    public String getPhone() {  
        return phone;  
    }  
      
    public void setPhone(String phone) {  
        this.phone = phone;  
    }  
    public Integer getActivateStatus() {  
        return activateStatus;  
    }  
      
    public void setActivateStatus(Integer activateStatus) {  
        this.activateStatus = activateStatus;  
    }  
    public Integer getStatus() {  
        return status;  
    }  
      
    public void setStatus(Integer status) {  
        this.status = status;  
    }  
    public Timestamp getCreateDate() {  
        return createDate;  
    }  
      
    public void setCreateDate(Timestamp createDate) {  
        this.createDate = createDate;  
    }  
    public String getAddress() {  
        return address;  
    }  
      
    public void setAddress(String address) {  
        this.address = address;  
    }  
    public Timestamp getUpdateTime() {  
        return updateTime;  
    }  
      
    public void setUpdateTime(Timestamp updateTime) {  
        this.updateTime = updateTime;  
    }

	public String getActivateCode() {
		return activateCode;
	}

	public void setActivateCode(String activateCode) {
		this.activateCode = activateCode;
	}

	public String getEncryptUserNo() {
		return encryptUserNo;
	}

	public void setEncryptUserNo(String encryptUserNo) {
		this.encryptUserNo = encryptUserNo;
	}

	public String getSafetyCode() {
		return safetyCode;
	}

	public void setSafetyCode(String safetyCode) {
		this.safetyCode = safetyCode;
	}

	public Timestamp getSafetyCodeTime() {
		return safetyCodeTime;
	}

	public void setSafetyCodeTime(Timestamp safetyCodeTime) {
		this.safetyCodeTime = safetyCodeTime;
	}  
 }  