package com.napoleon.life.core.dao;

import com.napoleon.life.common.persistence.GenericDao;
import com.napoleon.life.core.entity.LifeUser;

public interface LifeUserDao extends GenericDao<LifeUser> {

	/**
	 * 通过email查找用户信息
	 * @param email
	 * @return
	 */
	public LifeUser findByEmail(String email);
	
	/**
	 * 通过userNo查找用户信息
	 * @param userNo
	 * @return
	 */
	public LifeUser findByUserNo(String userNo);
	
}
