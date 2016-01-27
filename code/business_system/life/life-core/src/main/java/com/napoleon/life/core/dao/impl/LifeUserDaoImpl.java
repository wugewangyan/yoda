package com.napoleon.life.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.napoleon.life.common.persistence.GenericDaoDefault;
import com.napoleon.life.core.dao.LifeUserDao;
import com.napoleon.life.core.entity.LifeUser;

@Repository
public class LifeUserDaoImpl extends GenericDaoDefault<LifeUser> implements
		LifeUserDao {

	@Override
	public LifeUser findByEmail(String email) {
		return (LifeUser)super.queryOne("findByEmail", email);
	}
	
	@Override
	public LifeUser findByUserNo(String userNo) {
		return (LifeUser)super.queryOne("findByUserNo", userNo);
	}
}
