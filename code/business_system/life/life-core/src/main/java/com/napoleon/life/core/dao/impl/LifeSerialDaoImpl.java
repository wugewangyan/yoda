package com.napoleon.life.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.napoleon.life.common.persistence.GenericDaoDefault;
import com.napoleon.life.core.entity.LifeSerial;
import com.napoleon.life.core.dao.LifeSerialDao;

@Repository
public class LifeSerialDaoImpl extends GenericDaoDefault<LifeSerial> implements
		LifeSerialDao {

	@Override
	public Long getLifeSerialByIp(String ipAddr) {
		return (Long)this.queryOne("getLifeSerialByIp", ipAddr);
	}
}
